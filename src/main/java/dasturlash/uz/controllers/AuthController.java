package dasturlash.uz.controllers;

import dasturlash.uz.request.auth.RegistrationDTO;
import dasturlash.uz.services.AuthService;
import jakarta.validation.Valid;
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

    @GetMapping("/registration/verification/{username}/{code}")
    public ResponseEntity<String> registrationVerification(@PathVariable("username") String username,
                                                           @PathVariable("code") String code) {
        return ResponseEntity.ok(authService.regEmailVerification(username,code));
    }

}
