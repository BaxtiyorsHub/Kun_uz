package dasturlash.uz.services.email;

import dasturlash.uz.entities.EmailHistoryEntity;
import dasturlash.uz.enums.CodeStatus;
import dasturlash.uz.exp.AppBadExp;
import dasturlash.uz.repository.EmailHistoryRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class EmailHistoryService {

    private final EmailHistoryRepository emailHistoryRepository;
    private Integer attempt = 0;

    public EmailHistoryService(EmailHistoryRepository emailHistoryRepository) {
        this.emailHistoryRepository = emailHistoryRepository;
    }

    @Transactional
    public void create(@NotBlank String body, Integer code, @NotBlank String toAccount) {
        EmailHistoryEntity entity = findEmail(toAccount);

        entity.setAttempts(entity.getAttempts() + 1);
        entity.setAttempts(++attempt);
        entity.setStatus(CodeStatus.NOT_USED);
        entity.setCode(code.toString());
        entity.setToEmail(toAccount);
        entity.setBody(body);
    }

    /**
     * @param username
     * @param code
     * @return Checking email_code/phone_code true or false
     */
    public boolean isSmsValidationCheck(String username, String code) {
        EmailHistoryEntity entity = findEmail(username);

        if (!entity.getCode().equals(code) || entity.getStatus().equals(CodeStatus.NOT_USED)) {
            //  20:32:40           =   20:30.40  + 0:3:00
            LocalDateTime extDate = entity.getCreatedDate().plusMinutes(3);
            // now  20:31:30  >  20:32:40    |     now 20:35:30  >  20:32:40
            return !LocalDateTime.now().isAfter(extDate);
        }
        throw new AppBadExp("Via email verification failed");
    }

    private EmailHistoryEntity findEmail(String username) {
        Optional<EmailHistoryEntity> byToEmail = emailHistoryRepository.findByToEmail(username);
        if (byToEmail.isEmpty()) throw new AppBadExp("Email not found");

        EmailHistoryEntity entity = byToEmail.get();
        if (entity.getAttempts() == 4) {
            if (entity.getHoursBan().isBefore(LocalDateTime.now())) throw new AppBadExp("Via email verification failed");
            return new EmailHistoryEntity();
        }

        return byToEmail.get();
    }

    @Transactional
    public void changeCodeStatus(String email) {
        if (email.isBlank()) throw new AppBadExp("Email is blank");
        findEmail(email).setStatus(CodeStatus.USED);
    }
}