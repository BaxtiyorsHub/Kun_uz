package dasturlash.uz.config;

import dasturlash.uz.enums.RolesEnum;
import dasturlash.uz.jwtUtil.JwtAuthenticationFilter;
import dasturlash.uz.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtAuthenticationFilter jwtTokenFilter;

    public SpringSecurityConfig(CustomUserDetailsService customUserDetailsService,
                                BCryptPasswordEncoder bCryptPasswordEncoder,
                                JwtAuthenticationFilter jwtTokenFilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtTokenFilter = jwtTokenFilter;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        // authentication - Foydalanuvchining identifikatsiya qilish.
        // Ya'ni berilgan login va parolli user bor yoki yo'qligini aniqlash.
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(customUserDetailsService);
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                // --- AUTHORIZATION ---
                .authorizeHttpRequests(auth -> auth

                        // ATTACH
                        .requestMatchers("/api/v1/attach/delete/**",
                                "/api/v1/attach/pagination/**")
                        .hasRole(RolesEnum.ADMIN.name())
                        .requestMatchers("/api/v1/attach/**").permitAll()

                        // AUTH
                        .requestMatchers("/api/v1/auth/**",
                                "/api/v1/auth/login").permitAll()

                        // PROFILE
                        .requestMatchers("/api/v1/profile/profilePhoto/**",
                                "/api/v1/profile/update/**").permitAll()
                        .requestMatchers("/api/v1/profile/**")
                        .hasAnyRole(RolesEnum.ADMIN.name(),
                                RolesEnum.MODERATOR.name(),
                                RolesEnum.PUBLISHER.name())

                        // REGION
                        .requestMatchers("/api/v1/region/byLang").permitAll()
                        .requestMatchers("/api/v1/region/**")
                        .hasRole(RolesEnum.ADMIN.name())

                        // CATEGORY
                        .requestMatchers("/api/v1/category/byLang").permitAll()
                        .requestMatchers("/api/v1/category/**")
                        .hasRole(RolesEnum.ADMIN.name())

                        // SECTION
                        .requestMatchers("/api/v1/section/byLang").permitAll()
                        .requestMatchers("/api/v1/section/**")
                        .hasRole(RolesEnum.ADMIN.name())

                        // ARTICLE
                        .requestMatchers("/api/v1/article/status/**")
                        .hasRole(RolesEnum.PUBLISHER.name())
                        .requestMatchers("/api/v1/article/**")
                        .hasAnyRole(RolesEnum.ADMIN.name(),
                                RolesEnum.MODERATOR.name())

                        // OTHER
                        .anyRequest().authenticated()
                )
                // --- AUTHENTICATION STYLE ---
                .httpBasic(Customizer.withDefaults());
        // yoki .formLogin(Customizer.withDefaults());

        return http.build();
    }

    public static final String[] AUTH_WHITELIST = {
            "/profile/registration",
            "/profile/authorization",
    };
}
