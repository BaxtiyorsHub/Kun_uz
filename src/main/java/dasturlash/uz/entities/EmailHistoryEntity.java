package dasturlash.uz.entities;

import dasturlash.uz.enums.EmailAndSMSStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "email_history_entity")
public class EmailHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private String id;

    @Column(name = "code")
    private String code;

    @Column(name = "to_email")
    private String toEmail;

    @Column(name = "body",columnDefinition = "TEXT")
    private String body;

    @Column(name = "attempts")
    private Integer attempts = 0;

    @Column(name = "hoursBan")
    private LocalDateTime hoursBan;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EmailAndSMSStatus status;

    @Column(name = "created_date")
    @CreationTimestamp
    @Setter(AccessLevel.NONE)
    private LocalDateTime createdDate;

}
