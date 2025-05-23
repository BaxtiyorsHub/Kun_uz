package dasturlash.uz.responseDto;

import dasturlash.uz.enums.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProfileInfoResponseDTO {
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
}
