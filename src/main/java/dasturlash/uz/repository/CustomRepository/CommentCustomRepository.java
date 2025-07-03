package dasturlash.uz.repository.CustomRepository;

import dasturlash.uz.dto.FilterResultDTO;
import dasturlash.uz.entity.article.CommentFilterDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class CommentCustomRepository {

    private final EntityManager entityManager;

    public CommentCustomRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Filters for admin
     * @param page
     * @param size
     * @return {@code FilterResultDTO<Object[]>}
     * */
    public FilterResultDTO<Object[]> filter(int page, int size, CommentFilterDTO dto) {
        StringBuilder selectQuery = new StringBuilder("SELECT c.id,c.created_date,c.update_date,c.profile_id,c.content,c.article_id,c.visible,c.likes,c.dislikes FROM CommentArticleEntity c ");
        StringBuilder countQuery = new StringBuilder("select count(*) from CommentArticleEntity c ");
        StringBuilder builder = new StringBuilder(" where c.visible = true and c.article.status = 'PUBLISHED' ");

        Map<String, Object> params = new HashMap<>();

        if (dto.getCommentId() != null) {
            builder.append(" and c.id =:commentId ");
            params.put("commentId", dto.getCommentId());
        }
        if (dto.getArticle_id() != null) {
            builder.append(" and c.article_id =:article_id ");
            params.put("article_id", dto.getArticle_id());
        }
        if (dto.getProfile_id() != null) {
            builder.append(" and c.profile_id =:profile_id ");
            params.put("profile_id", dto.getProfile_id());
        }
        if (dto.getCreated_date_from() != null && dto.getCreated_date_to() != null) {
            builder.append(" and c.created_date between :created_date_from and :created_date_to ");
            params.put("created_date_from", dto.getCreated_date_from());
            params.put("created_date_to", dto.getCreated_date_to());
        } else if (dto.getCreated_date_from() != null) {
            builder.append(" and c.created_date > :created_date_from ");
            params.put("created_date_from", dto.getCreated_date_from());
        } else if (dto.getCreated_date_to() != null) {
            builder.append(" and c.created_date < :created_date_to ");
            params.put("created_date_to", dto.getCreated_date_to());
        }

        selectQuery.append(builder);
        countQuery.append(builder);

        Query selectQuery1 = entityManager.createQuery(selectQuery.toString());
        selectQuery1.setFirstResult((page) * size);
        selectQuery1.setMaxResults(size);

        params.forEach(selectQuery1::setParameter);

        var commentList = selectQuery1.getResultList();

        Query countQuery1 = entityManager.createQuery(countQuery.toString());
        params.forEach(countQuery1::setParameter);

        Long totalElements = (Long) countQuery1.getSingleResult();

        return new FilterResultDTO<Object[]>(commentList, totalElements);
    }
    //            id, created_date_from, created_date_to, profile_id, article_id
    // response : id, created_date, update_date, profile_id, content, article_id,
    //                reply_id, visible, like_count, dislike_count
}

