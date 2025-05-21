package dasturlash.uz.services;

import dasturlash.uz.dto.RegionDTO;
import dasturlash.uz.entities.RegionEntity;
import dasturlash.uz.repository.RegionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;

    public RegionDTO create(RegionDTO regionDTO) {
        RegionEntity region = new RegionEntity();
        region.setOrderNumber(regionDTO.getOrderNumber());
        region.setNameUz(regionDTO.getNameUz());
        region.setNameRu(regionDTO.getNameRu());
        region.setNameEn(regionDTO.getNameEn());
        region.setKey(regionDTO.getKey());
        region.setVisible(true);

        regionRepository.save(region);
        return toDTO(region);
    }

    public RegionDTO getById(Integer id) {
        RegionEntity region = regionRepository.findByIdAndVisibleIsTrue(id);
        return region != null ? toDTO(region) : null;
    }

    public List<RegionEntity> getListAll(){
        List<RegionEntity> allRegion =  new LinkedList<>();
        regionRepository.findAll().forEach(allRegion::add);
        return allRegion;
    }

    public RegionDTO update(RegionDTO regionDTO) {
        RegionEntity region = regionRepository.findByIdAndVisibleIsTrue(regionDTO.getId());
        if (region == null) {
            throw new EntityNotFoundException("Region not found or not visible with id: " + regionDTO.getId());
        }

        if (regionDTO.getNameUz() != null) region.setNameUz(regionDTO.getNameUz());
        if (regionDTO.getNameRu() != null) region.setNameRu(regionDTO.getNameRu());
        if (regionDTO.getNameEn() != null) region.setNameEn(regionDTO.getNameEn());
        if (regionDTO.getKey() != null) region.setKey(regionDTO.getKey());

        regionRepository.save(region);
        return toDTO(region);
    }


    public Boolean delete(Integer id) {
        RegionEntity region = regionRepository.findByIdAndVisibleIsTrue(id);
        if (region == null) {
            throw new EntityNotFoundException("Region not found or already deleted");
        }

        region.setVisible(false);
        regionRepository.save(region);
        return true;
    }


    private RegionDTO toDTO(RegionEntity entity) {
        RegionDTO dto = new RegionDTO();
        dto.setId(entity.getId());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());
        dto.setKey(entity.getKey());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public List<RegionEntity> getListByLang(String lang) {
        return null;
    }
}
