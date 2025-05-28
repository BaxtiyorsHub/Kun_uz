package dasturlash.uz.services;

import dasturlash.uz.entities.ArticleEntity;
import dasturlash.uz.entities.ArticleSectionEntity;
import dasturlash.uz.entities.SectionEntity;
import dasturlash.uz.repository.ArcSecRepo;
import org.springframework.stereotype.Service;

@Service
public class ArcSecService {

    private final ArcSecRepo arcSecRepo;

    public ArcSecService(ArcSecRepo arcSecRepo) {
        this.arcSecRepo = arcSecRepo;
    }

    public String create(SectionEntity sectionEntity, ArticleEntity entity) {
        ArticleSectionEntity articleSectionEntity = new ArticleSectionEntity();
        articleSectionEntity.setSectionId(sectionEntity.getId());
        articleSectionEntity.setSection(sectionEntity);
        articleSectionEntity.setArticleId(entity.getId());
        articleSectionEntity.setArticle(entity);

        arcSecRepo.save(articleSectionEntity);
        return "Chiki puki";
    }
}
