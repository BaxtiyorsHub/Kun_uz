package dasturlash.uz.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegistrationDTO {

    @NotBlank(message = "Name bo'lishi kerak")
    private String name;
    @NotBlank(message = "Surname bo'lishi kerak")
    private String surname;
    @NotBlank(message = "Email/phone bo'lishi kerak")
    private String username;
    @NotBlank(message = "Parol bo'lishi kerak")
    private String password;

}
