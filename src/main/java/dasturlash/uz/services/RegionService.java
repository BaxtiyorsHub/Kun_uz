package dasturlash.uz.services;

import dasturlash.uz.dto.RegionDTO;
import dasturlash.uz.dto.RegionResponseDTO;
import dasturlash.uz.entities.RegionEntity;
import dasturlash.uz.enums.Lang;
import dasturlash.uz.exp.AppBadExp;
import dasturlash.uz.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class RegionService {

    private final RegionRepository regionRepository;

    public RegionService(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    public RegionDTO create(RegionDTO regionDTO) {
        Optional<RegionEntity> entity = regionRepository.findByKeyAndOrderNumber(
                regionDTO.getKey(),
                regionDTO.getOrderNumber());

        if (entity.isEmpty()) throw new AppBadExp("Region with this key and orderNumber already exists");

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

    public List<RegionEntity> getListAll() {
        List<RegionEntity> allRegion = new LinkedList<>();
        regionRepository.findAll().forEach(allRegion::add);
        return allRegion;
    }

    public RegionDTO update(Integer id,RegionDTO regionDTO) {
        RegionEntity region = regionRepository.findByIdAndVisibleIsTrue(id);
        if (region == null) {
            throw new AppBadExp("Region not found or not visible with id: " + regionDTO.getId());
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
            throw new AppBadExp("Region not found or already deleted");
        }

        region.setVisible(false);
        regionRepository.save(region);
        return true;
    }

    public List<RegionResponseDTO> getListByLang(Lang lang) {
        List<RegionEntity> lists = regionRepository.findByVisibleIsTrue();

        List<RegionResponseDTO> response = new LinkedList<>();
        lists.forEach(entity -> response.add(toDTOLang(lang, entity)));
        return response;
    }

    private RegionResponseDTO toDTOLang(Lang lang, RegionEntity entity) {
        RegionResponseDTO dto = new RegionResponseDTO();
        dto.setId(entity.getId());
        dto.setKey(entity.getKey());
        switch (lang){
            case RU -> dto.setName(entity.getNameRu());
            case EN -> dto.setName(entity.getNameEn());
            default -> dto.setName(entity.getNameUz());
        }
        return dto;

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
}
