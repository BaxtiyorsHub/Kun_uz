package dasturlash.uz.dto;

import dasturlash.uz.dto.article.ArticleInfoDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class SavedArticleDTO {
    private Integer id;
    private ArticleInfoDTO article;
    private LocalDateTime savedDate;
}
