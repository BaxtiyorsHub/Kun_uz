package dasturlash.uz.repository;

import dasturlash.uz.entities.RegionEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RegionRepository extends CrudRepository<RegionEntity, Integer> {

    RegionEntity findByIdAndVisibleIsTrue(Integer id);

    List<RegionEntity> findAllByNameUzLikeIgnoreCase(String nameUz);

    List<RegionEntity> findAllByNameRuLikeIgnoreCase(String nameRu);

    List<RegionEntity> findAllByNameEnLikeIgnoreCase(String nameEn);

    Optional<RegionEntity> findByKeyAndOrderNumber(String key, Integer orderNumber);

    List<RegionEntity> findByVisibleIsTrue();
}
