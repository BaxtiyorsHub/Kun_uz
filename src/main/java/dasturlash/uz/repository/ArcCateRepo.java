package dasturlash.uz.repository;

import dasturlash.uz.entities.ArticleCategoryEntity;
import dasturlash.uz.entities.ArticleSectionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArcCateRepo extends CrudRepository<ArticleCategoryEntity, Integer> {
    List<ArticleCategoryEntity> findByArticleId(Integer attr0);
}
