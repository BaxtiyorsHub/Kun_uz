package dasturlash.uz.service.article;

import dasturlash.uz.dto.SavedArticleDTO;
import dasturlash.uz.entity.article.ArticleEntity;
import dasturlash.uz.entity.article.SavedArticleEntity;
import dasturlash.uz.entity.profile.ProfileEntity;
import dasturlash.uz.repository.article.SavedArticleRepository;
import dasturlash.uz.service.ProfileService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.orm.jpa.EntityManagerFactoryBuilderCustomizer;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SavedArticleService {
    private final SavedArticleRepository savedArticleRepository;
    private final ArticleService articleService;
    private final ProfileService profileService;

    public Boolean save(String articleId, Integer id) {
        try {
            ArticleEntity article = articleService.getArticleById(articleId);
            ProfileEntity profile = profileService.get(id);

            Optional<SavedArticleEntity> result = savedArticleRepository.findByArticle_idAndProfile_id(articleId, id);
            if (result.isPresent()) throw new RuntimeException("You already saved this article");

            SavedArticleEntity savedArticle = new SavedArticleEntity();
            savedArticle.setArticle_id(articleId);
            savedArticle.setArticle(article);
            savedArticle.setProfile_id(id);
            savedArticle.setProfile(profile);
            savedArticleRepository.save(savedArticle);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Transactional
    public Boolean delete(String articleId, Integer i) {
        savedArticleRepository.deleteByArticle_idAndProfile_id(articleId, i);
        return true;
    }

    public List<SavedArticleDTO> getByProfileId(Integer i) {
        return savedArticleRepository.findByProfile_id(i).stream().map(this::toDTO).toList();
    }

    private SavedArticleDTO toDTO(SavedArticleEntity entity) {
        SavedArticleDTO dto = new SavedArticleDTO();
        dto.setId(entity.getId());
        dto.setArticle(articleService.openDTO(entity.getArticle_id()));
        dto.setSavedDate(entity.getCreated_date());
        return dto;
    }
}
