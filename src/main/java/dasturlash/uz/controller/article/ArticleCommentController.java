package dasturlash.uz.controller.article;

import dasturlash.uz.dto.article.CommentAdminDTO;
import dasturlash.uz.dto.article.CommentDTO;
import dasturlash.uz.entity.article.CommentFilterDTO;
import dasturlash.uz.service.article.CommentArticleService;
import dasturlash.uz.util.PageCheckingUtil;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/comment")
public class ArticleCommentController {

    private final CommentArticleService commentArticleService;

    public ArticleCommentController(CommentArticleService commentArticleService) {
        this.commentArticleService = commentArticleService;
    }

    @PostMapping("/{articleId}")
    public ResponseEntity<Boolean> createComment(
            @PathVariable String articleId,
            @RequestBody @Valid CommentDTO dto) {
        return ResponseEntity.ok(commentArticleService.create(articleId, dto));
    }

    @PutMapping("/{articleId}")
    public ResponseEntity<Boolean> updateComment(
            @RequestBody @Valid CommentDTO dto,
            @PathVariable String articleId
    ) {
        return ResponseEntity.ok(commentArticleService.update(articleId, dto));
    }

    @DeleteMapping("/delete/{profileId}/{articleId}/{commentId}")
    public ResponseEntity<Boolean> deleteComment(
            @PathVariable Integer profileId,
            @PathVariable String commentId,
            @PathVariable String articleId) {
        return ResponseEntity.ok(commentArticleService.delete(profileId, articleId, commentId));
    }


    @GetMapping("/admin/get")
    public ResponseEntity<List<CommentDTO>> getComments(
            @RequestParam String articleId){
        return ResponseEntity.ok(commentArticleService.getCommentListByArticleId(articleId));
    }

    @GetMapping("/admin/pagination")
    public ResponseEntity<Page<CommentAdminDTO>> commentsPagination(
            @RequestParam int page,
            @RequestParam int size
    ){
        int i = PageCheckingUtil.checkPage(page);
        return ResponseEntity.ok(commentArticleService.paginationComment(i, size));
    }

    @GetMapping("/admin/filter")
    public ResponseEntity<Page<CommentAdminDTO>> filterComments(
            @RequestParam int page,
            @RequestParam int size,
            @RequestBody CommentFilterDTO dto
    ){
        int i = PageCheckingUtil.checkPage(page);
        return ResponseEntity.ok(commentArticleService.filter(i,size,dto));
    }

    @GetMapping("/admin/repliedComment")
    public ResponseEntity<List<CommentAdminDTO>> comments(
            @RequestParam String commentId
    ){
        return ResponseEntity.ok(commentArticleService.getCommentsByCommentId(commentId));
    }

    @GetMapping("all/comments/{articleId}")
    public ResponseEntity<List<CommentAdminDTO>> getAllComments(@PathVariable String articleId){
        return ResponseEntity.ok(commentArticleService.getAllComments(articleId));
    }
}
