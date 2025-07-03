package dasturlash.uz.entity.article;

import dasturlash.uz.entity.profile.ProfileEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "saved_article")
@Setter
@Getter
public class SavedArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Integer id;

    @Column(name = "profile_id")
    private Integer profile_id;
    @JoinColumn(name = "profile_id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private ProfileEntity profile;

    @Column(name = "article_id")
    private String article_id;
    @JoinColumn(name = "article_id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private ArticleEntity article;

    @Column(name = "created_date")
    @CreationTimestamp
    @Setter(AccessLevel.NONE)
    private LocalDateTime created_date;
}
