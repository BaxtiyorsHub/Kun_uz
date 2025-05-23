package dasturlash.uz.services;

import dasturlash.uz.responseDto.ProfileInfoDTO;
import dasturlash.uz.dto.ProfileRequestDTO;
import dasturlash.uz.entities.ProfileEntity;
import dasturlash.uz.entities.ProfileRolesEntity;
import dasturlash.uz.enums.RolesEnum;
import dasturlash.uz.repository.ProfileRepository;
import dasturlash.uz.repository.ProfileRolesRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final ProfileRolesRepository profileRolesRepository;

    public ProfileService(ProfileRepository profileRepository, ProfileRolesRepository profileRolesRepository) {
        this.profileRepository = profileRepository;
        this.profileRolesRepository = profileRolesRepository;
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
            for (RolesEnum roleEnum : profileDTO.getRolesEnumList()){
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
}
