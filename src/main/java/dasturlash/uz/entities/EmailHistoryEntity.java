package dasturlash.uz.entities;

import dasturlash.uz.enums.AuthStatus;
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

    @Column(name = "attempts")
    private Integer attempts = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AuthStatus status;

    @Column(name = "created_date")
    @CreationTimestamp
    @Setter(AccessLevel.NONE)
    private LocalDateTime createdDate;

}
