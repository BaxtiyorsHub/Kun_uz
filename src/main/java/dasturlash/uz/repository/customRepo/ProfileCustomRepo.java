package dasturlash.uz.repository.customRepo;

import dasturlash.uz.request.FilterRequestDTO;
import dasturlash.uz.entities.ProfileEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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

        if (dto.getQuery() != null) {
            conditionBuilder.append(" and lower(name) like :lower(query) or lower(surname) like :lower(query) or phone like :query  ");
            params.put("name", "%" + dto.getQuery() + "%");
        }
        if (dto.getRole() != null) {
            conditionBuilder.append(" and role = :role ");
            params.put("role", dto.getRole());
        }
        if (dto.getCreatedDateFrom() != null && dto.getCreatedDateTo() != null) {
            conditionBuilder.append(" and createdDate between :createdDateFrom and :createdDateTo ");
            params.put("createdDateFrom", dto.getCreatedDateFrom());
            params.put("createdDateTo", dto.getCreatedDateTo());
        }
        else if (dto.getCreatedDateFrom() != null) {
            conditionBuilder.append(" and createdDate >= :createdDateFrom ");
            params.put("createdDateFrom", dto.getCreatedDateFrom());
        }
        else if (dto.getCreatedDateTo() != null) {
            conditionBuilder.append(" and createdDate <= :createdDateTo ");
            params.put("createdDateTo", dto.getCreatedDateTo());
        }

        StringBuilder select = new StringBuilder("From ProfileEntity ");
        select.append(conditionBuilder);
        select.append(" order by createdDate desc");

        StringBuilder count = new StringBuilder(" select count(*) from ProfileEntity ");
        count.append(conditionBuilder);

        PageRequest pageRequest = PageRequest.of(page, size);

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

        return new PageImpl<>(result, pageRequest, total);
    }
}
