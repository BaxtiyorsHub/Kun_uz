package dasturlash.uz.request;

import dasturlash.uz.dto.CategoryDTO;
import dasturlash.uz.dto.SectionDTO;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class ArticleRequestDTO {

    /** Maqola sarlavhasi */
    @NotBlank(message = "Sarlavha bo'sh bo'lishi mumkin emas")
    private String title;

    /** Maqola qisqacha tavsifi */
    private String description;

    /** Maqolaning to'liq matni */
    @NotBlank(message = "Kontent bo'sh bo'lishi mumkin emas")
    private String content;

    /** Maqolaga biriktirilgan rasm ID raqami */
    private String imageId;

    /** Qaysi hududga tegishli ekanligi (region ID) */
    @NotNull(message = "Hudud ID kiritilishi kerak")
    private Integer regionId;

    /** O'qish uchun taxminiy vaqt (daqiqa hisobida) */
    @Min(value = 1, message = "O'qish vaqti kamida 1 daqiqa bo'lishi kerak")
    @Max(value = 60, message = "O'qish vaqti 60 daqiqadan oshmasligi kerak")
    private Byte readTime;

    /** Maqola tegishli bo'lgan kategoriyalar ro'yxati */
    private List<CategoryDTO> categoryList;

    /** Maqola bo'limlari (section) ro'yxati */
    private List<SectionDTO> sectionList;

    private LocalDateTime publishedDate;
}
