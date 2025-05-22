package dasturlash.uz.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import dasturlash.uz.enums.RolesEnum;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "profile_roles")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileRolesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private RolesEnum role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    private ProfileEntity profile;
}
