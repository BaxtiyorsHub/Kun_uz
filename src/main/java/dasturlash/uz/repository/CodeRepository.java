package dasturlash.uz.repository;

import dasturlash.uz.entities.EmailHistoryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CodeRepository extends CrudRepository<EmailHistoryEntity, String> {

    @Query("from EmailHistoryEntity where toEmail=?1")
    List<EmailHistoryEntity> findByToEmail(String toEmail);
}
