package dasturlash.uz.services;

import dasturlash.uz.dto.FilterRequestDTO;
import dasturlash.uz.repository.customRepo.ProfileCustomRepo;
import dasturlash.uz.responseDto.ProfileInfoDTO;
import dasturlash.uz.dto.ProfileRequestDTO;
import dasturlash.uz.entities.ProfileEntity;
import dasturlash.uz.entities.ProfileRolesEntity;
import dasturlash.uz.enums.RolesEnum;
import dasturlash.uz.repository.ProfileRepository;
import dasturlash.uz.repository.ProfileRolesRepository;
import dasturlash.uz.responseDto.ProfileInfoResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final ProfileRolesRepository profileRolesRepository;
    private final ProfileCustomRepo profileCustomRepo;

    public ProfileService(ProfileRepository profileRepository, ProfileRolesRepository profileRolesRepository, ProfileCustomRepo profileCustomRepo) {
        this.profileRepository = profileRepository;
        this.profileRolesRepository = profileRolesRepository;
        this.profileCustomRepo = profileCustomRepo;
    }

    public ProfileInfoDTO create(ProfileRequestDTO dto) {
        ProfileEntity profile = new ProfileEntity();

        profile.setName(dto.getName());
        profile.setSurname(dto.getSurname());
        profile.setPassword(dto.getPassword());
        profile.setEmail(dto.getEmail());
        profile.setPhone(dto.getPhone());
        profile.setPhotoId(dto.getPhotoId());

        profileRepository.save(profile);

        for (RolesEnum roleEnum : dto.getRolesEnumList()) {
            ProfileRolesEntity rolesEntity = new ProfileRolesEntity();

            rolesEntity.setProfile(profile);
            rolesEntity.setRole(roleEnum);
            profileRolesRepository.save(rolesEntity);
        }

        return toDTO(profile);
    }

    public ProfileInfoDTO getById(Integer id) {
        ProfileEntity profile = profileRepository.findByIdAndVisibleIsTrue(id);
        return profile != null ? toDTO(profile) : null;
    }

    public List<ProfileEntity> getListAll() {
        List<ProfileEntity> allProfile = new LinkedList<>();
        profileRepository.findAll().forEach(allProfile::add);
        return allProfile;
    }

    public ProfileInfoDTO update(Integer id, ProfileRequestDTO profileDTO) {

        ProfileEntity profile = profileRepository.findByIdAndVisibleIsTrue(id);

        if (profile == null) {
            throw new EntityNotFoundException("Profile not found or not visible with id: " + id);
        }

        if (profileDTO.getName() != null) profile.setName(profileDTO.getName());
        if (profileDTO.getSurname() != null) profile.setSurname(profileDTO.getSurname());
        if (profileDTO.getPhone() != null) profile.setPhone(profileDTO.getPhone());
        if (profileDTO.getEmail() != null) profile.setEmail(profileDTO.getEmail());
        if (profileDTO.getPassword() != null) profile.setPassword(profileDTO.getPassword());
        if (profileDTO.getPhotoId() != null) profile.setPhotoId(profileDTO.getPhotoId());
        if (profileDTO.getRolesEnumList() != null) {
            for (RolesEnum roleEnum : profileDTO.getRolesEnumList()) {
                ProfileRolesEntity rolesEntity = new ProfileRolesEntity();
                rolesEntity.setProfile(profile);
                rolesEntity.setRole(roleEnum);
                profileRolesRepository.save(rolesEntity);
            }
        }

        profileRepository.save(profile);
        return toDTO(profile);
    }


    public Boolean delete(Integer id) {
        ProfileEntity profile = profileRepository.findByIdAndVisibleIsTrue(id);
        if (profile == null) {
            throw new EntityNotFoundException("Profile not found or already deleted");
        }

        profile.setVisible(false);
        profileRepository.save(profile);
        return true;
    }

    private ProfileInfoDTO toDTO(ProfileEntity entity) {
        ProfileInfoDTO dto = new ProfileInfoDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setPassword(entity.getPassword());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setPhotoId(entity.getPhotoId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public ProfileInfoDTO photoUpdate(Integer id, String photoId) {
        ProfileEntity byId = profileRepository.findByIdAndVisibleIsTrue(id);
        if (photoId.isBlank()) throw new EntityNotFoundException("Photo id is blank");
        byId.setPhotoId(photoId);

        profileRepository.save(byId);
        return toDTO(byId);
    }

    public PageImpl<ProfileInfoDTO> pagination(Integer page, Integer size) {
        Sort sort = Sort.by("createdDate").descending();
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<ProfileEntity> entities = profileRepository.findAll(pageRequest);

        Long total = entities.getTotalElements();
        List<ProfileEntity> result = entities.getContent();

        List<ProfileInfoDTO> dtos = new LinkedList<>();
        for (ProfileEntity entity : result) {
            dtos.add(toDTO(entity));
        }
        return new PageImpl<>(dtos, pageRequest, total);
    }

    public Page<ProfileInfoResponseDTO> filter(Integer page, Integer size, FilterRequestDTO dto) {
        Page<ProfileEntity> pageResult = profileCustomRepo.filter(page, size, dto);

        Long total = pageResult.getTotalElements();
        List<ProfileEntity> dtos = pageResult.getContent();

        List<ProfileInfoResponseDTO> responseDTOS = new LinkedList<>();

        dtos.forEach(e -> responseDTOS.add(toResponse(e)));

        return new PageImpl<>(responseDTOS, PageRequest.of(page,size), total);
    }

    private ProfileInfoResponseDTO toResponse(ProfileEntity e) {
        ProfileInfoResponseDTO dto = new ProfileInfoResponseDTO();
        dto.setId(e.getId());
        dto.setName(e.getName());
        dto.setSurname(e.getSurname());
        dto.setEmail(e.getEmail());
        dto.setPhone(e.getPhone());
        dto.setPhotoId(e.getPhotoId());
        dto.setCreatedDate(e.getCreatedDate());
        dto.setStatus(e.getStatus());
        dto.setVisible(e.getVisible());

        return dto;
    }
}
