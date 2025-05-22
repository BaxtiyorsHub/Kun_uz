package dasturlash.uz.services;

import dasturlash.uz.dto.ProfileDTO;
import dasturlash.uz.entities.ProfileEntity;
import dasturlash.uz.repository.ProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    public ProfileDTO create(ProfileDTO dto) {
        ProfileEntity profile = new ProfileEntity();
        profile.setName(dto.getName());
        profile.setSurname(dto.getSurname());
        profile.setPassword(dto.getPassword());
        profile.setEmail(dto.getEmail());
        profile.setPhone(dto.getPhone());
        profile.setPhotoId(dto.getPhotoId());

        profileRepository.save(profile);
        return toDTO(profile);
    }

    public ProfileDTO getById(Integer id) {
        ProfileEntity profile = profileRepository.findByIdAndVisibleIsTrue(id);
        return profile != null ? toDTO(profile) : null;
    }

    public List<ProfileEntity> getListAll() {
        List<ProfileEntity> allProfile = new LinkedList<>();
        profileRepository.findAll().forEach(allProfile::add);
        return allProfile;
    }

    public ProfileDTO update(Integer id, ProfileDTO profileDTO) {

        ProfileEntity profile = profileRepository.findByIdAndVisibleIsTrue(id);

        if (profile == null) {
            throw new EntityNotFoundException("Profile not found or not visible with id: " + profileDTO.getId());
        }

        if (profileDTO.getName() != null) profile.setName(profileDTO.getName());
        if (profileDTO.getSurname() != null) profile.setSurname(profileDTO.getSurname());
        if (profileDTO.getPhone() != null) profile.setPhone(profileDTO.getPhone());
        if (profileDTO.getEmail() != null) profile.setEmail(profileDTO.getEmail());
        if (profileDTO.getPassword() != null) profile.setPassword(profileDTO.getPassword());
        if (profileDTO.getPhotoId() != null) profile.setPhotoId(profileDTO.getPhotoId());


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

    private ProfileDTO toDTO(ProfileEntity entity) {
        ProfileDTO dto = new ProfileDTO();
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
