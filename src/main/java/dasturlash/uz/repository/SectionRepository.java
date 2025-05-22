package dasturlash.uz.repository;

import dasturlash.uz.entities.SectionEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SectionRepository extends CrudRepository<SectionEntity, Integer> {

    SectionEntity findByIdAndVisibleIsTrue(Integer id);

    List<SectionEntity> findByVisibleIsTrue();
}
