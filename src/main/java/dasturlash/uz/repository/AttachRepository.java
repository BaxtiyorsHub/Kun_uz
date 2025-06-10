package dasturlash.uz.repository;

import dasturlash.uz.entities.AttachEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachRepository extends CrudRepository<AttachEntity, Integer> {

}
