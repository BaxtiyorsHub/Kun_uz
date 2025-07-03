package dasturlash.uz.dto.article;

import dasturlash.uz.enums.AppLanguageEnum;
import dasturlash.uz.enums.ArticleStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ArticleRequestDTO {

    @Min(value = 1, message = "n must be >= 1")
    private int n;

    private Integer regionId;
    private Integer sectionId;
    private Integer categoryId;

    @NotNull(message = "lang required")
    private AppLanguageEnum lang;

    @NotNull(message = "status required")
    private ArticleStatus status;
}
