package dasturlash.uz.services.sms;

import dasturlash.uz.dto.SmsProviderTokenDTO;
import dasturlash.uz.entities.SmsTokenEntity;
import dasturlash.uz.repository.SmsTokenRepository;
import dasturlash.uz.response.SmsTokenProviderResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SmsTokenService {

    private final SmsTokenRepository smsTokenRepository;
    private final RestTemplate restTemplate;
    @Value("${sms.eskiz.email}")
    private String email;
    @Value("${sms.eskiz.password}")
    private String password;

    private final String url = "https://notify.eskiz.uz/api/";

    public SmsTokenService(SmsTokenRepository smsTokenRepository, RestTemplate restTemplate) {
        this.smsTokenRepository = smsTokenRepository;
        this.restTemplate = restTemplate;
    }

    public String getToken() {
        Optional<SmsTokenEntity> optional = smsTokenRepository.findTopByOrderByCreatedDateDesc();
        if (optional.isPresent()) {
            SmsTokenEntity smsTokenEntity = optional.get();
            LocalDateTime tokenDate = smsTokenEntity.getCreatedDate();
            LocalDateTime now = LocalDateTime.now();
            long days = Duration.between(tokenDate, now).toDaysPart();

            if (days >= 30) {
                // create new and return token
                return createToken();
            } else if (days == 29) {
                // refresh token
                return refreshToken(smsTokenEntity.getToken());
            } else {
                return smsTokenEntity.getToken();
            }
        }
        // if token not exists create new one
        return createToken();
    }

    private String createToken() {
        SmsProviderTokenDTO smsProviderTokenDTO = new SmsProviderTokenDTO();
        smsProviderTokenDTO.setEmail(email);
        smsProviderTokenDTO.setPassword(password);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        RequestEntity<SmsProviderTokenDTO> request = RequestEntity
                .post(url + "auth/login")
                .headers(headers)
                .body(smsProviderTokenDTO);

        var response = restTemplate.exchange(request, SmsTokenProviderResponse.class);
        String token = response.getBody().getData().getToken();

        SmsTokenEntity entity = new SmsTokenEntity();
        entity.setToken(token);
        entity.setCreatedDate(LocalDateTime.now());
        smsTokenRepository.save(entity);

        return token;
    }

    public String refreshToken(String oldToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + oldToken);
        headers.set("Content-Type", "application/json");

        RequestEntity<Void> request = RequestEntity
                .patch(url + "auth/refresh")
                .headers(headers)
                .build();

        var response = restTemplate.exchange(request, SmsTokenProviderResponse.class);
        String newToken = response.getBody().getData().getToken();

        SmsTokenEntity entity = new SmsTokenEntity();
        entity.setToken(newToken);
        entity.setCreatedDate(LocalDateTime.now());
        smsTokenRepository.save(entity);

        return newToken;
    }
}
