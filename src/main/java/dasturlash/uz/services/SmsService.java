package dasturlash.uz.services;

import dasturlash.uz.request.SmsRequestDTO;
import dasturlash.uz.util.RandomUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SmsService {

    private final RestTemplate restTemplate;

    public SmsService(RestTemplate template) {
        this.restTemplate = template;
    }

    public void sendRegistration(String phone) {
        Integer smsCode = RandomUtil.getRandomInt5();
        String body = "<#>Kun.uz partali. Ro'yxatdan o'tish uchun tasdiqlash kodi : " + smsCode;
        sendSms(body, phone);
    }

    private void sendSms(String message, String phone) {
        SmsRequestDTO body = new SmsRequestDTO();
        body.setMessage(message);
        body.setPhone(phone);

        String url = "https://notify.eskiz.uz/api/message/sms/send";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer ");

        RequestEntity<SmsRequestDTO> request = RequestEntity
                .post(url)
                .headers(headers)
                .body(body);

        restTemplate.exchange(request, String.class);
    }
}
