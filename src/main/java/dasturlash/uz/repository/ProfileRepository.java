package dasturlash.uz.repository;

import dasturlash.uz.entities.ProfileEntity;
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

    @Query("from ProfileEntity where email =?1 or phone =?1 and password=?2 ")
    ProfileEntity findByPhoneOrEmail(String emailOrPhone, String password);
}
