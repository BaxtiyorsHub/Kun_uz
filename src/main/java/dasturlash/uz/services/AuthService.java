package dasturlash.uz.services;

import dasturlash.uz.dto.auth.RegistrationDTO;
import dasturlash.uz.entities.ProfileEntity;
import dasturlash.uz.enums.RolesEnum;
import dasturlash.uz.enums.Status;
import dasturlash.uz.exp.AppBadExp;
import dasturlash.uz.repository.ProfileRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

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

    public String registration(RegistrationDTO dto) throws InterruptedException {
        Optional<ProfileEntity> existOptional = profileRepository.findByPhoneOrEmailAndVisibleIsTrue(dto.getUsername());
        if (existOptional.isPresent()) {
            ProfileEntity existsProfile = existOptional.get();
            if (existsProfile.getStatus().equals(Status.NOT_ACTIVE)) {
                profileRoleService.deleteRolesByProfileId(existsProfile.getId());
                profileRepository.deleteById(existsProfile.getId());
            } else {
                throw new AppBadExp("Username already exists");
            }
        }

        ProfileEntity profile = new ProfileEntity();
        profile.setName(dto.getName());
        profile.setSurname(dto.getSurname());
        if (dto.getUsername().contains("@")) profile.setEmail(dto.getUsername());
        profile.setPhone(dto.getUsername());
        profile.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));

        profileRepository.save(profile);
        // create profile roles

        Random randomCode = new Random();
        Integer code = randomCode.nextInt(000001,999999);

        profileRoleService.createAndUpdate(profile , List.of(RolesEnum.USER));

        emailSenderService.sendSimpleMessage("Registration completion",
                code.toString(),
                dto.getUsername());
        Thread.sleep(60000);

        return "Tasdiqlash kodi ketdi mazgi qara.";
    }

}
