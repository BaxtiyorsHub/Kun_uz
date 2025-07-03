package dasturlash.uz.entity.article;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentFilterDTO {
    private String commentId;
    private String created_date_from;
    private String created_date_to;
    private Integer profile_id;
    private String article_id;
}
