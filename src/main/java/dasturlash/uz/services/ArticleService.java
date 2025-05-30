package dasturlash.uz.services;

import dasturlash.uz.entities.ArticleEntity;
import dasturlash.uz.entities.CategoryEntity;
import dasturlash.uz.entities.SectionEntity;
import dasturlash.uz.enums.ArtStatus;
import dasturlash.uz.enums.Lang;
import dasturlash.uz.exp.AppBadExp;
import dasturlash.uz.repository.ArticleRepository;
import dasturlash.uz.request.ArticleRequestDTO;
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

    public ArticleResponseDTO create(ArticleRequestDTO dto) {
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

    public ArticleResponseDTO update(Integer id, ArticleRequestDTO dto) {
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

    public ArticleResponseDTO getArticle(int id) {
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

    public Boolean changeStatus(int id, ArtStatus status) {
        ArticleEntity byId = articleRepository.getById(id);
        byId.setStatus(status);
        articleRepository.save(byId);
        return true;
    }
}