package dasturlash.uz.repository;

import dasturlash.uz.entities.AttachEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttachRepository extends CrudRepository<AttachEntity, String>, JpaRepository<AttachEntity,String> {

    /**
     * @return attachEntity
     */
    @Override
    AttachEntity getById(String id);
}
