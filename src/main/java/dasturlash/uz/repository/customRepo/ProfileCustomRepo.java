package dasturlash.uz.repository.customRepo;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ProfileCustomRepo {
    @Autowired
    private EntityManager entityManager;
}
