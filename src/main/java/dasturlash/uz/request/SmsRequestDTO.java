package dasturlash.uz.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SmsRequestDTO {
    private String message;
    private String phone;
}
