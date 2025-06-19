package dasturlash.uz.request;

import dasturlash.uz.dto.CategoryDTO;
import dasturlash.uz.dto.SectionDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ArticleRequestDTO {
    private String title;
    private String description;
    private String content;
    private String imageId;
    private Integer regionId;
    private Integer readTime; // in second
    //    private List<Integer> categoryId;    // [ 1,2,3,4]
    private List<CategoryDTO> categoryList; // [ {id:1}, {id:2},{id:3},{id:4}]
    private List<SectionDTO> sectionList; // [ {id:1}, {id:2},{id:3},{id:4}]
}

