package dasturlash.uz.responseDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Setter;

@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleEntitiesResponseDTO {
    Integer id;
    String key;
    String nameUz;
    String nameRu;
    String nameEn;
}
