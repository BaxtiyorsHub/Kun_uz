package dasturlash.uz.dto.article;

import com.fasterxml.jackson.annotation.JsonInclude;
import dasturlash.uz.dto.profile.ProfileDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDTO {
    @NotBlank
    private String content;
    @NotNull
    private Integer profileId;
    private String commentId;
    private ProfileDTO profile;
}
