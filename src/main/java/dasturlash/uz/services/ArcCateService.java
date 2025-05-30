package dasturlash.uz.services;

import dasturlash.uz.entities.*;
import dasturlash.uz.exp.AppBadExp;
import dasturlash.uz.repository.ArcCateRepo;
import dasturlash.uz.request.ArticleRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArcCateService {

    private final ArcCateRepo arcCateRepo;
    private final CategoryService categoryService;

    public ArcCateService(ArcCateRepo arcCateRepo, CategoryService categoryService)
    {
        this.arcCateRepo = arcCateRepo;
        this.categoryService = categoryService;
    }

    public String create(CategoryEntity categoryEntity, ArticleEntity entity) {
        ArticleCategoryEntity articleCategoryEntity = new ArticleCategoryEntity();
        articleCategoryEntity.setCategoryId(categoryEntity.getId());
//        articleCategoryEntity.setCategory(categoryEntity);
//        articleCategoryEntity.setArticle(entity);
        articleCategoryEntity.setArticleId(entity.getId());

        arcCateRepo.save(articleCategoryEntity);
        return "Chiki puki";
    }

    public void update(Integer articleId, List<Integer> categoryIds) {

        List<ArticleCategoryEntity> byId = arcCateRepo.findByArticleId(articleId);
        if (byId.isEmpty()) throw new AppBadExp("Article Section Not Found");

        ArticleEntity articleEntity = byId.getFirst().getArticle();

        byId.forEach(ars -> categoryIds.remove(ars.getCategoryId()));

        for (Integer sectionId : categoryIds) {
            CategoryEntity entityById = categoryService.getEntityById(sectionId);
            create(entityById, articleEntity);
        }

    }
}
