package dasturlash.uz.repository;

import dasturlash.uz.entities.ArticleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ArticleRepository extends CrudRepository<ArticleEntity, Integer> {

}
