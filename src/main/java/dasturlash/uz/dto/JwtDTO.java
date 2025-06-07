package dasturlash.uz.dto;

import lombok.Getter;

@Getter
public class JwtDTO {

    private String username;
    private String code;

    public JwtDTO(String username, String code) {
        this.username = username;
        this.code = code;
    }
}
