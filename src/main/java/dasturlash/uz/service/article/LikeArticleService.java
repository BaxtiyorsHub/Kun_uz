package dasturlash.uz.service.article;

import dasturlash.uz.entity.article.ArticleEntity;
import dasturlash.uz.entity.article.LikeArticleEntity;
import dasturlash.uz.entity.profile.ProfileEntity;
import dasturlash.uz.enums.LikeStatus;
import dasturlash.uz.repository.article.LikeArticleRepository;
import dasturlash.uz.service.ProfileService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeArticleService {
    @Autowired
    private LikeArticleRepository likeArticleRepository;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ProfileService profileService;

    @Transactional
    public Boolean like(String id, Integer profileId) {
        try {
            ArticleEntity article = articleService.getArticleById(id);
            ProfileEntity profile = profileService.get(profileId);

            LikeArticleEntity result = likeArticleRepository.getLikeArticleEntityByArticleId(id);

            if (result != null && result.getStatus() == LikeStatus.DISLIKE) {
                result.setStatus(LikeStatus.LIKE);
                articleService.changeLikeCount(article);
                articleService.removeDisLikeCount(article);
                return true;
            }

            LikeArticleEntity likeArticleEntity = new LikeArticleEntity();
            likeArticleEntity.setArticleId(article.getId());
            likeArticleEntity.setArticle(article);
            likeArticleEntity.setStatus(LikeStatus.LIKE);
            likeArticleEntity.setProfileId(profile.getId());
            likeArticleEntity.setProfile(profile);

            articleService.changeLikeCount(article);
            likeArticleRepository.save(likeArticleEntity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Transactional
    public Boolean dislike(String id, Integer profileId) {
        try {
            ArticleEntity article = articleService.getArticleById(id);
            ProfileEntity profile = profileService.get(profileId);

            LikeArticleEntity result = likeArticleRepository.getLikeArticleEntityByArticleId(id);
            if (result != null && result.getStatus() == LikeStatus.LIKE) {
                result.setStatus(LikeStatus.DISLIKE);
                articleService.changeDislikeCount(article);
                articleService.removeLikeCount(article);
                return true;
            }

            LikeArticleEntity likeArticle = new LikeArticleEntity();
            likeArticle.setArticleId(article.getId());
            likeArticle.setArticle(article);
            likeArticle.setStatus(LikeStatus.DISLIKE);
            likeArticle.setProfileId(profile.getId());
            likeArticle.setProfile(profile);

            articleService.changeDislikeCount(article);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Transactional
    public Boolean remove(String id) {
        try {
            LikeArticleEntity result = likeArticleRepository.getLikeArticleEntityByArticleId(id);
            if (result != null && result.getStatus() != null) {
                likeArticleRepository.delete(result);
            }
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
