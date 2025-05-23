package dasturlash.uz.repository.customRepo;

import dasturlash.uz.dto.FilterRequestDTO;
import dasturlash.uz.entities.ProfileEntity;
import dasturlash.uz.responseDto.ProfileInfoDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProfileCustomRepo {
    private final EntityManager entityManager;

    public ProfileCustomRepo(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Page<ProfileEntity> filter(Integer page, Integer size, FilterRequestDTO dto) {
        StringBuilder conditionBuilder = new StringBuilder(" where 1=1");

        Map<String, Object> params = new HashMap<>();

        if (dto.getName() != null) {
            conditionBuilder.append(" and name like :name ");
            params.put("name", "%" + dto.getName() + "%");
        }
        if (dto.getSurname() != null) {
            conditionBuilder.append(" and surname like :surname");
            params.put("surname", "%" + dto.getSurname() + "%");
        }
        if (dto.getPhone() != null) {
            conditionBuilder.append(" and phone like :phone ");
            params.put("phone", "%" + dto.getPhone() + "%");
        }
        if (dto.getRole() != null) {
            conditionBuilder.append(" and role = :role ");
            params.put("role", dto.getRole());
        }
        if (dto.getCreatedDateFrom() != null) {
            conditionBuilder.append(" and created_date >= :createdDateFrom ");
            params.put("createdDateFrom", dto.getCreatedDateFrom());
        }
        if (dto.getCreatedDateTo() != null) {
            conditionBuilder.append(" and created_date <= :createdDateTo ");
            params.put("createdDateTo", dto.getCreatedDateTo());
        }

        StringBuilder select = new StringBuilder("From ProfileEntity ");
        select.append(conditionBuilder);
        select.append(" order by created_date desc");

        StringBuilder count = new StringBuilder(" select count(*) from ProfileEntity ");
        count.append(conditionBuilder);

        Query selectQuery = entityManager.createQuery(select.toString());
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            selectQuery.setParameter(entry.getKey(), entry.getValue());
        }
        selectQuery.setMaxResults(size);
        selectQuery.setFirstResult(page * size);

        List<ProfileEntity> result = selectQuery.getResultList();

        Query countQuery = entityManager.createQuery(count.toString());
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            countQuery.setParameter(entry.getKey(), entry.getValue());
        }
        Long total = (Long) countQuery.getSingleResult();

        return new PageImpl<>(result, null, total);
    }
}
