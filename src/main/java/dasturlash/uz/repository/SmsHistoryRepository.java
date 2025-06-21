package dasturlash.uz.repository;

import dasturlash.uz.entities.SmsHistoryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SmsHistoryRepository extends CrudRepository<SmsHistoryEntity, Integer> {
    /**
     * @param toPhone
     * @return SmsHistoryEntity
     */
    SmsHistoryEntity findByToPhone(String toPhone);
}
