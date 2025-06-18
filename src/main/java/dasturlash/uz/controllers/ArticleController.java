package dasturlash.uz.controllers;

import dasturlash.uz.enums.ArticleStatus;
import dasturlash.uz.enums.Lang;
import dasturlash.uz.request.ArticleRequestDTO;
import dasturlash.uz.responseDto.ArticleResponseDTO;
import dasturlash.uz.services.ArticleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/article")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService){
        this.articleService = articleService;
    }

    @GetMapping("/create")
    public ResponseEntity<Map<String, Object>> getCreateFormData(@RequestParam(defaultValue = "UZ") Lang lang) {
        Map<String, Object> response = new HashMap<>();
        try {
            response.put("categories", articleService.getCategories(lang));
            response.put("sections", articleService.getSections(lang));
        } catch (Exception e) {
            response.put("error", e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponseDTO> getArticleById(@PathVariable int id) {
        return ResponseEntity.ok(articleService.getArticle(id));
    }

    @PostMapping("/create")
    public ResponseEntity<ArticleResponseDTO> createArticle(@Valid @RequestBody ArticleRequestDTO dto) {
        return ResponseEntity.ok(articleService.create(dto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ArticleResponseDTO> updateArticle(
            @PathVariable Integer id,
            @RequestBody ArticleRequestDTO dto) {
        return ResponseEntity.ok(articleService.update(id,dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteArticle(@PathVariable int id) {
        return ResponseEntity.ok(articleService.delete(id));
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<Boolean> updateArticleStatus(@PathVariable int id, @RequestParam ArticleStatus status) {
        return ResponseEntity.ok(articleService.changeStatus(id,status));
    }
}
