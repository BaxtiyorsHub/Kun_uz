package dasturlash.uz.services;

import dasturlash.uz.entities.ArticleCategoryEntity;
import dasturlash.uz.entities.ArticleEntity;
import dasturlash.uz.entities.CategoryEntity;
import dasturlash.uz.exp.AppBadExp;
import dasturlash.uz.repository.ArticleCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleCategoryService {

    private final ArticleCategoryRepository repository;

    public ArticleCategoryService(ArticleCategoryRepository repository) {
        this.repository = repository;
    }

    public String create(List<CategoryEntity> categoryList, ArticleEntity articleEntity) {
        if (categoryList == null ||
                categoryList.isEmpty() ||
                articleEntity == null) throw new AppBadExp("null or empty category list");

        for (CategoryEntity category : categoryList) {
            ArticleCategoryEntity articleCategoryEntity = new ArticleCategoryEntity();
            articleCategoryEntity.setArticleId(articleEntity.getId());
            articleCategoryEntity.setArticle(articleEntity);
            articleCategoryEntity.setCategoryId(category.getId());
            articleCategoryEntity.setCategory(category);

            repository.save(articleCategoryEntity);
        }

        return "Chiki puki";
    }

    public List<CategoryEntity> articleCategories() {
        return repository.getByVisibleIsTrue();
    }
}
