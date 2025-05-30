package dasturlash.uz.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegistrationDTO {
    private String name;
    private String surname;
    private String username;
    private String password;

}
