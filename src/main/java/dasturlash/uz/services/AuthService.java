package dasturlash.uz.services;

import dasturlash.uz.request.auth.LoginRequestDTO;
import dasturlash.uz.request.auth.RegistrationDTO;
import dasturlash.uz.entities.ProfileEntity;
import dasturlash.uz.enums.RolesEnum;
import dasturlash.uz.enums.Status;
import dasturlash.uz.exceptions.AppBadExp;
import dasturlash.uz.repository.ProfileRepository;
import dasturlash.uz.response.LoginResponseDTO;
import dasturlash.uz.services.connectedServices.ProfileRoleService;
import dasturlash.uz.services.email.EmailHistoryService;
import dasturlash.uz.services.email.EmailSenderService;
import dasturlash.uz.services.sms.SmsHistoryService;
import dasturlash.uz.services.sms.SmsSenderService;
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
    private final SmsSenderService smsSenderService;
    private final SmsHistoryService smsHistoryService;

    public AuthService(ProfileRepository profileRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder,
                       ProfileRoleService profileRoleService,
                       EmailSenderService emailSenderService,
                       EmailHistoryService emailHistoryService,
                       SmsSenderService smsSenderService, SmsHistoryService smsHistoryService) {
        this.smsSenderService = smsSenderService;
        this.profileRepository = profileRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.profileRoleService = profileRoleService;
        this.emailSenderService = emailSenderService;
        this.emailHistoryService = emailHistoryService;
        this.smsHistoryService = smsHistoryService;
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
        profile.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        if (dto.getUsername().contains("@")) {
            profile.setEmail(dto.getUsername());
            emailSenderService.sendRegistrationStyledEmail(dto.getUsername());
        } else if (isValidPhone(dto.getUsername())) {
            profile.setPhone(dto.getUsername());
            smsSenderService.sendRegistration(dto);
        }
        profileRepository.save(profile);

        profileRoleService.createAndUpdate(profile, List.of(RolesEnum.USER));

        return "Verification code sent check your email";
    }

    @Transactional
    public String emailVerification(String email, String code) {
        ProfileEntity profile = findUser(email);

        if (profile.getStatus().equals(Status.ACTIVE)) throw new AppBadExp("Username already verified");
        if (code == null || code.isBlank()) throw new AppBadExp("Verification code is empty");

        if (emailHistoryService.isSmsValidationCheck(email, code)) {
            profile.setStatus(Status.ACTIVE);
            profileRepository.save(profile);
            emailHistoryService.changeCodeStatus(email);
        }
        return "Email verification successful";
    }

    @Transactional
    public String phoneVerification(String phone, String code) {
        ProfileEntity profile = findUser(phone);

        if (profile.getStatus().equals(Status.ACTIVE)) throw new AppBadExp("Username already verified");
        if (code == null || code.isBlank()) throw new AppBadExp("Verification code is empty");

        if (emailHistoryService.isSmsValidationCheck(phone, code)) {
            profile.setStatus(Status.ACTIVE);
            profileRepository.save(profile);
            smsHistoryService.changeStatus(phone);
        }
        return "Phone verification successful";
    }

    public LoginResponseDTO login(@Valid LoginRequestDTO dto) {
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
        smsSenderService.sendRegistration(dto);

        return "Verification code sent check your messages";
    }

    private ProfileEntity findUser(String username) {
        Optional<ProfileEntity> byId = profileRepository.findByPhoneOrEmailAndVisibleIsTrue(username);
        if (byId.isEmpty()) throw new AppBadExp("Username not found");

        return byId.get();
    }

    public static boolean isValidPhone(String phone) {
        return phone.matches("^(\\+998|998)?[-\\s]?\\(?\\d{2}\\)?[-\\s]?\\d{3}[-\\s]?\\d{2}[-\\s]?\\d{2}$");
    }
}
