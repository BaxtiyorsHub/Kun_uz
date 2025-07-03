package dasturlash.uz.repository.article;

import dasturlash.uz.entity.article.SavedArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavedArticleRepository extends JpaRepository<SavedArticleEntity, Long> {


}
