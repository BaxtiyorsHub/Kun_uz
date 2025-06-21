package dasturlash.uz.entities;

import dasturlash.uz.enums.CodeStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "sms_history_entity")
@Setter
@Getter
public class SmsHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private String id;

    @Column(name = "codes",unique = true)
    private Integer code;

    @Column(name = "phones",unique = true)
    private String toPhone;

    @Column(name = "attempts")
    private Integer attempts = 0;

    @Column(name = "hoursBan")
    private LocalDateTime hoursBan;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CodeStatus status;

    @Column(name = "created_date")
    @CreationTimestamp
    @Setter(AccessLevel.NONE)
    private LocalDateTime createdDate;


}
