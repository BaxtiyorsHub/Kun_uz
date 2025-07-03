package dasturlash.uz.dto.article;

import dasturlash.uz.enums.ArticleStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ArticleChangeStatus {
    @NotBlank
    private String id;
    @NotNull
    private ArticleStatus status;
}
