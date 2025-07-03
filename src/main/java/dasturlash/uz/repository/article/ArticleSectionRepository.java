package dasturlash.uz.repository.article;

import dasturlash.uz.entity.article.ArticleSectionEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleSectionRepository extends CrudRepository<ArticleSectionEntity, String> {

    @Modifying
    @Query("delete  from  ArticleSectionEntity where articleId =?1 and sectionId =?2")
    void deleteBySectionIdAndArticleId(String articleId, Integer sectionId);

    @Query("select sectionId from ArticleSectionEntity where articleId =?1")
    List<Integer> getSectionIdListByArticleId(String articleId);
}
