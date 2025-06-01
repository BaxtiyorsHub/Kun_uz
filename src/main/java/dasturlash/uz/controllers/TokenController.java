package dasturlash.uz.controllers;

import dasturlash.uz.dto.JwtDTO;
import dasturlash.uz.jwtUtil.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/token")
public class TokenController {

    @GetMapping({"/generate"})
    public ResponseEntity<String> generateToken() {
        return ResponseEntity.ok(JwtUtil.encode( "mazgiyev", "ADMIN"));
    }

    @GetMapping({"/parse/{token}"})
    public ResponseEntity<JwtDTO> parseToken(@PathVariable String token) {
        return ResponseEntity.ok(JwtUtil.decode(token));
    }
}
