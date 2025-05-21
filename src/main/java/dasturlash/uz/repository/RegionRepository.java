package dasturlash.uz.repository;

import dasturlash.uz.entities.RegionEntity;
import org.springframework.data.repository.CrudRepository;

public interface RegionRepository extends CrudRepository<RegionEntity, Integer> {

    RegionEntity findByIdAndVisibleIsTrue(Integer id);

    void deleteByIdAndVisibleIsTrue(Integer id);
}
