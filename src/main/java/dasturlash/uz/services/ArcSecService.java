package dasturlash.uz.services;

import dasturlash.uz.dto.SectionDTO;
import dasturlash.uz.entities.ArticleEntity;
import dasturlash.uz.entities.ArticleSectionEntity;
import dasturlash.uz.entities.SectionEntity;
import dasturlash.uz.exp.AppBadExp;
import dasturlash.uz.repository.ArcSecRepo;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArcSecService {

    private final ArcSecRepo arcSecRepo;
    private final SectionService sectionService;

    public ArcSecService(SectionService sectionService, ArcSecRepo arcSecRepo) {
        this.sectionService = sectionService;
        this.arcSecRepo = arcSecRepo;
    }

    public String create(SectionEntity sectionEntity, ArticleEntity entity) {
        ArticleSectionEntity articleSectionEntity = new ArticleSectionEntity();
        articleSectionEntity.setSectionId(sectionEntity.getId());
        articleSectionEntity.setSection(sectionEntity);
        articleSectionEntity.setArticleId(Integer.valueOf(entity.getId()));
        articleSectionEntity.setArticle(entity);

        arcSecRepo.save(articleSectionEntity);
        return "Chiki puki";
    }

    public void update(Integer articleId, @NotNull List<SectionDTO> sectionIds) {
        List<ArticleSectionEntity> byId = arcSecRepo.findByArticleId(articleId);
        if (byId.isEmpty()) throw new AppBadExp("Article Section Not Found");

        ArticleEntity articleEntity = byId.getFirst().getArticle();

        byId.forEach(ars -> sectionIds.remove(ars.getSectionId()));

        for (SectionDTO sectionId : sectionIds) {
            SectionEntity entityById = sectionService.getEntityById(sectionId.getId());
            create(entityById, articleEntity);
        }
    }
}
