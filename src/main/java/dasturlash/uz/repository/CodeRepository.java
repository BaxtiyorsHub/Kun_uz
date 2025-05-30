package dasturlash.uz.repository;

import dasturlash.uz.entities.AuthRegistrationCodes;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CodeRepository extends CrudRepository<AuthRegistrationCodes, Long> {

    @Query("from AuthRegistrationCodes where toEmail=?1")
    List<AuthRegistrationCodes> findByToEmail(String toEmail);
}
