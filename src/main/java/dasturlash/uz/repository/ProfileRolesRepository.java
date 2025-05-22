package dasturlash.uz.repository;

import dasturlash.uz.entities.ProfileRolesEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRolesRepository extends CrudRepository<ProfileRolesEntity, Integer> {

}
