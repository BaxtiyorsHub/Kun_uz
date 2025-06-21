package dasturlash.uz.services.sms;

import dasturlash.uz.config.MyRestTemplate;
import dasturlash.uz.request.SmsRequestDTO;
import dasturlash.uz.request.auth.RegistrationDTO;
import dasturlash.uz.util.RandomUtil;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;

@Service
public class SmsSenderService {

    private final MyRestTemplate template;
    private final SmsHistoryService smsHistoryService;

    public SmsSenderService(MyRestTemplate template, SmsHistoryService smsHistoryService) {
        this.template = template;
        this.smsHistoryService = smsHistoryService;
    }

    public void sendRegistration(RegistrationDTO dto) {
        int smsCode = RandomUtil.getRandomInt5();
        String body = "<#>Kun.uz sayti. Ro'yxatdan o'tish uchun tasdiqlash kodi : " + smsCode;
        sendSms(body, dto.getUsername());

        smsHistoryService.create(dto,smsCode);
    }

    private void sendSms(String message, String phone) {
        String formattedPhone = formatPhoneNumber(phone);

        SmsRequestDTO body = new SmsRequestDTO();
        body.setMessage(message);
        body.setPhone(formattedPhone);

        String url = "https://notify.eskiz.uz/api/message/sms/send";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.setBearerAuth("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3NTIwNTMxNTAsImlhdCI6MTc0OTQ2MTE1MCwicm9sZSI6InRlc3QiLCJzaWduIjoiNDJjYzdkZDFiOTc4NjJkMWZiNTgxMGE0ZjY5ZGVmYjhjNWU4NjdmZmY3OTI0ZDQ5YjhlMTNiYjE4MjE2ZjE2MCIsInN1YiI6IjExMjY4In0.Yp_cnUZj0CTp88wY6Jk36Ab8WA_KfyzUMz6bsm_uyBw");

        RequestEntity<SmsRequestDTO> request = RequestEntity
                .post(url)
                .headers(headers)
                .body(body);

        template.restTemplate().exchange(request, String.class);
    }

    private String formatPhoneNumber(String phone) {
        // + belgisi, bo'sh joylar yoki boshqa belgilarni olib tashlab, 998 bilan boshlanishini tekshirish
        phone = phone.replaceAll("[^0-9]", ""); // faqat raqamlar qoldiriladi

        if (phone.startsWith("998")) {
            return phone;
        } else if (phone.length() == 9) {
            // Faqat "901234567" ko'rinishida kelsa
            return "998" + phone;
        } else if (phone.length() == 12 && phone.startsWith("998")) {
            return phone;
        } else {
            throw new IllegalArgumentException("Phone number is not valid: " + phone);
        }
    }
}
