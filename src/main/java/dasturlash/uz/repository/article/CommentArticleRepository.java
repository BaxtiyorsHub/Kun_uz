package dasturlash.uz.repository.article;

import dasturlash.uz.entity.article.CommentArticleEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentArticleRepository extends JpaRepository<CommentArticleEntity, Long> {

    List<CommentArticleEntity> getCommentArticleEntitiesByArticle_id(String articleId);

    /**
     * @param articleId
     * @return Comments by article_id CommentArticleEntity List
     */
    List<CommentArticleEntity> findByArticle_idAndVisibleIsTrue(String articleId);

    /**
     * @param articleId
     * @param profileId
     * @param commentId
     * @return Comment for write reply comment
     */
    @Query("from CommentArticleEntity where visible=true and article_id=?1 and profile_id=?2 and id=?3")
    CommentArticleEntity findByComment(String articleId, @NotNull Integer profileId, String commentId);

    @Query("select cae from CommentArticleEntity as cae where cae.visible=true and cae.reply=?1")
    List<CommentArticleEntity> findByCommentId(String commentId);

    // (10) Salom bormisizlar
    //      - (50) borman (reply_id = 10)
    //      - (52) Qalaysan (reply_id = 10)
    //          - (55) nima (reply_id = 52)
    //      - (56) bor (reply_id = 10)

    /**
     * @param articleId
     * @param commentId
     * @return Replied Comments
     */
    @Query("select cae.reply from CommentArticleEntity as cae where cae.visible=true and cae.article_id=?1 and cae.id=?2")
    List<CommentArticleEntity> getReplies(String articleId, String commentId);

    CommentArticleEntity findCommentArticleEntitiesById(String id);
}
