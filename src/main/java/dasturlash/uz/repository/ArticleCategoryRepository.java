package dasturlash.uz.repository;

import dasturlash.uz.entities.ArticleCategoryEntity;
import dasturlash.uz.entities.CategoryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleCategoryRepository extends CrudRepository<ArticleCategoryEntity,Integer> {

    @Query("select ct from ArticleCategoryEntity arc join arc.category ct where arc.visible = true and ct.visible = true")
    List<CategoryEntity> getByVisibleIsTrue();

}
