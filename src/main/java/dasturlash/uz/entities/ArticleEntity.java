package dasturlash.uz.entities;

import dasturlash.uz.enums.ArtStatus;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "articles")
public class ArticleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Integer id;

    @Column(nullable = false,name = "title")
    private String title;

    @Column(columnDefinition = "TEXT", name = "decription")
    private String description;

    @Lob
    @Column(columnDefinition = "TEXT",name = "content")
    private String content;

    @Column(name = "image_id")
    private String imageId;

    @Column(name = "region_id", nullable = false)
    private String regionId;

    @Column(name = "visible")
    private Boolean visible = true;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ArtStatus status = ArtStatus.NOT_PUBLISHED;
}
