package dasturlash.uz.service.article;

import dasturlash.uz.dto.SectionDTO;
import dasturlash.uz.entity.article.ArticleSectionEntity;
import dasturlash.uz.repository.article.ArticleSectionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleSectionService {
    private final ArticleSectionRepository articleSectionRepository;

    public ArticleSectionService(ArticleSectionRepository articleSectionRepository) {
        this.articleSectionRepository = articleSectionRepository;
    }

    public void merge(String articleId, List<SectionDTO> sectionList) {
        List<Integer> newList = sectionList.stream().map(SectionDTO::getId).toList();

        List<Integer> oldList = articleSectionRepository.getSectionIdListByArticleId(articleId);
        newList.stream().filter(n -> !oldList.contains(n)).forEach(sId -> create(articleId, sId));
        oldList.stream().filter(m -> !newList.contains(m)).forEach(sId -> articleSectionRepository.deleteBySectionIdAndArticleId(articleId,sId));
    }

    public void create(String articleId, Integer sectionId) {
        ArticleSectionEntity entity = new ArticleSectionEntity();
        entity.setArticleId(articleId);
        entity.setSectionId(sectionId);
        articleSectionRepository.save(entity);
    }
}
