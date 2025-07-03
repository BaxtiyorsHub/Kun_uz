package dasturlash.uz.service.article;

import dasturlash.uz.entity.article.CommentLikeEntity;
import dasturlash.uz.entity.profile.ProfileEntity;
import dasturlash.uz.enums.LikeStatus;
import dasturlash.uz.exceptions.AppBadException;
import dasturlash.uz.repository.article.CommentLikeRepository;
import dasturlash.uz.service.ProfileService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentLikeService {
    @Autowired
    private CommentLikeRepository commentLikeRepository;
    @Autowired
    private CommentArticleService commentArticleService;
    @Autowired
    private ProfileService profileService;

    @Transactional
    public Boolean likeComment(String commentId, Integer profileId) {
        CommentLikeEntity likeEntity;
        try {
            likeEntity = commentLikeRepository.findByComment_idAndProfile_id(commentId, profileId);
            if (likeEntity != null) {
                if (likeEntity.getEmotion() == LikeStatus.LIKE) throw new AppBadException("You already liked");
                if (likeEntity.getEmotion() == LikeStatus.DISLIKE) likeEntity.setEmotion(LikeStatus.LIKE);
                return true;
            }
            ProfileEntity profile = profileService.get(profileId);

            likeEntity = new CommentLikeEntity();
            likeEntity.setComment_id(commentId);
            likeEntity.setComment(commentArticleService.getCommentByCommentId(commentId));
            likeEntity.setEmotion(LikeStatus.LIKE);
            likeEntity.setProfile_id(profileId);
            likeEntity.setProfile(profile);

            commentLikeRepository.save(likeEntity);
            commentArticleService.commentLiked(commentId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Transactional
    public Boolean dislikeComment(String commentId, Integer profileId) {
        CommentLikeEntity result;
        try {
            result = commentLikeRepository.findByComment_idAndProfile_id(commentId, profileId);
            if (result != null) {
                if (result.getEmotion() == LikeStatus.DISLIKE) throw new AppBadException("You already disliked");
                if (result.getEmotion() == LikeStatus.LIKE) {
                    result.setEmotion(LikeStatus.DISLIKE);
                    return true;
                }
            }

            result = new CommentLikeEntity();
            result.setComment_id(commentId);
            result.setComment(commentArticleService.getCommentByCommentId(commentId));
            result.setEmotion(LikeStatus.DISLIKE);
            result.setProfile_id(profileId);
            result.setProfile(profileService.get(profileId));

            commentLikeRepository.save(result);
            commentArticleService.commentDisliked(commentId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean remove(String commentId, Integer profileId) {
        CommentLikeEntity entity;
        try {
            entity = commentLikeRepository.findByComment_idAndProfile_id(commentId, profileId);
            if (entity != null) {
                if (entity.getEmotion() == LikeStatus.LIKE) commentArticleService.removeLike(commentId);
                if (entity.getEmotion() == LikeStatus.DISLIKE) commentArticleService.removeDislike(commentId);
                commentLikeRepository.delete(entity);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
