package dasturlash.uz.repository;

import dasturlash.uz.entities.EmailHistoryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailHistoryRepository extends CrudRepository<EmailHistoryEntity, String> {

    /**
     * @return EmailHistoryEntity
     */
    @Query("from EmailHistoryEntity where toEmail=?1 order by createdDate desc limit 1")
    Optional<EmailHistoryEntity> findByToEmail(String toEmail);
}
