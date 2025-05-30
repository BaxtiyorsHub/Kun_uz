package dasturlash.uz.repository;

import dasturlash.uz.entities.ArticleEntity;
import dasturlash.uz.entities.CategoryEntity;
import dasturlash.uz.entities.SectionEntity;
import dasturlash.uz.responseDto.CategoryResponseDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ArticleRepository extends CrudRepository<ArticleEntity, Integer> {

    @Query("select arc.category from ArticleCategoryEntity arc where arc.visible = true and arc.category.visible = true")
    List<CategoryEntity> getCategory();

    @Query("select arc.section from ArticleSectionEntity arc where arc.visible = true and arc.section.visible = true ")
    List<SectionEntity> getSection();

    @Query("from ArticleEntity where id = ?1 and visible =true ")
    ArticleEntity getById(Integer id);
}
