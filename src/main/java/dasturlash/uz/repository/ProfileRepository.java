package dasturlash.uz.repository;

import dasturlash.uz.entities.ProfileEntity;
import dasturlash.uz.enums.RolesEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends CrudRepository<ProfileEntity, Integer>,
        PagingAndSortingRepository<ProfileEntity, Integer>,
        JpaRepository<ProfileEntity, Integer> {

    ProfileEntity findByIdAndVisibleIsTrue(Integer id);

    Optional<ProfileEntity> findByPhone(String phone);

    @Query("from ProfileEntity where phone=?1 or email=?1 and visible=true ")
    Optional<ProfileEntity> findByPhoneOrEmailAndVisibleIsTrue(String username);

    @Query("select pre.role from ProfileRolesEntity pre where pre.profile.id = ?1 and pre.profile.visible = true")
    List<RolesEnum> roles(Integer profileId);

}
