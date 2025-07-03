package dasturlash.uz.service.article;

import dasturlash.uz.dto.FilterResultDTO;
import dasturlash.uz.dto.article.ArticleDTO;
import dasturlash.uz.dto.article.ArticleFilterDTO;
import dasturlash.uz.dto.article.ArticleInfoDTO;
import dasturlash.uz.dto.article.ArticleRequestDTO;
import dasturlash.uz.entity.article.ArticleEntity;
import dasturlash.uz.enums.AppLanguageEnum;
import dasturlash.uz.enums.ArticleStatus;
import dasturlash.uz.exceptions.AppBadException;
import dasturlash.uz.mapper.ArticleFullInfo;
import dasturlash.uz.mapper.ArticleShortInfo;
import dasturlash.uz.repository.CustomRepository.ArticleCustomRepository;
import dasturlash.uz.repository.article.ArticleRepository;
import dasturlash.uz.service.AttachService;
import dasturlash.uz.service.CategoryService;
import dasturlash.uz.service.RegionService;
import dasturlash.uz.service.SectionService;
import dasturlash.uz.util.SpringSecurityUtil;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleCategoryService articleCategoryService;
    private final ArticleSectionService articleSectionService;
    private final AttachService attachService;
    private final ArticleCustomRepository articleCustomRepository;
    private final RegionService regionService;
    private final SectionService sectionService;
    private final CategoryService categoryService;

    public ArticleService(ArticleRepository articleRepository, ArticleCategoryService articleCategoryService, ArticleSectionService articleSectionService, AttachService attachService, ArticleCustomRepository articleCustomRepository, RegionService regionService, SectionService sectionService, CategoryService categoryService) {
        this.articleRepository = articleRepository;
        this.articleCategoryService = articleCategoryService;
        this.articleSectionService = articleSectionService;
        this.attachService = attachService;
        this.articleCustomRepository = articleCustomRepository;
        this.regionService = regionService;
        this.sectionService = sectionService;
        this.categoryService = categoryService;
    }

    private void toEntity(ArticleDTO dto, ArticleEntity entity) {
        entity.setTitleUz(dto.getTitle());
        entity.setTitleRu(dto.getTitleRu());
        entity.setTitleEn(dto.getTitleEn());
        entity.setDescriptionUz(dto.getDescription());
        entity.setDescriptionRu(dto.getDescriptionRu());
        entity.setDescriptionEn(dto.getDescriptionEn());
        entity.setContentUz(dto.getContent());
        entity.setContentRu(dto.getContentRu());
        entity.setContentEn(dto.getContentEn());
        entity.setImageId(dto.getImageId());
        entity.setRegionId(dto.getRegionId());
        entity.setReadTime(dto.getReadTime());
    }

    private ArticleInfoDTO toDTO(ArticleEntity entity) {
        ArticleInfoDTO dto = new ArticleInfoDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitleUz());
        dto.setTitleRu(entity.getTitleRu());
        dto.setTitleEn(entity.getTitleEn());
        dto.setDescription(entity.getDescriptionUz());
        dto.setDescriptionRu(entity.getDescriptionRu());
        dto.setDescriptionEn(entity.getDescriptionEn());
        dto.setContent(entity.getContentUz());
        dto.setContentRu(entity.getContentRu());
        dto.setContentEn(entity.getContentEn());
        dto.setSharedCount(entity.getSharedCount());
        dto.setReadTime(entity.getReadTime());
        dto.setViewCount(entity.getViewCount());
        dto.setStatus(entity.getStatus());
        dto.setImageId(entity.getImageId());
        dto.setRegionId(entity.getRegionId());
        dto.setPublishedDate(entity.getPublishedDate());

        return dto;
    }

    private ArticleEntity findEntityFromDb(String id) {
        Optional<ArticleEntity> entity = articleRepository.findByIdAndVisibleIsTrue(id);
        if (entity.isEmpty()) throw new AppBadException("Article not found");
        return entity.get();
    }

    private ArticleInfoDTO toFullDTO(ArticleFullInfo entity) {
        ArticleInfoDTO dto = new ArticleInfoDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setContent(entity.getContent());
        dto.setSharedCount(entity.getSharedCount());
        dto.setViewCount(entity.getViewCount());
        dto.setLikes(entity.getLikeCount());
        dto.setImage(attachService.openDTO(entity.getImageId()));
        dto.setRegion(regionService.openDTO(entity.getRegionId()));
        dto.setPublishedDate(entity.getPublishedDate());

        dto.setSectionList(sectionService.parseSectionList(entity.getSectionList()));
        dto.setCategoryList(categoryService.parseCategoryList(entity.getCategoryList()));
        return dto;
    }

    private ArticleInfoDTO toDTO(ArticleShortInfo mapper) {
        ArticleInfoDTO dto = new ArticleInfoDTO();
        dto.setId(mapper.getId());
        dto.setTitle(mapper.getTitle());
        dto.setDescription(mapper.getDescription());
        dto.setImage(attachService.openDTO(mapper.getId()));
        dto.setPublishedDate(mapper.getPublishedDate());

        changeViewCount(mapper.getId());
        return dto;
    }

    @Transactional
    public ArticleInfoDTO createArticle(@Valid ArticleDTO createDTO) {
        ArticleEntity entity = new ArticleEntity();
        toEntity(createDTO, entity);

        // Set default values
        entity.setVisible(true);
        entity.setViewCount(0L);
        entity.setSharedCount(0L);
        entity.setModeratorId(SpringSecurityUtil.currentProfileId());
        ArticleEntity savedEntity = articleRepository.save(entity);
        // category -> merge
        articleCategoryService.merge(savedEntity.getId(), createDTO.getCategoryList());
        // section -> merge
        articleSectionService.merge(savedEntity.getId(), createDTO.getSectionList());
        // return
        ArticleInfoDTO dto = toDTO(entity);
        dto.setCategoryList(createDTO.getCategoryList());
        dto.setSectionList(createDTO.getSectionList());
        return dto;
    }

    @Transactional
    public ArticleInfoDTO updateArticle(String articleId, @Valid ArticleDTO dto) {
        ArticleEntity savedEntity = findEntityFromDb(articleId);

        savedEntity.setTitleUz(dto.getTitle());
        savedEntity.setTitleRu(dto.getTitleRu());
        savedEntity.setTitleEn(dto.getTitleEn());
        savedEntity.setDescriptionUz(dto.getDescription());
        savedEntity.setDescriptionRu(dto.getDescriptionRu());
        savedEntity.setDescriptionEn(dto.getDescriptionEn());
        savedEntity.setContentUz(dto.getContent());
        savedEntity.setContentRu(dto.getContentRu());
        savedEntity.setContentEn(dto.getContentEn());
        if (!dto.getImageId().isBlank()) {
            attachService.delete(savedEntity.getImageId());
            savedEntity.setImageId(dto.getImageId());
        }
        savedEntity.setRegionId(dto.getRegionId());
        savedEntity.setReadTime(dto.getReadTime());

        if (dto.getCategoryList() != null && !dto.getCategoryList().isEmpty()) {
            articleCategoryService.merge(savedEntity.getId(), dto.getCategoryList());
        }
        if (dto.getSectionList() != null && !dto.getSectionList().isEmpty()) {
            articleSectionService.merge(savedEntity.getId(), dto.getSectionList());
        }

        ArticleInfoDTO response = toDTO(savedEntity);
        response.setCategoryList(dto.getCategoryList());
        response.setSectionList(dto.getSectionList());
        return response;
    }

    @Transactional
    public String deleteArticle(String id) {
        findEntityFromDb(id).setVisible(false);
        return "Article deleted successfully";
    }

    @Transactional
    public String changeArticleStatus(String id, ArticleStatus status) {
        findEntityFromDb(id).setStatus(status);
        return "Article status changed successfully";
    }

    @Transactional
    public Page<ArticleInfoDTO> getLastNArticles(int page, int size, @Valid ArticleRequestDTO request) {
        Pageable pageable = PageRequest.of(page, size);

        List<ArticleShortInfo> results;
        List<ArticleInfoDTO> response = new LinkedList<>();

        if (request.getSectionId() != null) {
            results = articleRepository
                    .find_n_ArticlesBySectionId(
                            request.getSectionId(),
                            request.getStatus().name(),
                            request.getLang().name(),
                            request.getN());
            results.forEach(entity -> response.add(toDTO(entity)));
            return new PageImpl<>(response, pageable, response.size());
        }
        return getLastNArticlesByCategoryId(page, size, request);
    }

    public boolean changeViewCount(String id) {
        try {
            Optional<ArticleEntity> articleEntityById = articleRepository.findByIdAndVisibleIsTrue(id);

            articleEntityById.get().setViewCount(articleEntityById.get().getViewCount() + 1);
            articleRepository.save(articleEntityById.get());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean shareCount(String id) {
        try {
            Optional<ArticleEntity> articleEntityById = articleRepository.findByIdAndVisibleIsTrue(id);
            articleEntityById.get().setSharedCount(articleEntityById.get().getSharedCount() + 1);
            articleRepository.save(articleEntityById.get());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Transactional
    public Page<ArticleInfoDTO> getLastNArticlesByCategoryId(int page, int size, @Valid ArticleRequestDTO request) {
        Pageable pageable = PageRequest.of(page, size);

        List<ArticleShortInfo> results;
        List<ArticleInfoDTO> response = new LinkedList<>();

        if (request.getCategoryId() != null) {
            results = articleRepository.find_n_ArticlesByCategoryId(
                    request.getCategoryId(),
                    request.getStatus().name(),
                    request.getLang().name(),
                    request.getN());
            results.forEach(entity -> response.add(toDTO(entity)));
        }
        return new PageImpl<>(response, pageable, response.size());
    }

    @Transactional
    public Page<ArticleInfoDTO> getArticlesByRegion(Integer regionId, int page, int size, AppLanguageEnum language) {
        Pageable pageable = PageRequest.of(page, size);

        List<ArticleFullInfo> results;
        List<ArticleInfoDTO> response = new LinkedList<>();

        if (regionId != null) {
            results = articleRepository.findByRegionId(regionId, language.name());
            response = results.stream().map(this::toFullDTO).toList();
        }

        return new PageImpl<>(response, pageable, response.size());
    }

    @Transactional
    public List<ArticleInfoDTO> most4ReadArticles(String articleId, AppLanguageEnum language) {
        List<ArticleShortInfo> result = articleRepository.findMostRead4Articles(articleId, language.name());

        return result.stream().map(this::toDTO).toList();
    }

    @Transactional
    public List<ArticleInfoDTO> get4ArticleBySectionId(String articleId, int sectionId, AppLanguageEnum language) {
        List<ArticleShortInfo> result = articleRepository.find_n_ArticlesBySectionId(sectionId, "PUBLISHED", language.name(), 4);
        result.removeIf(entity -> entity.getId().equals(articleId));

        return result.stream().map(this::toDTO).toList();
    }

    @Transactional
    public Page<ArticleInfoDTO> lastest12Articles(int page, int size, List<String> articleId, AppLanguageEnum language) {
        Pageable pageable = PageRequest.of(page, size);

        List<ArticleInfoDTO> response = articleRepository.find12Articles(articleId, language.name())
                .stream()
                .map(this::toDTO)
                .toList();
        return new PageImpl<>(response, pageable, response.size());
    }

    @Transactional
    public ArticleEntity getArticleById(String id) {
        return articleRepository.findByIdAndVisibleIsTrue(id).get();
    }

    public void changeDislikeCount(ArticleEntity article) {
        article.setLikes(article.getLikes() + 1);
        articleRepository.save(article);
    }

    public void changeLikeCount(ArticleEntity entity) {
        entity.setLikes(entity.getLikes() + 1);
        articleRepository.save(entity);
    }

    @Transactional
    public Page<ArticleInfoDTO> filter(ArticleFilterDTO filter, int page, int size) { // 1, 100
        FilterResultDTO<Object[]> filterResult = articleCustomRepository.filter(filter, page, size);
        List<ArticleInfoDTO> articleList = new LinkedList<>();
        for (Object[] obj : filterResult.getContent()) {
            ArticleInfoDTO article = new ArticleInfoDTO();
            // a.id, a.title, a.description, a.publishedDate,a.imageId
            article.setId((String) obj[0]);
            article.setTitle((String) obj[1]);
            article.setDescription((String) obj[2]);
            article.setPublishedDate((LocalDateTime) obj[3]);
            if (obj[4] != null) {
                article.setImage(attachService.openDTO((String) obj[4]));
            }
            articleList.add(article);
        }
        return new PageImpl<>(articleList, PageRequest.of(page, size), filterResult.getTotal());
    }

    public ArticleInfoDTO openDTO(String articleId) {
        ArticleInfoDTO dto = new ArticleInfoDTO();
        ArticleEntity entity = articleRepository.findByIdAndVisibleIsTrue(articleId).orElseThrow(() -> new AppBadException("Article not found"));

        dto.setId(entity.getId());
        dto.setTitle(entity.getTitleUz());
        dto.setTitleRu(entity.getTitleRu());
        dto.setTitleEn(entity.getTitleEn());

        return dto;
    }

    public void removeLikeCount(ArticleEntity article) {
        ArticleEntity result = getArticleById(article.getId());
        result.setLikes(result.getLikes() - 1);
        articleRepository.save(result);
    }

    public void removeDisLikeCount(ArticleEntity article) {
        ArticleEntity articleById = getArticleById(article.getId());
        articleById.setDislikes(articleById.getDislikes() - 1);
        articleRepository.save(articleById);
    }
}
