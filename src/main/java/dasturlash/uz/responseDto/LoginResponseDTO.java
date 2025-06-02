package dasturlash.uz.responseDto;

import dasturlash.uz.enums.RolesEnum;
import lombok.Setter;

import java.util.List;

@Setter
public class LoginResponseDTO {
    private String name;
    private String surname;
    private String username;
    private List<RolesEnum> rolesList;
}
