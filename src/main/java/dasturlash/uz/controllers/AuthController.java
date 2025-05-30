package dasturlash.uz.controllers;

import dasturlash.uz.dto.auth.RegistrationDTO;
import dasturlash.uz.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
