package dasturlash.uz.services;

import dasturlash.uz.entities.ArticleCategoryEntity;
import dasturlash.uz.entities.ArticleEntity;
import dasturlash.uz.entities.CategoryEntity;
import dasturlash.uz.repository.ArcCateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArcCateService {

    private final ArcCateRepo arcCateRepo;

    public ArcCateService(ArcCateRepo arcCateRepo) {
        this.arcCateRepo = arcCateRepo;
    }

    public String create(CategoryEntity categoryEntity, ArticleEntity entity) {
        ArticleCategoryEntity articleCategoryEntity = new ArticleCategoryEntity();
        articleCategoryEntity.setCategoryId(categoryEntity.getId());
        articleCategoryEntity.setCategory(categoryEntity);
        articleCategoryEntity.setArticle(entity);
        articleCategoryEntity.setArticleId(categoryEntity.getId());

        arcCateRepo.save(articleCategoryEntity);
        return "Chiki puki";
    }
}
