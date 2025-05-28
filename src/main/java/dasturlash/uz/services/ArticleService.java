package dasturlash.uz.services;

import dasturlash.uz.entities.ArticleEntity;
import dasturlash.uz.entities.CategoryEntity;
import dasturlash.uz.entities.SectionEntity;
import dasturlash.uz.enums.Lang;
import dasturlash.uz.exp.AppBadExp;
import dasturlash.uz.repository.ArticleRepository;
import dasturlash.uz.request.ArticleRequestDTO;
import dasturlash.uz.responseDto.ArticleEntitiesResponseDTO;
import dasturlash.uz.responseDto.ArticleResponseDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleSectionService articleSectionService;
    private final ArticleCategoryService articleCategoryService;

    public ArticleService(ArticleSectionService articleSectionService, ArticleRepository articleRepository, ArticleCategoryService articleCategoryService) {
        this.articleRepository = articleRepository;
        this.articleSectionService = articleSectionService;
        this.articleCategoryService = articleCategoryService;
    }

    public ArticleResponseDTO create(ArticleRequestDTO request) {
        if (request == null) throw new AppBadExp("article null request");

        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setTitle(request.getTitle());
        articleEntity.setDescription(request.getDescription());
        articleEntity.setContent(request.getContent());
        articleEntity.setImageId(request.getImageId());
        articleEntity.setRegionId(request.getRegionId());

        if (request.getSectionList() != null) {
            articleSectionService.create(request.getSectionList(), articleEntity);
        }
        if (request.getCategoryList() != null) {
            articleCategoryService.create(request.getCategoryList(), articleEntity);
        }

        articleRepository.save(articleEntity);

        return toDto(articleEntity);
    }

    private ArticleResponseDTO toDto(ArticleEntity articleEntity) {
        ArticleResponseDTO response = new ArticleResponseDTO();
        response.setTitle(articleEntity.getTitle());
        response.setDescription(articleEntity.getDescription());
        response.setContent(articleEntity.getContent());
        response.setRegionId(articleEntity.getRegionId());
        response.setImageId(articleEntity.getImageId());

        return response;
    }

    public List<ArticleEntitiesResponseDTO> getCategories(Lang lang) {
        List<CategoryEntity> entities = articleCategoryService.articleCategories();
        List<ArticleEntitiesResponseDTO> response = new ArrayList<>();
        for (CategoryEntity e : entities) {
            ArticleEntitiesResponseDTO responseDTO = new ArticleEntitiesResponseDTO();
            responseDTO.setId(e.getId());
            responseDTO.setKey(e.getKey());
            switch (lang) {
                case EN -> responseDTO.setNameEn(e.getNameEn());
                case RU -> responseDTO.setNameRu(e.getNameRu());
                default -> responseDTO.setNameUz(e.getNameUz());
            }
            response.add(responseDTO);
        }
        return response;
    }

    public List<ArticleEntitiesResponseDTO> getSections(Lang lang) {
        List<SectionEntity> entities = articleSectionService.articleSections();
        List<ArticleEntitiesResponseDTO> response = new ArrayList<>();
        for (SectionEntity e : entities) {
            ArticleEntitiesResponseDTO responseDTO = new ArticleEntitiesResponseDTO();
            responseDTO.setId(e.getId());
            responseDTO.setKey(e.getKey());
            switch (lang) {
                case EN -> responseDTO.setNameEn(e.getNameEn());
                case RU -> responseDTO.setNameRu(e.getNameRu());
                default -> responseDTO.setNameUz(e.getNameUz());
            }
            response.add(responseDTO);
        }
        return response;
    }
}