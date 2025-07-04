package dasturlash.uz.controller.article;

import dasturlash.uz.dto.SavedArticleDTO;
import dasturlash.uz.service.article.SavedArticleService;
import dasturlash.uz.util.SpringSecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/saved")
public class SavedArticleController {
    private final SavedArticleService savedArticleService;

    @PostMapping("/save/{articleId}")
    public ResponseEntity<Boolean> saveArticle(@PathVariable String articleId){
        Integer id = SpringSecurityUtil.currentProfileId();
        return ResponseEntity.ok(savedArticleService.save(articleId,id));
    }

    @DeleteMapping("/save-delete/{articleId}")
    public ResponseEntity<Boolean> deleteSavedArticle(@PathVariable String articleId){
        Integer i = SpringSecurityUtil.currentProfileId();
        return ResponseEntity.ok(savedArticleService.delete(articleId,i));
    }

    @GetMapping("/get-save-article")
    public ResponseEntity<List<SavedArticleDTO>> getByProfileId(){
        Integer i = SpringSecurityUtil.currentProfileId();
        return ResponseEntity.ok(savedArticleService.getByProfileId(i));
    }
}
