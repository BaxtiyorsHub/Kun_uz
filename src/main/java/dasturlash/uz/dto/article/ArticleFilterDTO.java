package dasturlash.uz.dto.article;

import dasturlash.uz.enums.ArticleStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class ArticleFilterDTO {
    private String title;
    private Integer regionId;
    private Integer categoryId;
    private Integer sectionId;
    private LocalDate createdDateFrom;
    private LocalDate createdDateTo;
    private ArticleStatus status;
    private LocalDate publishedDateFrom;
    private LocalDate publishedDateTo;
    private Integer moderatorId;
    private Integer publisherId;
}
