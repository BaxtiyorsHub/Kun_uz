package dasturlash.uz.responseDto;

import dasturlash.uz.enums.RolesEnum;
import jakarta.validation.constraints.NotNull;

public class ProfileInfoDTO {

    Integer id;
    @NotNull
    Integer profile_id;
    @NotNull
    RolesEnum role;
}
