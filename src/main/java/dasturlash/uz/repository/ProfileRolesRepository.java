package dasturlash.uz.repository;

import dasturlash.uz.entities.ProfileRolesEntity;
import dasturlash.uz.enums.RolesEnum;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRolesRepository extends CrudRepository<ProfileRolesEntity, Integer> {

    @Query("select pr.role from ProfileRolesEntity pr join pr.profile p where p.visible = true and pr.profileId = ?1")
    List<RolesEnum> findProfileRole(Integer id);

    @Transactional
    @Modifying
    @Query("delete from ProfileRolesEntity pr where pr.profileId = ?1 and pr.role=?2")
    void deleteProfileRole(Integer id, RolesEnum role);


}
