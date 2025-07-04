package dasturlash.uz.controller.article;

import dasturlash.uz.dto.article.TagDTO;
import dasturlash.uz.service.article.TagService;
import dasturlash.uz.util.PageCheckingUtil;
import dasturlash.uz.util.SpringSecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/tag-article")
public class TagArticleController {
    private final TagService tagService;

    @PostMapping("/create-tag/{id}")
    public ResponseEntity<Boolean> create(@PathVariable String id){
        Integer i = SpringSecurityUtil.currentProfileId();
        return ResponseEntity.ok(tagService.create(id,i));
    }

    @GetMapping("/get-tags/{id}")
    public ResponseEntity<Page<TagDTO>> getlist(
            @PathVariable String id,
            @RequestParam int page,
            @RequestParam int size){
        Integer i = SpringSecurityUtil.currentProfileId();
        int i1 = PageCheckingUtil.checkPage(page);
        return ResponseEntity.ok(tagService.getList(id,i,i1,size));
    }
}
