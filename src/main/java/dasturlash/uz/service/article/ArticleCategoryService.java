package dasturlash.uz.service.article;

import dasturlash.uz.dto.CategoryDTO;
import dasturlash.uz.entity.article.ArticleCategoryEntity;
import dasturlash.uz.repository.article.ArticleCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleCategoryService {
    @Autowired
    private ArticleCategoryRepository articleCategoryRepository;

    public void merge(String articleId, List<CategoryDTO> dtoList) {
        List<Integer> newList = dtoList.stream().map(CategoryDTO::getId).toList();

        List<Integer> oldList = articleCategoryRepository.getCategoryIdListByArticleId(articleId);
        newList.stream().filter(n -> !oldList.contains(n)).forEach(pe -> create(articleId, pe)); // create
        oldList.stream().filter(old -> !newList.contains(old)).forEach(pe -> articleCategoryRepository.deleteByCategoryIdAndArticleId(articleId, pe));
    }

    public void create(String articleId, Integer categoryId) {
        ArticleCategoryEntity entity = new ArticleCategoryEntity();
        entity.setArticleId(articleId);
        entity.setCategoryId(categoryId);
        articleCategoryRepository.save(entity);
    }
}
