package dasturlash.uz.services;

import dasturlash.uz.entities.ArticleEntity;
import dasturlash.uz.entities.CategoryEntity;
import dasturlash.uz.entities.SectionEntity;
import dasturlash.uz.enums.Lang;
import dasturlash.uz.repository.ArticleRepository;
import dasturlash.uz.request.ArticleRequestDTO;
import dasturlash.uz.responseDto.ArticleResponseDTO;
import dasturlash.uz.responseDto.CategoryResponseDTO;
import dasturlash.uz.responseDto.SectionResponseDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final CategoryService categoryService;
    private final SectionService sectionService;
    private final ArcSecService arcSecService;
    private final ArcCateService arcCateService;

    public ArticleService(ArticleRepository articleRepository, CategoryService categoryService,
                          SectionService sectionService, ArcCateService arcCateService, ArcSecService arcSecService) {
        this.articleRepository = articleRepository;
        this.arcSecService = arcSecService;
        this.arcCateService = arcCateService;
        this.categoryService = categoryService;
        this.sectionService = sectionService;
    }

    public List<CategoryResponseDTO> getCategories(Lang lang) {
        return categoryService.getListByLang(lang);
    }

    public List<SectionResponseDTO> getSections(Lang lang) {
        return sectionService.getListLang(lang);
    }

    public ArticleResponseDTO create(@Valid ArticleRequestDTO dto) {
        ArticleEntity entity = new ArticleEntity();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());
        entity.setImageId(dto.getImageId());
        entity.setRegionId(dto.getRegionId());

        articleRepository.save(entity);

        if (!dto.getSectionList().isEmpty()) {
            for (Integer sectionId : dto.getSectionList()) {
                SectionEntity sectionEntity = sectionService.getEntityById(sectionId);
                arcSecService.create(sectionEntity, entity);
            }
        }

        if (!dto.getCategoryList().isEmpty()) {
            for (Integer categoryId : dto.getSectionList()) {
                CategoryEntity categoryEntity = categoryService.getEntityById(categoryId);
                arcCateService.create(categoryEntity, entity);
            }
        }

        return toDto(entity);
    }

    private ArticleResponseDTO toDto(ArticleEntity entity) {
        ArticleResponseDTO dto = new ArticleResponseDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setContent(entity.getContent());
        dto.setImageId(entity.getImageId());
        dto.setRegionId(entity.getRegionId());

        List<CategoryEntity> category = articleRepository.getCategory();
        List<SectionEntity> section = articleRepository.getSection();

        dto.setCategoryList(categoryService.toResponse(category));
        dto.setSectionList(sectionService.toResponse(section));

        return dto;
    }
}