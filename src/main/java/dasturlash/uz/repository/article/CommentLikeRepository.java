package dasturlash.uz.repository.article;

import dasturlash.uz.entity.article.CommentLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLikeEntity, Long> {
    CommentLikeEntity findByComment_idAndProfile_id(String commentId, Integer profileId);

}
