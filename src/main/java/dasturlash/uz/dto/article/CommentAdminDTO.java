package dasturlash.uz.dto.article;

import com.fasterxml.jackson.annotation.JsonInclude;
import dasturlash.uz.dto.profile.ProfileDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentAdminDTO {
    private String commentId;
    private LocalDateTime created_date;
    private LocalDateTime update_date;
    private ProfileDTO profile; // id name surname
    private String content;
    private ArticleInfoDTO article; // id title
    private String reply_id;
    private List<String> replyList;
    private boolean visible;
    private Long likes;
    private Long dislikes;
}
