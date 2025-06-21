package dasturlash.uz.controllers;

import dasturlash.uz.dto.JwtDTO;
import dasturlash.uz.util.JwtUtil;
import dasturlash.uz.request.auth.LoginRequestDTO;
import dasturlash.uz.request.auth.RegistrationDTO;
import dasturlash.uz.response.LoginResponseDTO;
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
    public ResponseEntity<String> registration(@Valid @RequestBody RegistrationDTO dto) {
        return ResponseEntity.ok(authService.registration(dto));
    }

    @GetMapping("/registration/verification/email/{token}")
    public ResponseEntity<String> verifyRegistration(@PathVariable("token") String token) {
        JwtDTO jwtDTO;
        try {
            jwtDTO = JwtUtil.decode(token);
            String email = jwtDTO.getUsername();
            String code = jwtDTO.getCode();

            return ResponseEntity.ok(authService.emailVerification(email, code));

        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token expiration date is expired");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping("/registration/phone")
    public ResponseEntity<String> phoneRegistration(@Valid @RequestBody RegistrationDTO dto) {
        return ResponseEntity.ok(authService.sendSmsToPhone(dto));
    }

    @GetMapping("/registration/verification/phone")
    public ResponseEntity<String> phoneVerification(@Valid @RequestBody RegistrationDTO dto) {
        return ResponseEntity.ok(authService.phoneVerification(dto.getUsername(), dto.getPassword()));
    }
}
