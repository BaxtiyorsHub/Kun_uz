package dasturlash.uz.dto;

import dasturlash.uz.enums.RolesEnum;
import jakarta.validation.constraints.NotNull;

public class ProfileRolesDTO {

    Integer id;
    @NotNull
    Integer profile_id;
    @NotNull
    RolesEnum role;
}
