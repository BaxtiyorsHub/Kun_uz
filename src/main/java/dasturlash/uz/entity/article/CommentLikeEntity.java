package dasturlash.uz.entity.article;

import dasturlash.uz.entity.profile.ProfileEntity;
import dasturlash.uz.enums.LikeStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "comment_like")
@Setter
@Getter
public class CommentLikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private String id;

    @Column(name = "profile_id")
    private Integer profile_id;
    @JoinColumn(name = "profile_id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private ProfileEntity profile;

    @Column(name = "comment_id")
    private String comment_id;

    @JoinColumn(name = "comment_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private CommentArticleEntity comment;

    @CreationTimestamp
    @Column(name = "created_date")
    @Setter(AccessLevel.NONE)
    private LocalDateTime created_date;

    @Column(name = "emotion")
    private LikeStatus emotion;
}
