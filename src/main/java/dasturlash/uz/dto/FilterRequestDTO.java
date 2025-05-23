package dasturlash.uz.dto;

import dasturlash.uz.enums.RolesEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FilterRequestDTO {
    private String name;
    private String surname;
    private String phone;
    private RolesEnum role;
    private LocalDateTime createdDateFrom;
    private LocalDateTime createdDateTo;

}
