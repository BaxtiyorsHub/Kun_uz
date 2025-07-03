package dasturlash.uz.controller.article;

import dasturlash.uz.dto.article.*;
import dasturlash.uz.enums.AppLanguageEnum;
import dasturlash.uz.service.article.ArticleService;
import dasturlash.uz.util.PageCheckingUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping("/moderator/create")
    public ResponseEntity<ArticleInfoDTO> create(@Valid @RequestBody ArticleDTO dto) {
        return ResponseEntity.ok(articleService.createArticle(dto));
    }

    @PutMapping("/moderator/update/{id}")
    public ResponseEntity<ArticleInfoDTO> updateArticle(
            @PathVariable String id,
            @Valid @RequestBody ArticleDTO dto) {
        return ResponseEntity.ok(articleService.updateArticle(id, dto));
    }

    @DeleteMapping("/moderator/delete/{id}")
    public ResponseEntity<String> deleteArticle(@PathVariable String id) {
        return ResponseEntity.ok(articleService.deleteArticle(id));
    }

    @PutMapping("/publisher/status")
    public ResponseEntity<String> updateArticleStatus(@RequestBody @Valid ArticleChangeStatus request) {
        return ResponseEntity.ok(articleService.changeArticleStatus(request.getId(), request.getStatus()));
    }

    @PostMapping("/admin/get/articles")
    public ResponseEntity<Page<ArticleInfoDTO>> getArticlesBySection(
            @RequestParam int page,
            @RequestParam int size,
            @RequestBody @Valid ArticleRequestDTO request) {
        int pageCheck = PageCheckingUtil.checkPage(page);
        return ResponseEntity.ok(articleService.getLastNArticles(pageCheck, size, request));
    }

    @GetMapping("/admin/get/{regionId}/articles")
    public ResponseEntity<Page<ArticleInfoDTO>> getArticlesByRegion(
            @PathVariable Integer regionId,
            @RequestParam(defaultValue = "UZ") AppLanguageEnum lang,
            @RequestParam int page,
            @RequestParam int size
    ) {
        int pageCheck = PageCheckingUtil.checkPage(page);
        return ResponseEntity.ok(articleService.getArticlesByRegion(regionId, pageCheck, size, lang));
    }

    @GetMapping("lastest/articles")
    public ResponseEntity<Page<ArticleInfoDTO>> getLastestArticles(
            @RequestParam(defaultValue = "UZ") AppLanguageEnum lang,
            @RequestParam int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestBody List<String> articleId) {
        int pageCheck = PageCheckingUtil.checkPage(page);
        return ResponseEntity.ok(articleService.lastest12Articles(pageCheck, size, articleId, lang));
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<Boolean> viewCount(@PathVariable String id) {
        return ResponseEntity.ok(articleService.changeViewCount(id));
    }

    @GetMapping("/share/{id}")
    public ResponseEntity<Boolean> shareCount(@PathVariable String id) {
        return ResponseEntity.ok(articleService.shareCount(id));
    }

    @GetMapping("/most/view/articles/{id}")
    public ResponseEntity<List<ArticleInfoDTO>> getMostViewedArticles(
            @RequestParam(defaultValue = "UZ") AppLanguageEnum language,
            @PathVariable String id) {
        return ResponseEntity.ok(articleService.most4ReadArticles(id, language));
    }

    @GetMapping("/most/view/section/articles")
    public ResponseEntity<List<ArticleInfoDTO>> last4ArticlesBySection(
            @RequestParam(defaultValue = "UZ") AppLanguageEnum language,
            @RequestParam String id,
            @RequestParam Integer sectionId
    ) {
        return ResponseEntity.ok(articleService.get4ArticleBySectionId(id, sectionId, language));
    }

    @GetMapping("/search/articles")
    public ResponseEntity<Page<ArticleInfoDTO>> filterArticles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestBody ArticleFilterDTO request) {
        int pageCheck = PageCheckingUtil.checkPage(page);
        return ResponseEntity.ok(articleService.filter(request,pageCheck, size));
        // 15-filter
    }

    @GetMapping("/moderator/filter/articles")
    public ResponseEntity<Page<ArticleInfoDTO>> filterForModerator(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestBody ArticleFilterDTO request
    ){
        int pageCheck = PageCheckingUtil.checkPage(page);
        return ResponseEntity.ok(articleService.filter(request,pageCheck,size));
    }

    @GetMapping("/publisher/filter/articles")
    public ResponseEntity<Page<ArticleInfoDTO>> filterForPublisher(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestBody ArticleFilterDTO request
    ){
        int pageCheck = PageCheckingUtil.checkPage(page);
        return ResponseEntity.ok(articleService.filter(request,pageCheck,size));
    }
}
