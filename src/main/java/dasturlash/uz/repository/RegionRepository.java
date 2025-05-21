package dasturlash.uz.repository;

import dasturlash.uz.entities.RegionEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RegionRepository extends CrudRepository<RegionEntity, Integer> {

    RegionEntity findByIdAndVisibleIsTrue(Integer id);

    List<RegionEntity> findAllByNameUzLikeIgnoreCase(String nameUz);

    List<RegionEntity> findAllByNameRuLikeIgnoreCase(String nameRu);

    List<RegionEntity> findAllByNameEnLikeIgnoreCase(String nameEn);
}
