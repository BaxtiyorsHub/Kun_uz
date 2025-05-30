package dasturlash.uz.services;

import dasturlash.uz.dto.auth.RegistrationDTO;
import dasturlash.uz.entities.ProfileEntity;
import dasturlash.uz.enums.Status;
import dasturlash.uz.exp.AppBadExp;
import dasturlash.uz.repository.ProfileRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final ProfileRepository profileRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ProfileRoleService profileRoleService;
    private final EmailSenderService emailSenderService;

    public AuthService(ProfileRepository profileRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder,
                       ProfileRoleService profileRoleService,
                       EmailSenderService emailSenderService) {
        this.profileRepository = profileRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.profileRoleService = profileRoleService;
        this.emailSenderService = emailSenderService;
    }

    public String registration(RegistrationDTO dto) {
        // 1. validation TODO in DTO class
        // 2.   1213
        Optional<ProfileEntity> existOptional = profileRepository.findByPhoneOrEmailAndVisibleIsTrue(dto.getUsername());
        if (existOptional.isPresent()) {
            ProfileEntity existsProfile = existOptional.get();
            if (existsProfile.getStatus().equals(Status.NOT_ACTIVE)) {
                profileRoleService.deleteRolesByProfileId(existsProfile.getId());
                profileRepository.deleteById(existsProfile.getId()); // delete
            } else {
                throw new AppBadExp("Username already exists");
            }
        }
        // create profile
        ProfileEntity profile = new ProfileEntity();
        profile.setName(dto.getName());
        profile.setSurname(dto.getSurname());
       // profile.setUsername(dto.getUsername());
        profile.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        profile.setVisible(true);
        profile.setStatus(Status.NOT_ACTIVE);
        profileRepository.save(profile);
        // create profile roles
        //profileRoleService.createAndUpdate(profile.getId(), RolesEnum.USER);
        emailSenderService.sendSimpleMessage("Registration complete",
                "Sms code 12345",
                dto.getUsername());

        // response
        return "Tastiqlash kodi ketdi mazgi qara.";
    }

}
