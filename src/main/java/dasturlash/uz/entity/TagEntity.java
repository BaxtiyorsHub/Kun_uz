package dasturlash.uz.entity;

import dasturlash.uz.entity.article.ArticleEntity;
import dasturlash.uz.entity.profile.ProfileEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Table(name = "tag")
@Entity
@Setter
@Getter
public class TagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "article")
    private String articleId;

    @JoinColumn(name = "article", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private ArticleEntity article;

    @Column(name = "profile")
    private Integer profileId;

    @JoinColumn(name = "profile", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private ProfileEntity profile;

    @Column(name = "visible")
    private Boolean visible = true;

    @Column(name = "created_date")
    @CreationTimestamp
    @Setter(AccessLevel.NONE)
    private LocalDateTime createdDate;
}
