package dasturlash.uz.dto;

import lombok.Getter;

@Getter
public class JwtDTO {

    private String username;
    private String role;

    public JwtDTO(String username, String role) {
        this.username = username;
        this.role = role;
    }
}
