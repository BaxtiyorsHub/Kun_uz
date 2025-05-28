package dasturlash.uz.controllers;

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
    public Map<String, Object> getCreateFormData(@RequestParam(defaultValue = "UZ") Lang lang) {
        Map<String, Object> response = new HashMap<>();
        try {
            response.put("categories", articleService.getCategories(lang));
            response.put("sections", articleService.getSections(lang));
        } catch (Exception e) {
            response.put("error", e.getMessage());
        }
        return response;
    }


    @PostMapping("/create")
    public ResponseEntity<ArticleResponseDTO> createArticle(@Valid
                                                                @RequestBody ArticleRequestDTO dto) {
        return ResponseEntity.ok(articleService.create(dto));
    }
}
