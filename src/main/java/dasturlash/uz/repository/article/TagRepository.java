package dasturlash.uz.repository.article;

import dasturlash.uz.entity.TagEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<TagEntity,Long> {
    List<TagEntity> findByArticleIdAndProfileId(String articleId, Integer profileId, PageRequest pageRequest);
}
