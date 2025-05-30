package dasturlash.uz.services;

import dasturlash.uz.util.RandomUtil;
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

    private void sendSimpleMessage(String subject, String body,Integer code, String toAccount) throws InterruptedException {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(fromAccount);
        msg.setTo(toAccount);
        msg.setSubject(subject);
        msg.setText(body);
        javaMailSender.send(msg);

        emailHistoryService.create(fromAccount,code,toAccount);
    }

    public void sendRegistrationEmail(String toAccount) throws InterruptedException {
        String subject = "Kun Uz - Tasdiqlash kodi";
        int randomInt5 = RandomUtil.getRandomInt5();
        String body = "Kun Uz - Tasdiqlash kodingiz: " + randomInt5;

        sendSimpleMessage(subject, body,randomInt5, toAccount);

    }
}
