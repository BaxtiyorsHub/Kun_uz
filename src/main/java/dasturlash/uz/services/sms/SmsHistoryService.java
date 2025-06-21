package dasturlash.uz.services.sms;

import dasturlash.uz.entities.SmsHistoryEntity;
import dasturlash.uz.enums.CodeStatus;
import dasturlash.uz.repository.sms.SmsHistoryRepository;
import dasturlash.uz.request.auth.RegistrationDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class SmsHistoryService {

    private final SmsHistoryRepository smsHistoryRepository;

    public SmsHistoryService(SmsHistoryRepository smsHistoryRepository) {
        this.smsHistoryRepository = smsHistoryRepository;
    }


    public void create(@Valid RegistrationDTO dto, Integer code) {
        SmsHistoryEntity smsHistoryEntity = new SmsHistoryEntity();

        smsHistoryEntity.setCode(code);
        smsHistoryEntity.setToPhone(dto.getUsername());
        smsHistoryEntity.setStatus(CodeStatus.NOT_USED);

        smsHistoryRepository.save(smsHistoryEntity);
    }

    @Transactional
    public void changeStatus(String phone) {
        SmsHistoryEntity byToPhone = smsHistoryRepository.findByToPhone(phone);
        byToPhone.setStatus(CodeStatus.USED);
    }
}
