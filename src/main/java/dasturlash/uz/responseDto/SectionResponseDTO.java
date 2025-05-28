package dasturlash.uz.responseDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SectionResponseDTO {
    Integer id;
    String key;
    String name;
    String nameRu;
    String nameEn;
}
