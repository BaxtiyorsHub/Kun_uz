package dasturlash.uz.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import dasturlash.uz.enums.RolesEnum;
import dasturlash.uz.enums.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileResponseDTO {

    private Integer id;
    private String name;
    private String surname;
    private String phone;
    private String email;
    private String password;
    private Status status;
    private Boolean visible;
    private LocalDateTime createdDate;
    private String photoId;
    private List<RolesEnum> rolesList;

}
