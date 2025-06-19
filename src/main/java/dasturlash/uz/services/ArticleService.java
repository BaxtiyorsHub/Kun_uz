package dasturlash.uz.services;

import dasturlash.uz.dto.CategoryDTO;
import dasturlash.uz.dto.SectionDTO;
import dasturlash.uz.entities.ArticleEntity;
import dasturlash.uz.entities.CategoryEntity;
import dasturlash.uz.entities.SectionEntity;
import dasturlash.uz.enums.ArticleStatus;
import dasturlash.uz.enums.Lang;
import dasturlash.uz.exp.AppBadExp;
import dasturlash.uz.repository.ArticleRepository;
import dasturlash.uz.responseDto.ArticleResponseDTO;
import dasturlash.uz.responseDto.CategoryResponseDTO;
import dasturlash.uz.responseDto.SectionResponseDTO;
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

    public dasturlash.uz.responseDto.ArticleResponseDTO create(ArticleResponseDTO dto) {
        ArticleEntity entity = new ArticleEntity();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());
        entity.setImageId(dto.getImageId());
        entity.setRegionId(dto.getRegionId());

        articleRepository.save(entity);

        if (!dto.getSectionList().isEmpty()) {
            for (SectionDTO sectionId : dto.getSectionList()) {
                SectionEntity sectionEntity = sectionService.getEntityById(sectionId.getId());
                arcSecService.create(sectionEntity, entity);
            }
        }

        if (!dto.getCategoryList().isEmpty()) {
            for (CategoryDTO categoryId : dto.getCategoryList()) {
                CategoryEntity categoryEntity = categoryService.getEntityById(categoryId.getId());
                arcCateService.create(categoryEntity, entity);
            }
        }

        return toDto(entity);
    }

    private dasturlash.uz.responseDto.ArticleResponseDTO toDto(ArticleEntity entity) {
        dasturlash.uz.responseDto.ArticleResponseDTO dto = new dasturlash.uz.responseDto.ArticleResponseDTO();
        dto.setId(Integer.valueOf(entity.getId()));
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setContent(entity.getContent());
        dto.setImageId(entity.getImageId());
        dto.setRegionId(String.valueOf(entity.getRegionId()));

        List<CategoryEntity> category = articleRepository.getCategory();
        List<SectionEntity> section = articleRepository.getSection();

        dto.setCategoryList(categoryService.toResponse(category));
        dto.setSectionList(sectionService.toResponse(section));

        return dto;
    }

    public dasturlash.uz.responseDto.ArticleResponseDTO update(Integer id, ArticleResponseDTO dto) {
        ArticleEntity article = articleRepository.findById(id)
                .orElseThrow(() -> new AppBadExp("Article not found"));

        if (!dto.getTitle().equals(article.getTitle())) article.setTitle(dto.getTitle());
        if (!dto.getDescription().equals(article.getDescription())) article.setDescription(dto.getDescription());
        if (!dto.getContent().equals(article.getContent())) article.setContent(dto.getContent());
        if (!dto.getImageId().equals(article.getImageId())) article.setImageId(dto.getImageId());
        if (!dto.getRegionId().equals(article.getRegionId())) article.setRegionId(dto.getRegionId());

        articleRepository.save(article);

        if (!dto.getSectionList().isEmpty()) arcSecService.update(id, dto.getSectionList());
        if (!dto.getCategoryList().isEmpty()) arcCateService.update(id,dto.getCategoryList());

        return toDto(article);
    }

    public dasturlash.uz.responseDto.ArticleResponseDTO getArticle(int id) {
        ArticleEntity article = articleRepository.findById(id)
                .orElseThrow(() -> new AppBadExp("Article not found"));

        return toDto(article);
    }

    public Boolean delete(int id) {
        ArticleEntity byId = articleRepository.getById(id);
        byId.setVisible(false);
        articleRepository.save(byId);
        return true;
    }

    public Boolean changeStatus(int id, ArticleStatus status) {
        ArticleEntity byId = articleRepository.getById(id);
        byId.setStatus(status);
        articleRepository.save(byId);
        return true;
    }
}