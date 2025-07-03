package dasturlash.uz.entity.article;

import dasturlash.uz.entity.AttachEntity;
import dasturlash.uz.entity.RegionEntity;
import dasturlash.uz.entity.profile.ProfileEntity;
import dasturlash.uz.enums.ArticleStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "article")
public class ArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "title_uz", columnDefinition = "text")
    private String titleUz;
    @Column(name = "title_ru", columnDefinition = "text")
    private String titleRu;
    @Column(name = "title_en", columnDefinition = "text")
    private String titleEn;

    @Column(name = "description_uz", columnDefinition = "text")
    private String descriptionUz;
    @Column(name = "description_ru", columnDefinition = "text")
    private String descriptionRu;
    @Column(name = "description_en", columnDefinition = "text")
    private String descriptionEn;

    @Column(name = "content_uz", columnDefinition = "text")
    private String contentUz;
    @Column(name = "content_ru", columnDefinition = "text")
    private String contentRu;
    @Column(name = "content_en", columnDefinition = "text")
    private String contentEn;

    @Column(name = "shared_count")
    private Long sharedCount;

    @Column(name = "likes_count")
    private Long likes;
    @Column(name = "dislikes_count")
    private Long dislikes;

    @Column(name = "image_id")
    private String imageId;
    @ManyToOne(fetch = FetchType.LAZY) // buyerda OneToOne ham bo'lishi mumkun.
    @JoinColumn(name = "image_id", insertable = false, updatable = false)
    private AttachEntity image;

    @Column(name = "region_id")
    private Integer regionId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", insertable = false, updatable = false)
    private RegionEntity region;

    @Column(name = "moderator_id")
    private Integer moderatorId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moderator_id", insertable = false, updatable = false)
    private ProfileEntity moderator;

    @Column(name = "publisher_id")
    private Integer publisherId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id", insertable = false, updatable = false)
    private ProfileEntity publisher;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ArticleStatus status = ArticleStatus.NOT_PUBLISHED;

    @Column(name = "read_time")
    private Integer readTime; // in second

    @Column(name = "view_count")
    private Long viewCount; // in second

    @Column(name = "created_date")
    @CreationTimestamp
    @Setter(AccessLevel.NONE)
    private LocalDateTime createdDate;

    @Column(name = "published_date")
    private LocalDateTime publishedDate;

    @Column(name = "visible")
    private Boolean visible = Boolean.TRUE;

}
