package dasturlash.uz.request.auth;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequestDTO {
    private String username;
    private String password;
}
