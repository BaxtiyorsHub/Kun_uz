package dasturlash.uz.services;

import dasturlash.uz.dto.SectionDTO;
import dasturlash.uz.responseDto.SectionResponseDTO;
import dasturlash.uz.entities.SectionEntity;
import dasturlash.uz.enums.Lang;
import dasturlash.uz.repository.SectionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class SectionService {
    private final SectionRepository sectionRepository;

    public SectionService(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    public SectionDTO create(SectionDTO sectionDTO) {
        SectionEntity section = new SectionEntity();
        section.setOrderNumber(sectionDTO.getOrderNumber());
        section.setNameUz(sectionDTO.getNameUz());
        section.setNameRu(sectionDTO.getNameRu());
        section.setNameEn(sectionDTO.getNameEn());
        section.setKey(sectionDTO.getKey());

        sectionRepository.save(section);
        return toDTO(section);
    }

    public SectionDTO getById(Integer id) {
        SectionEntity section = sectionRepository.findByIdAndVisibleIsTrue(id);
        return section != null ? toDTO(section) : null;
    }

    public SectionDTO update(Integer id, SectionDTO sectionDTO) {
        SectionEntity section = sectionRepository.findByIdAndVisibleIsTrue(id);
        if (section == null) {
            throw new EntityNotFoundException("Section not found or not visible with id: " + sectionDTO.getId());
        }

        if (sectionDTO.getNameUz() != null) section.setNameUz(sectionDTO.getNameUz());
        if (sectionDTO.getNameRu() != null) section.setNameRu(sectionDTO.getNameRu());
        if (sectionDTO.getNameEn() != null) section.setNameEn(sectionDTO.getNameEn());
        if (sectionDTO.getKey() != null) section.setKey(sectionDTO.getKey());

        sectionRepository.save(section);
        return toDTO(section);
    }

    public Boolean delete(Integer id) {
        SectionEntity section = sectionRepository.findByIdAndVisibleIsTrue(id);
        if (section == null) {
            throw new EntityNotFoundException("Section not found or already deleted");
        }

        section.setVisible(false);
        sectionRepository.save(section);
        return true;
    }


    public List<SectionDTO> getListAll() {
        List<SectionDTO> allSection = new LinkedList<>();
        sectionRepository.findAll()
                .forEach(t -> allSection.add(toDTOAdmin(t)));
        return allSection;
    }

    private SectionDTO toDTOAdmin(SectionEntity entity) {
        SectionDTO dto = new SectionDTO();
        dto.setId(entity.getId());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());
        dto.setKey(entity.getKey());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setImageId(entity.getImageId());
        return dto;
    }

    private SectionDTO toDTO(SectionEntity entity) {
        SectionDTO dto = new SectionDTO();
        dto.setId(entity.getId());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());
        dto.setKey(entity.getKey());
        dto.setCreatedDate(entity.getCreatedDate());

        return dto;
    }

    public List<SectionResponseDTO> getListLang(Lang lang) {
        List<SectionEntity> sections = sectionRepository.findByVisibleIsTrue();

        List<SectionResponseDTO> response = new LinkedList<>();

        sections.forEach(s -> response.add(toDtoLang(lang, s)));
        return response;
    }

    private SectionResponseDTO toDtoLang(Lang lang, SectionEntity s) {
        SectionResponseDTO dto = new SectionResponseDTO();

        dto.setId(s.getId());
        dto.setKey(s.getKey());
        switch (lang) {
            case EN -> dto.setName(s.getNameEn());
            case RU -> dto.setName(s.getNameRu());
            default -> dto.setName(s.getNameUz());
        }
        return dto;
    }
}
