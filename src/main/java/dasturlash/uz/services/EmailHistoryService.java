package dasturlash.uz.services;

import dasturlash.uz.entities.EmailHistoryEntity;
import dasturlash.uz.enums.AuthStatus;
import dasturlash.uz.exp.AppBadExp;
import dasturlash.uz.repository.CodeRepository;
import jakarta.validation.constraints.Email;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class EmailHistoryService {

    private final CodeRepository codeRepository;
    private Integer attempt = 0;

    public EmailHistoryService(CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

    public void create(String fromEmail, Integer code, String toAccount) {
        Optional<EmailHistoryEntity> byToEmail = codeRepository.findByToEmail(toAccount);
        
        EmailHistoryEntity entity;
        if (byToEmail.isPresent()) {
            entity = byToEmail.get();
            if (entity.getAttempts() > 3) {
                throw new AppBadExp("Email send limit exceeded");
            }
            entity.setAttempts(entity.getAttempts() + 1);
        } else {
            entity = new EmailHistoryEntity();
            entity.setAttempts(++attempt);
        }

        entity.setStatus(AuthStatus.SENT);
        entity.setCode(code.toString());
        entity.setToEmail(toAccount);

        codeRepository.save(entity);
    }

    public boolean isSmsValidationCheck(String username, String code) {
        Optional<EmailHistoryEntity> byToEmail = codeRepository.findByToEmail(username);
        if (byToEmail.isEmpty()) return false;

        EmailHistoryEntity entity = byToEmail.get();
        if (!entity.getCode().equals(code)) return false;

        //  20:32:40           =   20:30.40  + 0:2:00
        LocalDateTime extDate = entity.getCreatedDate().plusMinutes(3);
        // now  20:31:30  >  20:32:40    |     now 20:35:30  >  20:32:40
        if (LocalDateTime.now().isAfter(extDate)) return false;

        return true;
    }
}