package dasturlash.uz.repository;

import dasturlash.uz.entities.CategoryEntity;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<CategoryEntity, Integer> {

    CategoryEntity findByIdAndVisibleIsTrue(Integer id);
}
