package dasturlash.uz.services;

import dasturlash.uz.dto.ProfileDTO;
import dasturlash.uz.dto.SectionDTO;
import dasturlash.uz.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SectionService {
    @Autowired
    private SectionRepository sectionRepository;

    public SectionDTO create(SectionDTO sectionDTO) {
        return null;
    }

    public SectionDTO getById(Integer id) {
        return null;
    }

    public SectionDTO update(Integer id) {
        return null;
    }

    public Boolean delete(Integer id) {
        return null;
    }
}
