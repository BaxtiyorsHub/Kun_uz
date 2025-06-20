package dasturlash.uz.services.email;

import dasturlash.uz.jwtUtil.JwtUtil;
import dasturlash.uz.util.RandomUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
//import jakarta.validation.constraints.Email;
//import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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

/*    private void sendSimpleMessage(String subject, String body, @Email String toAccount) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(fromAccount);
        msg.setTo(toAccount);
        msg.setSubject(subject);
        msg.setText(body);
        javaMailSender.send(msg);

    }

    public void sendRegistrationEmail(String toAccount) {
        String subject = "Kun Uz - Tasdiqlash kodi";
        Integer smsCode = RandomUtil.getRandomInt5();
        String encode = JwtUtil.encode(toAccount, smsCode.toString());
        String body = "Click there: http://localhost:8080/api/v1/auth/registration/verification/%s";
        body = String.format(body,encode);

        sendSimpleMessage(subject, body, toAccount);

        emailHistoryService.create(fromAccount, smsCode, toAccount);
    }*/

    public void sendRegistrationStyledEmail(String toAccount) {
        Integer smsCode = RandomUtil.getRandomInt5();
        String body = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Title</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<h1 style=\"text-align: center\">Kunuz Portaliga xush kelibsiz.</h1>\n" +
                "<br>\n" +
                "<h4>Ro'yhatdan o'tishni tugatish uchun quyidagi linkga bosing</h4>\n" +
                "<a style=\" background-color: indianred;\n" +
                "  color: black;\n" +
                "  padding: 10px 20px;\n" +
                "  text-align: center;\n" +
                "  text-decoration: none;\n" +
                "  display: inline-block;\"\n" +
                "   href=\"http://localhost:8081/api/v1/auth/registration/email/verification/%s\">Ro'yhatdan\n" +
                "    o'tishni tugatish</a>\n" +
                "\n" +
                "\n" +
                "</body>\n" +
                "</html>";
        String jwtToken = JwtUtil.encode(toAccount, smsCode.toString());
        body = String.format(body, jwtToken);
        // send
        sendMimeMessage("Registration complete", body, toAccount);
        // save to db
        emailHistoryService.create(body, smsCode, toAccount);
    }

    public void sendMimeMessage(String subject, String body, String toAccount) {
        try {
            MimeMessage msg = javaMailSender.createMimeMessage();
            msg.setFrom(fromAccount);

            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            helper.setTo(toAccount);
            helper.setSubject(subject);
            helper.setText(body, true);
            javaMailSender.send(msg);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
