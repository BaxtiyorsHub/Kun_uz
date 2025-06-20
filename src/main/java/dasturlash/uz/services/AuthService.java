package dasturlash.uz.services;

import dasturlash.uz.request.LoginDTO;
import dasturlash.uz.request.auth.RegistrationDTO;
import dasturlash.uz.entities.ProfileEntity;
import dasturlash.uz.enums.RolesEnum;
import dasturlash.uz.enums.Status;
import dasturlash.uz.exp.AppBadExp;
import dasturlash.uz.repository.ProfileRepository;
import dasturlash.uz.responseDto.LoginResponseDTO;
import dasturlash.uz.services.email.EmailHistoryService;
import dasturlash.uz.services.email.EmailSenderService;
import dasturlash.uz.services.sms.SmsService;
import jakarta.transaction.Transactional;
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

    @Transactional
    public String registration(RegistrationDTO dto) {
        ProfileEntity user = findUser(dto.getUsername());

        if (user.getStatus().equals(Status.NOT_ACTIVE)) {
            profileRoleService.deleteRolesByProfileId(user.getId());
            profileRepository.deleteById(user.getId());
        } else {
            throw new AppBadExp("Username already exists");
        }

        ProfileEntity profile = new ProfileEntity();
        profile.setName(dto.getName());
        profile.setSurname(dto.getSurname());
        if (dto.getUsername().contains("@")) profile.setEmail(dto.getUsername());
        profile.setPhone(dto.getUsername());
        profile.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));

        profileRepository.save(profile);
        // create profile roles
        profileRoleService.createAndUpdate(profile, List.of(RolesEnum.USER));

        emailSenderService.sendRegistrationStyledEmail(dto.getUsername());

        return "Verification code sent check your email";
    }

    public String regEmailVerification(String username, String code) {
        ProfileEntity profile = findUser(username);

        if (!profile.getStatus().equals(Status.NOT_ACTIVE)) throw new AppBadExp("Username already verified");

        if (code == null || code.isBlank()) throw new AppBadExp("Verification code is empty");

        if (emailHistoryService.isSmsValidationCheck(username, code)) {
            profile.setStatus(Status.ACTIVE);
            profileRepository.save(profile);
            return "Email verification successful";
        }
        throw new AppBadExp("Verification code is not completed");
    }

    public LoginResponseDTO login(@Valid LoginDTO dto) {
        String username = dto.getUsername();
        String password = dto.getPassword();

        ProfileEntity profile = findUser(username);

        if (!bCryptPasswordEncoder.matches(password, profile.getPassword()))
            throw new AppBadExp("Password is incorrect");

        if (profile.getStatus().equals(Status.ACTIVE)) {
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

    private ProfileEntity findUser(String username) {
        Optional<ProfileEntity> byId = profileRepository.findByPhoneOrEmailAndVisibleIsTrue(username);
        if (byId.isEmpty()) throw new AppBadExp("Username not found");

        return byId.get();
    }
}
