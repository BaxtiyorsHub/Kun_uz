package dasturlash.uz.config;

import dasturlash.uz.enums.RolesEnum;
import dasturlash.uz.enums.Status;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Getter
public class CustomUserDetails implements UserDetails {

    private final Integer id;
    private final String username;
    private final String password;
    private final Status status;
    private final List<SimpleGrantedAuthority> roleList;

    public CustomUserDetails(Integer id, String username, String password, Status status, List<RolesEnum> roleList) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.status = status;

        this.roleList = roleList.stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .toList();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roleList;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return status.equals(Status.ACTIVE);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
