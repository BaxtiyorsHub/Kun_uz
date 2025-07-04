package dasturlash.uz.repository.article;

import dasturlash.uz.entity.article.SavedArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavedArticleRepository extends JpaRepository<SavedArticleEntity, Long> {
    Optional<SavedArticleEntity> findByArticle_idAndProfile_id(String articleId, Integer profileId);

    @Modifying
    void deleteByArticle_idAndProfile_id(String articleId, Integer profileId);

    List<SavedArticleEntity> findByProfile_id(Integer profileId);
}