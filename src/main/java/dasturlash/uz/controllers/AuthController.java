package dasturlash.uz.controllers;

import dasturlash.uz.dto.JwtDTO;
import dasturlash.uz.jwtUtil.JwtUtil;
import dasturlash.uz.request.LoginDTO;
import dasturlash.uz.request.auth.RegistrationDTO;
import dasturlash.uz.responseDto.LoginResponseDTO;
import dasturlash.uz.services.AuthService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/registration")
    public ResponseEntity<String> registration(@Valid @RequestBody RegistrationDTO dto) throws InterruptedException {
        return ResponseEntity.ok(authService.registration(dto));
    }

    @GetMapping("/registration/verification/{token}")
    public ResponseEntity<String> verifyRegistration(@PathVariable("token") String token) {
        JwtDTO jwtDTO = null;
        try {
            jwtDTO = JwtUtil.decode(token);
            String username = jwtDTO.getUsername();
            String code = jwtDTO.getCode();

            return ResponseEntity.ok(authService.regEmailVerification(username, code));

        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token muddati tugagan.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token noto‘g‘ri.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginDTO dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping("/phoneRegistration")
    public ResponseEntity<String> phoneRegistration(@Valid @RequestBody RegistrationDTO dto) {
        return ResponseEntity.ok(authService.sendSmsToPhone(dto));
    }
}
