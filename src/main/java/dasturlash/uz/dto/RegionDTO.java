package dasturlash.uz.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RegionDTO {

    private Integer id; // Optional: faqat response uchun

    @NotNull(message = "Order number bo‘sh bo‘lmasligi kerak")
    private Integer orderNumber;

    @NotBlank(message = "NameUz bo‘sh bo‘lmasligi kerak")
    private String nameUz;

    @NotBlank(message = "NameRu bo‘sh bo‘lmasligi kerak")
    private String nameRu;

    @NotBlank(message = "NameEn bo‘sh bo‘lmasligi kerak")
    private String nameEn;

    @NotBlank(message = "Key bo‘sh bo‘lmasligi kerak")
    private String key;

    @NotNull(message = "Visible maydoni null bo‘lishi mumkin emas")
    private Boolean visible;

    private LocalDateTime createdDate; // Optional: backend tomonidan to‘ldiriladi
}
