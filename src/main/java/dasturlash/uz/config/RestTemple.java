package dasturlash.uz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestTemple {

    @Bean
    public RestTemple restTemple() {
        return new RestTemple();
    }

}
