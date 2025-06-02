package dasturlash.uz.services;

import dasturlash.uz.jwtUtil.JwtUtil;
import dasturlash.uz.util.RandomUtil;
import jakarta.validation.constraints.Email;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

@Service
public class EmailSenderService {

    @Value("${spring.mail.username}")
    private String fromAccount;
    private final JavaMailSender javaMailSender;
    private EmailHistoryService emailHistoryService;

    public EmailSenderService(EmailHistoryService emailHistoryService, JavaMailSender javaMailSender) {
        this.emailHistoryService = emailHistoryService;
        this.javaMailSender = javaMailSender;
    }

    private void sendSimpleMessage(String subject, String body, Integer code, @Email String toAccount) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(fromAccount);
        msg.setTo(toAccount);
        msg.setSubject(subject);
        msg.setText(body);
        javaMailSender.send(msg);

        emailHistoryService.create(fromAccount, code, toAccount);
    }

    public void sendRegistrationEmail(String toAccount) {
        String subject = "Kun Uz - Tasdiqlash kodi";
        Integer smsCode = RandomUtil.getRandomInt5();
        String encode = JwtUtil.encode(toAccount, smsCode.toString());
        String body = "Click there: http://localhost:8081/api/v1/auth/registration/email/verification/";
        body += encode;
        body = String.format(body, toAccount, smsCode);

        sendSimpleMessage(subject, body, smsCode, toAccount);

    }
}
