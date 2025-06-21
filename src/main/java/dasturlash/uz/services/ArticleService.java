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
import dasturlash.uz.request.ArtReqPagination;
import dasturlash.uz.request.ArticleRequestDTO;
import dasturlash.uz.responseDto.ArticleResponseDTO;
import dasturlash.uz.responseDto.CategoryResponseDTO;
import dasturlash.uz.responseDto.SectionResponseDTO;
import dasturlash.uz.services.connectedServices.ArcCateService;
import dasturlash.uz.services.connectedServices.ArcSecService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageImpl;
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

    @Transactional
    public ArticleResponseDTO create(ArticleRequestDTO dto) {
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

    private ArticleResponseDTO toDto(ArticleEntity entity) {
        ArticleResponseDTO dto = new ArticleResponseDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setContent(entity.getContent());
        dto.setImageId(entity.getImageId());
        dto.setRegionId(entity.getRegionId());
        dto.setModeratorId(entity.getModeratorId());
        dto.setPublisherId(entity.getPublisherId());
        dto.setStatus(entity.getStatus());
        dto.setViewCount(entity.getViewCount());
        dto.setSharedCount(entity.getSharedCount());
        dto.setReadTime(entity.getReadTime());
        dto.setPublishedDate(entity.getPublishedDate());

        dto.setCategoryList(categoryService.toResponse(articleRepository.getCategory()));
        dto.setSectionList(sectionService.toResponse(articleRepository.getSection()));

        return dto;
    }

    @Transactional
    public ArticleResponseDTO update(Integer id, ArticleRequestDTO dto) {
        ArticleEntity article = articleRepository.findById(id)
                .orElseThrow(() -> new AppBadExp("Maqola topilmadi: " + id));

        article.setTitle(dto.getTitle());
        article.setDescription(dto.getDescription());
        article.setContent(dto.getContent());
        article.setImageId(dto.getImageId());
        article.setRegionId(dto.getRegionId());
        article.setReadTime(dto.getReadTime());
        article.setPublishedDate(dto.getPublishedDate());

        if (dto.getSectionList() != null && !dto.getSectionList().isEmpty())
            arcSecService.update(id, dto.getSectionList());

        if (dto.getCategoryList() != null && !dto.getCategoryList().isEmpty())
            arcCateService.update(id, dto.getCategoryList());

        return toDto(article);
    }


    public ArticleResponseDTO getArticle(int id) {
        ArticleEntity article = articleRepository.findById(id)
                .orElseThrow(() -> new AppBadExp("Article not found"));

        return toDto(article);
    }

    @Transactional
    public Boolean delete(int id) {
        ArticleEntity article = articleRepository.findById(id)
                .orElseThrow(() -> new AppBadExp("Maqola topilmadi: " + id));
        article.setVisible(false);
        return true;
    }

    @Transactional
    public Boolean changeStatus(int id, ArticleStatus status) {
        ArticleEntity byId = articleRepository.findById(id)
                .orElseThrow(() -> new AppBadExp("Maqola topilmadi: " + id));
        byId.setStatus(status);
        return true;
    }

    public PageImpl<ArticleResponseDTO> getLastNArticles(int page, int size, ArtReqPagination request) {
        String className = request.getClassName();
        int classId = request.getClassId();

        List<?> entities;
        if (!className.isBlank() && className.equals("SectionEntity")) {
            entities = articleRepository.getArticleEntitiesBySectionId(classId,request.getLimit());
        } else if (!className.isBlank() && className.equals("CategoryEntity")) {
            entities = articleRepository.getArticleEntitiesByCategoryId(classId,request.getLimit());
        }
        return null;
    }

}