package dasturlash.uz.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "category")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "order_number", nullable = false, unique = true, updatable = false)
    private Integer orderNumber;

    @Column(name = "name_uz", nullable = false)
    private String nameUz;

    @Column(name = "name_ru", nullable = false)
    private String nameRu;

    @Column(name = "name_en", nullable = false)
    private String nameEn;

    @Column(name = "key", nullable = false, unique = true)
    private String key;

    @Column(name = "visible", nullable = false)
    private Boolean visible;

    @CreationTimestamp
    @Setter(AccessLevel.NONE)
    private LocalDateTime createdDate;
}
