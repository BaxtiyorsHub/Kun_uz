package dasturlash.uz.entity.article;

import dasturlash.uz.entity.profile.ProfileEntity;
import dasturlash.uz.enums.LikeStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "like_article")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LikeArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "article_id")
    private String articleId;

    @JoinColumn(name = "article_id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private ArticleEntity article;

    @Column(name = "profile_id")
    private Integer profileId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private LikeStatus status;

    @JoinColumn(name = "profile_id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private ProfileEntity profile;

    @CreationTimestamp
    @Column(name = "created_date")
    @Setter(AccessLevel.NONE)
    private LocalDateTime createdDate;
}
