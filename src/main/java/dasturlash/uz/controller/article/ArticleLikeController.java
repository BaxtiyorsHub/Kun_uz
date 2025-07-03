package dasturlash.uz.controller.article;

import dasturlash.uz.service.article.LikeArticleService;
import dasturlash.uz.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/like")
public class ArticleLikeController {
    @Autowired
    private LikeArticleService likeArticleService;

    @PostMapping("/article/{id}")
    public ResponseEntity<Boolean> likeArticle(@PathVariable String id) {
        Integer i = SpringSecurityUtil.currentProfileId();
        return ResponseEntity.ok(likeArticleService.like(id,i));
    }

    @PutMapping("/dislike/article/{id}")
    public ResponseEntity<Boolean> dislikeArticle(@PathVariable String id) {
        Integer i = SpringSecurityUtil.currentProfileId();
        return ResponseEntity.ok(likeArticleService.dislike(id,i));
    }

    @PutMapping("/remove/article/{id}")
    public ResponseEntity<Boolean> removeArticle(@PathVariable String id) {
        return ResponseEntity.ok(likeArticleService.remove(id));
    }
}
