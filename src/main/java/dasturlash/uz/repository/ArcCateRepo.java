package dasturlash.uz.repository;

import dasturlash.uz.entities.ArticleCategoryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArcCateRepo extends CrudRepository<ArticleCategoryEntity, Integer> {
}
