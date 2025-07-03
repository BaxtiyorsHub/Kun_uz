package dasturlash.uz.entity.article;

import dasturlash.uz.entity.profile.ProfileEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "comment_article")
@Setter
@Getter
public class CommentArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private String id;

    @Column(name = "created_date")
    @Setter(AccessLevel.NONE)
    @CreationTimestamp
    private LocalDateTime created_date;

    @Column(name = "update_date")
    private LocalDateTime update_date;

    @Column(name = "profile_id")
    private Integer profile_id;
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private ProfileEntity profile;

    @Column(name = "content")
    private String content;

    @Column(name = "article_id")
    private String article_id; // articleId
    @JoinColumn(name = "article_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private ArticleEntity article;

    @Column(name = "reply_id")
    private String reply_id; // replyId
    @JoinColumn(name = "reply_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private CommentArticleEntity reply;

    @Column(name = "visible")
    private Boolean visible = true;

    @Column(name = "likes")
    private Long likes = 0L;

    @Column(name = "dislikes")
    private Long dislikes = 0L;
}
