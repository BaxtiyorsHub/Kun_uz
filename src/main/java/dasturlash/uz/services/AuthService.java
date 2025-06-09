package dasturlash.uz.services;

import dasturlash.uz.dto.JwtDTO;
import dasturlash.uz.jwtUtil.JwtUtil;
import dasturlash.uz.request.LoginDTO;
import dasturlash.uz.request.auth.RegistrationDTO;
import dasturlash.uz.entities.ProfileEntity;
import dasturlash.uz.enums.RolesEnum;
import dasturlash.uz.enums.Status;
import dasturlash.uz.exp.AppBadExp;
import dasturlash.uz.repository.ProfileRepository;
import dasturlash.uz.responseDto.LoginResponseDTO;
import jakarta.validation.Valid;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

    private final ProfileRepository profileRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ProfileRoleService profileRoleService;
    private final EmailSenderService emailSenderService;
    private final EmailHistoryService emailHistoryService;
    private final SmsService smsService;

    public AuthService(ProfileRepository profileRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder,
                       ProfileRoleService profileRoleService,
                       EmailSenderService emailSenderService,
                       EmailHistoryService emailHistoryService,
                       SmsService smsService) {
        this.smsService = smsService;
        this.profileRepository = profileRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.profileRoleService = profileRoleService;
        this.emailSenderService = emailSenderService;
        this.emailHistoryService = emailHistoryService;
    }

    public String registration(RegistrationDTO dto) {
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
        profileRoleService.createAndUpdate(profile , List.of(RolesEnum.USER));

        emailSenderService.sendRegistrationStyledEmail(dto.getUsername());

        return "Tasdiqlash kodi ketdi mazgi qara.";
    }

    public String regEmailVerification(String username, String code) {
        Optional<ProfileEntity> byId = profileRepository.findByPhoneOrEmailAndVisibleIsTrue(username);
        if (byId.isEmpty()) throw new AppBadExp("Username not found");

        ProfileEntity profile = byId.get();
        if (!profile.getStatus().equals(Status.NOT_ACTIVE)) throw new AppBadExp("Username already verified");

        if (code == null || code.isBlank()) throw new AppBadExp("Verification code is empty");

        if (emailHistoryService.isSmsValidationCheck(username,code)) {
            profile.setStatus(Status.ACTIVE);
            profileRepository.save(profile);
            return "Email verification successful";
        }
        throw new AppBadExp("Verification code is not completed") ;
    }

    public LoginResponseDTO login(@Valid LoginDTO dto) {
        String username = dto.getUsername();
        String password = dto.getPassword();

        Optional<ProfileEntity> byId = profileRepository.findByPhoneOrEmailAndVisibleIsTrue(username);
        if (byId.isEmpty()) throw new AppBadExp("Username not found");

        if (!bCryptPasswordEncoder.matches(password, byId.get().getPassword())) throw new AppBadExp("Password is incorrect");

        if (byId.get().getStatus().equals(Status.ACTIVE) ) {
            ProfileEntity profile = byId.get();
            List<RolesEnum> roles = profileRepository.roles(profile.getId());

            LoginResponseDTO responseDTO = new LoginResponseDTO();
            responseDTO.setName(profile.getName());
            responseDTO.setUsername(username);
            responseDTO.setSurname(profile.getSurname());
            responseDTO.setRolesList(roles);

            return responseDTO;
        }
        throw new AppBadExp("Something went wrong");
    }

    public String sendSmsToPhone(RegistrationDTO dto) {
        if (dto.getUsername().contains("@")) throw new AppBadExp("Something went wrong");
        smsService.sendRegistration(dto.getUsername());

        return "Tasdiqlash kodi ketdi mazgi.";
    }
}
