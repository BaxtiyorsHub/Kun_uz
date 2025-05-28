package dasturlash.uz.request;

import dasturlash.uz.enums.RolesEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FilterRequestDTO {
    private String query;
    private RolesEnum role;
    private LocalDateTime createdDateFrom;
    private LocalDateTime createdDateTo;

}
