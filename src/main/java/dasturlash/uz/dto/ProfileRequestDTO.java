package dasturlash.uz.dto;

import dasturlash.uz.enums.RolesEnum;
import dasturlash.uz.enums.Status;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProfileRequestDTO {

    @NotBlank(message = "Ism bo‘sh bo‘lmasligi kerak")
    private String name;

    @NotBlank(message = "Familiya bo‘sh bo‘lmasligi kerak")
    private String surname;

    @NotBlank(message = "Telefon raqam bo‘sh bo‘lmasligi kerak")
    private String phone;

    @Email(message = "Email noto‘g‘ri formatda bo'lishi mumkin")
    private String email; // Optional

    @NotBlank(message = "Parol bo‘sh bo‘lmasligi kerak")
    private String password;

    @NotNull(message = "Status null bo‘lmasligi kerak")
    private Status status;

    private String photoId;

    @NotNull(message = "Role bo'lishi kerak")
    private List<RolesEnum> rolesEnumList;
}
