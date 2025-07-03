package dasturlash.uz.service.article;

import dasturlash.uz.dto.FilterResultDTO;
import dasturlash.uz.dto.article.CommentAdminDTO;
import dasturlash.uz.dto.article.CommentDTO;
import dasturlash.uz.entity.article.CommentArticleEntity;
import dasturlash.uz.entity.article.CommentFilterDTO;
import dasturlash.uz.exceptions.AppBadException;
import dasturlash.uz.repository.CustomRepository.CommentCustomRepository;
import dasturlash.uz.repository.article.CommentArticleRepository;
import dasturlash.uz.service.ProfileService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Service
public class CommentArticleService {
    private final CommentArticleRepository commentArticleRepository;
    private final ProfileService profileService;
    private final ArticleService articleService;
    private final CommentCustomRepository commentCustomRepository;

    public CommentArticleService(CommentArticleRepository commentArticleRepository, ProfileService profileService, ArticleService articleService, CommentCustomRepository commentCustomRepository) {
        this.commentArticleRepository = commentArticleRepository;
        this.profileService = profileService;
        this.articleService = articleService;
        this.commentCustomRepository = commentCustomRepository;
    }

    public Boolean create(String articleId, CommentDTO dto) {
        try {
            CommentArticleEntity entity = new CommentArticleEntity();
            entity.setArticle_id(articleId);
            entity.setArticle(articleService.getArticleById(articleId));
            entity.setContent(dto.getContent());
            entity.setProfile_id(dto.getProfileId());
            entity.setProfile(profileService.get(dto.getProfileId()));
            if (dto.getCommentId() != null) {
                entity.setReply(entity);
                entity.setReply_id(entity.getReply_id());
            }
            commentArticleRepository.save(entity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppBadException("Sign up please");
        }
    }

    @Transactional
    public Boolean update(String articleId, CommentDTO dto) {
        CommentArticleEntity result = commentArticleRepository
                .findByComment(articleId, dto.getProfileId(), dto.getCommentId());
        if (result == null) throw new AppBadException("Comment not found");

        result.setUpdate_date(LocalDateTime.now());
        result.setContent(dto.getContent());
        return true;
    }

    @Transactional
    public Boolean delete(Integer profileId, String articleId, String commentId) {
        CommentArticleEntity result = commentArticleRepository
                .findByComment(articleId, profileId, commentId);
        if (result == null) throw new AppBadException("Comment not found");
        result.setVisible(false);
        return true;
    }

    public List<CommentDTO> getCommentListByArticleId(String articleId) {
        try {
            return commentArticleRepository
                    .getCommentArticleEntitiesByArticle_id(articleId)
                    .stream()
                    .map(this::toDto)
                    .toList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private CommentDTO toDto(CommentArticleEntity entity) {
        CommentDTO dto = new CommentDTO();
        dto.setContent(entity.getContent());
        dto.setProfile(profileService.openDTO(entity.getProfile_id()));
        return dto;
    }

    public Page<CommentAdminDTO> paginationComment(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "created_date");
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        /*List<CommentAdminDTO> response = commentArticleRepository
                .findAll(pageRequest)
                .getContent()
                .stream()
                .map(this::toAdminDTo)
                .toList();*/
        Page<CommentAdminDTO> map = commentArticleRepository
                .findAll(pageRequest)
                .map(this::toAdminDTo);

        return new PageImpl<>(map.getContent(), pageRequest, map.getTotalElements());
    }

    private CommentAdminDTO toAdminDTo(CommentArticleEntity entity) {
        CommentAdminDTO dto = new CommentAdminDTO();
        dto.setCommentId(entity.getId());
        dto.setContent(entity.getContent());
        dto.setProfile(profileService.openDTO(entity.getProfile_id()));
        dto.setArticle(articleService.openDTO(entity.getArticle_id()));
        dto.setReply_id(entity.getReply_id());
        dto.setCreated_date(entity.getCreated_date());
        dto.setUpdate_date(entity.getUpdate_date());

        return dto;
    }

    public List<CommentAdminDTO> getCommentsByCommentId(String commentId) {
        List<CommentArticleEntity> result = commentArticleRepository.findByCommentId(commentId);
        if (result == null) throw new AppBadException("Comment not found");

        return result.stream().map(this::toDTO).toList();
    }

    private CommentAdminDTO toDTO(CommentArticleEntity entity) {
        CommentAdminDTO dto = new CommentAdminDTO();
        dto.setCommentId(entity.getId());
        dto.setCreated_date(entity.getCreated_date());
        dto.setUpdate_date(entity.getUpdate_date());
        dto.setProfile(profileService.openDTO(entity.getProfile_id()));
        return dto;
    }

    public Page<CommentAdminDTO> filter(int page, int size, CommentFilterDTO dto) {
        FilterResultDTO<Object[]> filter = commentCustomRepository.filter(page, size, dto);
        List<CommentAdminDTO> response = new LinkedList<>();
        for (Object[] objects : filter.getContent()) {
            CommentAdminDTO commentAdminDTO = new CommentAdminDTO();
            commentAdminDTO.setCommentId((String) objects[0]);
            commentAdminDTO.setCreated_date((LocalDateTime) objects[1]);
            commentAdminDTO.setUpdate_date((LocalDateTime) objects[2]);
            commentAdminDTO.setProfile(profileService.openDTO((Integer) objects[3]));
            commentAdminDTO.setContent((String) objects[4]);
            commentAdminDTO.setArticle(articleService.openDTO((String) objects[5]));
            commentAdminDTO.setReplyList(this.replyComments(dto.getArticle_id(), dto.getCommentId()));
            if (objects[7] != null) commentAdminDTO.setVisible((Boolean) objects[6]);
            commentAdminDTO.setLikes((Long) objects[7]);
            commentAdminDTO.setDislikes((Long) objects[8]);

            response.add(commentAdminDTO);
        }
        return new PageImpl<>(response,PageRequest.of(page,size),filter.getTotal());
        // response : id, created_date, update_date, profile_id, content, article_id,
        //                reply_id, visible, like_count, dislike_count
    }

    private List<String> replyComments(String articleId, String commentId) {
        List<CommentArticleEntity> replies = commentArticleRepository.getReplies(articleId, commentId);
        if (replies == null) throw new AppBadException("Comment not found");
        return replies.stream().map(CommentArticleEntity::getId).toList();
    }

    public List<CommentAdminDTO> getAllComments(String articleId) {
        return commentArticleRepository
                .getCommentArticleEntitiesByArticle_id(articleId)
                .stream()
                .map(this::toAdminDTo)
                .toList();
    }

    public CommentArticleEntity getCommentByCommentId(String commentId) {
        CommentArticleEntity result = commentArticleRepository.findCommentArticleEntitiesById(commentId);
        if (result == null) throw new AppBadException("Comment not found");
        return result;
    }

    public void commentLiked(String commentId) {
        CommentArticleEntity commentByCommentId = getCommentByCommentId(commentId);
        commentByCommentId.setLikes(commentByCommentId.getLikes() + 1);
        commentArticleRepository.save(commentByCommentId);
    }

    public void commentDisliked(String commentId) {
        CommentArticleEntity commentByCommentId = getCommentByCommentId(commentId);
        commentByCommentId.setLikes(commentByCommentId.getLikes() + 1);
        commentArticleRepository.save(commentByCommentId);
    }

    public void removeLike(String commentId) {
        CommentArticleEntity result = getCommentByCommentId(commentId);
        result.setLikes(result.getLikes() - 1);
        commentArticleRepository.save(result);
    }

    public void removeDislike(String commentId) {
        CommentArticleEntity result = getCommentByCommentId(commentId);
        result.setDislikes(result.getDislikes() - 1);
        commentArticleRepository.save(result);
    }
}
