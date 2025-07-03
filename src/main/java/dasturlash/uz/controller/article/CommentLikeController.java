package dasturlash.uz.controller.article;

import dasturlash.uz.service.article.CommentLikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/comment_like")
public class CommentLikeController {
    private final CommentLikeService commentLikeService;

    public CommentLikeController(CommentLikeService commentLikeService) {
        this.commentLikeService = commentLikeService;
    }

    @PostMapping("/like-comment")
    public ResponseEntity<Boolean> likeComment(
            @RequestParam String commentId ,
            @RequestParam Integer profileId){
        return ResponseEntity.ok(commentLikeService.likeComment(commentId,profileId));
    }

    @PostMapping("/dislike-comment")
    public ResponseEntity<Boolean> dislikeComment(
            @RequestParam String commentId,
            @RequestParam Integer profileId ){
        return ResponseEntity.ok(commentLikeService.dislikeComment(commentId,profileId));
    }

    @PutMapping("/remove-emotion")
    public ResponseEntity<Boolean> remove(
            @RequestParam String commentId,
            @RequestParam Integer profileId){
        return ResponseEntity.ok(commentLikeService.remove(commentId,profileId));
    }
}
