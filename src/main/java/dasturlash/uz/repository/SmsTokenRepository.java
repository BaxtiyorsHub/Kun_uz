package dasturlash.uz.repository;

import dasturlash.uz.entities.SmsTokenEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SmsTokenRepository extends CrudRepository<SmsTokenEntity,Integer> {
    Optional<SmsTokenEntity> findTopByOrderByCreatedDateDesc();
}
