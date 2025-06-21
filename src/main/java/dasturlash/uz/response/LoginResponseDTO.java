package dasturlash.uz.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import dasturlash.uz.enums.RolesEnum;
import lombok.Setter;

import java.util.List;

@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponseDTO {
    private String name;
    private String surname;
    private String username;
    private List<RolesEnum> rolesList;
    private String jwtToken;
}
