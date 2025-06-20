package dasturlash.uz.request;

import dasturlash.uz.entities.SectionEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArtReqPagination {
    private String className;
    private int classId;
    private int limit;

    public String getClassName() {
        if ("ArticleEntity".equalsIgnoreCase(className)) {
            return className = "ArticleEntity";
        } else if ("SectionEntity".equalsIgnoreCase(className)) {
            return className = "SectionEntity";
        }
        throw new IllegalArgumentException("Unknown class name: " + className);
    }
}
