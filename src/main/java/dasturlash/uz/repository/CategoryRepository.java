package dasturlash.uz.repository;

import dasturlash.uz.entities.CategoryEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<CategoryEntity, Integer> {

    CategoryEntity findByIdAndVisibleIsTrue(Integer id);

    List<CategoryEntity> findByVisibleIsTrue();
}
