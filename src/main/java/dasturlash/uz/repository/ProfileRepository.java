package dasturlash.uz.repository;

import dasturlash.uz.entities.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends CrudRepository<ProfileEntity, Integer>,
        PagingAndSortingRepository<ProfileEntity, Integer>,
        JpaRepository<ProfileEntity, Integer> {

    ProfileEntity findByIdAndVisibleIsTrue(Integer id);

    Optional<ProfileEntity> findByPhone(String phone);
}
