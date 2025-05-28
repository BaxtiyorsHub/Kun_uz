package dasturlash.uz.responseDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleResponseDTO {

    private Integer id;
    private String title;
    private String description;
    private String content;
    private String imageId;
    private String regionId;
    private List<CategoryResponseDTO> categoryList;
    private List<SectionResponseDTO> sectionList;

}
