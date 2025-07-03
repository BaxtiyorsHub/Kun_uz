package dasturlash.uz.dto.article;

import dasturlash.uz.dto.CategoryDTO;
import dasturlash.uz.dto.SectionDTO;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ArticleDTO {

    @NotBlank(message = "Title must not be blank")
    @Size(min = 5, max = 300, message = "Title must be between 5 and 400 characters")
    private String title;

    @NotBlank(message = "Title must not be blank")
    @Size(min = 5, max = 300, message = "Title must be between 5 and 400 characters")
    private String titleRu;

    @NotBlank(message = "Title must not be blank")
    @Size(min = 5, max = 300, message = "Title must be between 5 and 400 characters")
    private String titleEn;

    @NotBlank(message = "Description must not be blank")
    @Size(min = 10, max = 500, message = "Description must be between 10 and 500 characters")
    private String description;

    @NotBlank(message = "Description must not be blank")
    @Size(min = 10, max = 500, message = "Description must be between 10 and 500 characters")
    private String descriptionRu;

    @NotBlank(message = "Description must not be blank")
    @Size(min = 10, max = 500, message = "Description must be between 10 and 500 characters")
    private String descriptionEn;

    @NotBlank(message = "Content must not be blank")
    private String content;

    @NotBlank(message = "Content must not be blank")
    private String contentRu;

    @NotBlank(message = "Content must not be blank")
    private String contentEn;

    private String imageId;

    @NotNull(message = "Region ID is required")
    private Integer regionId;

    @NotNull(message = "Read time is required")
    @Min(value = 1, message = "Read time must be greater than 0")
    @Max(value = 120, message = "Read time must be less than 120")
    private Integer readTime;

    @NotEmpty(message = "At least one category must be selected")
    private List<CategoryDTO> categoryList;

    @NotEmpty(message = "At least one section must be selected")
    private List<SectionDTO> sectionList;
}
