package dasturlash.uz.services;

import dasturlash.uz.entities.EmailHistoryEntity;
import dasturlash.uz.enums.AuthStatus;
import dasturlash.uz.repository.CodeRepository;
import org.springframework.stereotype.Service;

@Service
public class EmailHistoryService {

    private final CodeRepository codeRepository;

    public EmailHistoryService(CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

    public void create(String fromEmail, Integer code, String toAccount) {
        EmailHistoryEntity entity = new EmailHistoryEntity();
        entity.setFromEmail(fromEmail);
        entity.setStatus(AuthStatus.SENT);
        entity.setCode(code.toString());
        entity.setToEmail(toAccount);
        codeRepository.save(entity);
    }

    public boolean isSmsValidationCheck(String username, String code) {

        return false;
    }
}
