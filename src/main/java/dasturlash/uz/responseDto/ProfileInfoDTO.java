package dasturlash.uz.responseDto;

import dasturlash.uz.enums.Status;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProfileInfoDTO {

    private Integer id;

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

    @NotNull(message = "Visible qiymati null bo‘lmasligi kerak")
    private Boolean visible;

    private LocalDateTime createdDate; // Optional: server tomonidan to‘ldiriladi

    private String photoId; // Optional: rasm bo‘lmasligi mumkin
}
