package dasturlash.uz.services;

import dasturlash.uz.entities.AuthRegistrationCodes;
import dasturlash.uz.enums.AuthStatus;
import dasturlash.uz.repository.CodeRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;


@Service
public class EmailSenderService {

    @Value("${spring.mail.username}")
    private String fromAccount;
    private final JavaMailSender javaMailSender;
    private CodeRepository codeRepository;

    public EmailSenderService(CodeRepository codeRepository, JavaMailSender javaMailSender) {
        this.codeRepository = codeRepository;
        this.javaMailSender = javaMailSender;
    }

    public Integer sendSimpleMessage(String subject, String body, String toAccount) throws InterruptedException {
        /*List<AuthRegistrationCodes> byToEmail = codeRepository.findByToEmail(toAccount);
        if (byToEmail != null && !byToEmail.isEmpty()) {
            for (AuthRegistrationCodes authRegistrationCodes : byToEmail) {
                if (authRegistrationCodes.getStatus().equals(AuthStatus.SENT)) {
                    return -1;
                }
            }
            codeRepository.deleteAll(byToEmail);
        }*/

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(fromAccount);
        msg.setTo(toAccount);
        msg.setSubject(subject);
        msg.setText(body);
        javaMailSender.send(msg);

        AuthRegistrationCodes authRegistrationCodes = new AuthRegistrationCodes();
        authRegistrationCodes.setCode(body);
        authRegistrationCodes.setToEmail(toAccount);
        authRegistrationCodes.setFromEmail(fromAccount);
        authRegistrationCodes.setStatus(AuthStatus.SENT);

        codeRepository.save(authRegistrationCodes);
        return 0;
    }

}
