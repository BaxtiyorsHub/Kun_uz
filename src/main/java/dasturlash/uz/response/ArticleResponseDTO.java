package dasturlash.uz.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import dasturlash.uz.enums.ArticleStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleResponseDTO {
    private Integer id;
    private String title;
    private String description;
    private String content;
    private Long sharedCount;
    private String imageId;
    private Integer regionId;
    private Integer moderatorId;
    private Integer publisherId;
    private ArticleStatus status;
    private Byte readTime; // in second
    private Integer viewCount; // in second
    private LocalDateTime publishedDate;
    private List<CategoryResponseDTO> categoryList;
    private List<SectionResponseDTO> sectionList;
}
