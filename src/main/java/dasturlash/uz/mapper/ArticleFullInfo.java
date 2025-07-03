package dasturlash.uz.mapper;

import java.time.LocalDateTime;

public interface ArticleFullInfo {
    String getId();
    String getTitle();
    String getDescription();
    String getContent();
    String getImageId();
    Long getSharedCount();
    Long getLikeCount();
    Long getViewCount();
    LocalDateTime getPublishedDate();
    Integer getRegionId();

    String getCategoryList(); // json keladi
    String getSectionList(); // json keladi
}