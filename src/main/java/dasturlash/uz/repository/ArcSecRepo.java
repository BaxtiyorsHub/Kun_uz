package dasturlash.uz.repository;

import dasturlash.uz.entities.ArticleSectionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArcSecRepo extends CrudRepository<ArticleSectionEntity, Integer> {

    @Query("from ArticleSectionEntity where articleId =?1 and visible=true ")
    List<ArticleSectionEntity> findByArticleId(Integer articleId);
}
