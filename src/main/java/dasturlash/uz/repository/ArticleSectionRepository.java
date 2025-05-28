package dasturlash.uz.repository;

import dasturlash.uz.entities.ArticleSectionEntity;
import dasturlash.uz.entities.SectionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleSectionRepository extends CrudRepository<ArticleSectionEntity, Integer> {

    @Query("select se from ArticleSectionEntity ars join ars.section se where ars.visible = true and se.visible = true")
    List<SectionEntity> findSections();

}
