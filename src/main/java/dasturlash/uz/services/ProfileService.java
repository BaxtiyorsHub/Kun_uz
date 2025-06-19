package dasturlash.uz.services;

import dasturlash.uz.config.CustomUserDetails;
import dasturlash.uz.jwtUtil.JwtUtil;
import dasturlash.uz.request.FilterRequestDTO;
import dasturlash.uz.exp.AppBadExp;
import dasturlash.uz.repository.customRepo.ProfileCustomRepo;
import dasturlash.uz.request.LoginDTO;
import dasturlash.uz.request.ProfileRequestDTO;
import dasturlash.uz.entities.ProfileEntity;
import dasturlash.uz.repository.ProfileRepository;
import dasturlash.uz.repository.ProfileRolesRepository;
import dasturlash.uz.responseDto.LoginResponseDTO;
import dasturlash.uz.responseDto.ProfileResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileCustomRepo profileCustomRepo;
    private final PasswordEncoder passwordEncoder;
    private final ProfileRoleService profileRoleService;
    private AuthenticationManager authenticationManager;

    public ProfileService(PasswordEncoder passwordEncoder,
                          ProfileRepository profileRepository,
                          ProfileRolesRepository profileRolesRepository,
                          ProfileCustomRepo profileCustomRepo,
                          ProfileRoleService profileRoleService
    ) {
        this.profileRoleService=profileRoleService;
        this.passwordEncoder = passwordEncoder;
        this.profileRepository = profileRepository;
        this.profileCustomRepo = profileCustomRepo;
    }

    // ADMIN/USER uchun
    public ProfileResponseDTO create(ProfileRequestDTO dto) {

        Optional<ProfileEntity> byPhone = profileRepository.findByPhone(dto.getPhone());
        if (byPhone.isPresent()) throw new AppBadExp("Phone already exists");

        ProfileEntity profile = new ProfileEntity();
        String code = passwordCoder(dto.getPassword());

        profile.setName(dto.getName());
        profile.setSurname(dto.getSurname());

        if (!passCheck(dto.getPassword(), code)) throw new AppBadExp("Password not matched");

        profile.setPassword(code);
        profile.setEmail(dto.getEmail());
        profile.setPhone(dto.getPhone());
        profile.setPhotoId(dto.getPhotoId());

        profileRepository.save(profile);

        if (dto.getRolesEnumList() != null) profileRoleService.createAndUpdate(profile, dto.getRolesEnumList());

        return toDTO(profile);
    }

    public LoginResponseDTO authorization(LoginDTO auth) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(auth.getUsername(), auth.getPassword()));

            if (authentication.isAuthenticated()) {
                CustomUserDetails profile = (CustomUserDetails) authentication.getPrincipal();
                LoginResponseDTO response = new LoginResponseDTO();
                response.setUsername(profile.getUsername());
                response.setJwtToken(JwtUtil.encode(profile.getUsername(),profile.getPassword()));
                return response;
            }
        } catch (BadCredentialsException e) {
            throw new UsernameNotFoundException("Phone or password wrong");
        }
        throw new UsernameNotFoundException("Phone or password wrong");
    }
    // ADMIN uchun
    public ProfileResponseDTO update(Integer id, ProfileRequestDTO profileDTO) {

        ProfileEntity profile = profileRepository.findByIdAndVisibleIsTrue(id);
        if (profile == null) throw new EntityNotFoundException("Profile not found");

        if (profileDTO.getName() != null) profile.setName(profileDTO.getName());
        if (profileDTO.getSurname() != null) profile.setSurname(profileDTO.getSurname());
        if (profileDTO.getPhone() != null) profile.setPhone(profileDTO.getPhone());
        if (profileDTO.getEmail() != null) profile.setEmail(profileDTO.getEmail());
        if (profileDTO.getPassword() != null) {
            String code = passwordCoder(profileDTO.getPassword());
            if (!passCheck(profileDTO.getPassword(), code)) throw new AppBadExp("Password not matched");
            profile.setPassword(code);
        }
        if (profileDTO.getPhotoId() != null) profile.setPhotoId(profileDTO.getPhotoId());
        if (profileDTO.getRolesEnumList() != null) {
            profileRoleService.createAndUpdate(profile, profileDTO.getRolesEnumList());
        }

        profileRepository.save(profile);
        return toDTO(profile);
    }

    public ProfileResponseDTO photoUpdate(Integer id, String photoId) {
        ProfileEntity byId = profileRepository.findByIdAndVisibleIsTrue(id);
        if (photoId.isBlank()) throw new EntityNotFoundException("Photo id is blank");
        byId.setPhotoId(photoId);

        profileRepository.save(byId);
        return toDTO(byId);
    }

    public ProfileResponseDTO getById(Integer id) {
        ProfileEntity profile = profileRepository.findByIdAndVisibleIsTrue(id);
        return profile != null ? toDTO(profile) : null;
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
    // ADMIN uchun
    public PageImpl<ProfileResponseDTO> pagination(Integer page, Integer size) {
        Sort sort = Sort.by("createdDate").descending();
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<ProfileEntity> entities = profileRepository.findAll(pageRequest);

        Long total = entities.getTotalElements();
        List<ProfileEntity> result = entities.getContent();

        List<ProfileResponseDTO> dtos = new LinkedList<>();
        for (ProfileEntity entity : result) {
            dtos.add(toDTO(entity));
        }
        return new PageImpl<>(dtos, pageRequest, total);
    }
    // ADMIN uchun
    public Page<ProfileResponseDTO> filter(Integer page, Integer size, FilterRequestDTO dto) {
        Page<ProfileEntity> pageResult = profileCustomRepo.filter(page, size, dto);

        Long total = pageResult.getTotalElements();
        List<ProfileEntity> dtos = pageResult.getContent();

        List<ProfileResponseDTO> responseDTOS = new LinkedList<>();

        dtos.forEach(e -> responseDTOS.add(filterResponse(e)));

        return new PageImpl<>(responseDTOS, pageResult.getPageable(), total);
    }
    // ADMIN uchun
    private ProfileResponseDTO filterResponse(ProfileEntity e) {
        ProfileResponseDTO dto = new ProfileResponseDTO();
        dto.setId(e.getId());
        dto.setName(e.getName());
        dto.setSurname(e.getSurname());
        dto.setEmail(e.getEmail());
        dto.setPhone(e.getPhone());
        dto.setPassword(e.getPassword());
        dto.setPhotoId(e.getPhotoId());
        dto.setCreatedDate(e.getCreatedDate());
        dto.setStatus(e.getStatus());
        dto.setVisible(e.getVisible());

        return dto;
    }
    // ADMIN uchun
    private ProfileResponseDTO toDTO(ProfileEntity entity) {
        ProfileResponseDTO dto = new ProfileResponseDTO();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setPhotoId(entity.getPhotoId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    private boolean passCheck(String password, String passCode) {
        return passwordEncoder.matches(password, passCode);
    }

    private String passwordCoder(String password) {
        return passwordEncoder.encode(password);
    }
}
