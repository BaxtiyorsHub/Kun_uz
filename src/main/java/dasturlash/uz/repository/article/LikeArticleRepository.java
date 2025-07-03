package dasturlash.uz.repository.article;

import dasturlash.uz.entity.article.LikeArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeArticleRepository extends JpaRepository<LikeArticleEntity, Long> {

    LikeArticleEntity getLikeArticleEntityByArticleId(String articleId);
}
