package dasturlash.uz.services;

import dasturlash.uz.dto.ProfileDTO;
import dasturlash.uz.entities.ProfileEntity;
import dasturlash.uz.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
        profile.setVisible(true);

        profileRepository.save(profile);
        return toDTO(profile);
    }

    public ProfileDTO getById(Integer id) {
        ProfileEntity profile = profileRepository.findByIdAndVisibleIsTrue(id);
        return toDTO(profile);
    }

    public ProfileDTO update(Integer id) {
        return null;
    }

    public Boolean delete(Integer id) {
        return null;
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
