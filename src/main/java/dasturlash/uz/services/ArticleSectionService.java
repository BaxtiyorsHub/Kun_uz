package dasturlash.uz.services;

import dasturlash.uz.entities.ArticleEntity;
import dasturlash.uz.entities.ArticleSectionEntity;
import dasturlash.uz.entities.CategoryEntity;
import dasturlash.uz.entities.SectionEntity;
import dasturlash.uz.repository.ArticleSectionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleSectionService {

    private ArticleSectionRepository articleSectionRepository;

    public ArticleSectionService(ArticleSectionRepository articleSectionRepository) {
        this.articleSectionRepository = articleSectionRepository;
    }

    public String create(List<SectionEntity> sectionList, ArticleEntity articleEntity) {
        for (SectionEntity section : sectionList) {
            ArticleSectionEntity articleLinkEntity = new ArticleSectionEntity();
            articleLinkEntity.setArticleId(articleEntity.getId());
            articleLinkEntity.setArticle(articleEntity);
            articleLinkEntity.setSectionId(section.getId());
            articleLinkEntity.setSection(section);

            articleSectionRepository.save(articleLinkEntity);
        }

        return "Chiki puki";
    }

    public List<SectionEntity> articleSections() {
        return articleSectionRepository.findSections();
    }
}
