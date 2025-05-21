package dasturlash.uz.repository;

import dasturlash.uz.dto.ProfileDTO;
import dasturlash.uz.entities.ProfileEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends CrudRepository<ProfileEntity, Integer> {
    ProfileEntity findByIdAndVisibleIsTrue(Integer id);
}
