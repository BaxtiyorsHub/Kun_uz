package dasturlash.uz.request;

import dasturlash.uz.entities.CategoryEntity;
import dasturlash.uz.entities.SectionEntity;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
public class ArticleRequestDTO {

    @NotNull
    private String title;

    @Lob
    private String description;
    @Lob
    private String content;

    private String imageId;
    @NotNull
    private String regionId;
    @NotNull
    private List<Integer> categoryList;
    @NotNull
    private List<Integer> sectionList;

}
