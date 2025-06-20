package dasturlash.uz.repository;

import dasturlash.uz.entities.ArticleEntity;
import dasturlash.uz.entities.CategoryEntity;
import dasturlash.uz.entities.SectionEntity;
import dasturlash.uz.responseDto.CategoryResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ArticleRepository extends CrudRepository<ArticleEntity, Integer> , JpaRepository<ArticleEntity, Integer> {

    @Query("select arc.category from ArticleCategoryEntity arc where arc.visible = true and arc.category.visible = true")
    List<CategoryEntity> getCategory();

    @Query("select arc.section from ArticleSectionEntity arc where arc.visible = true and arc.section.visible = true ")
    List<SectionEntity> getSection();

    /**
     * @param classId
     * @return ArticleEntities
     */
    @Query("from ArticleSectionEntity ars join ArticleEntity ar on ar.id=ars.articleId where ar.visible=true and ars.sectionId=?1 order by ar.publishedDate desc limit ?2")
    List<ArticleEntity> getArticleEntitiesBySectionId(Integer classId, int limit);

    /**
     * @param classId
     * @return ArticleEntities
     */
    @Query("from ArticleCategoryEntity arc join ArticleEntity ar on ar.id=arc.articleId where ar.visible=true and arc.categoryId=?1 order by ar.publishedDate desc limit ?2")
    List<ArticleEntity> getArticleEntitiesByCategoryId(int classId, int limit);
}
