package dasturlash.uz.repository.profile;

import dasturlash.uz.entity.profile.ProfileRoleEntity;
import dasturlash.uz.enums.ProfileRoleEnum;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProfileRoleRepository extends CrudRepository<ProfileRoleEntity, Integer> {

    @Query("select roles from ProfileRoleEntity where profileId =?1")
    List<ProfileRoleEnum> getRoleListByProfileId(Integer profileId);

    @Transactional
    @Modifying
    @Query("Delete from ProfileRoleEntity where profileId =?1 and roles =?2")
    void deleteByIdAndRoleEnum(Integer profileId, ProfileRoleEnum role);

    @Transactional
    @Modifying
    @Query("Delete from ProfileRoleEntity where profileId =?1")
    void deleteByProfileId(Integer profileId);
}
