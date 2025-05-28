package dasturlash.uz.responseDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Setter;

import java.util.List;

@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleResponseDTO {

    private String title;
    private String description;
    private String content;
    private String imageId;
    private String regionId;
    private List<String> categoryList;
    private List<String> sectionList;

}
