package dasturlash.uz.services;

import dasturlash.uz.config.CustomUserDetails;
import dasturlash.uz.entities.ProfileEntity;
import dasturlash.uz.exp.AppBadExp;
import dasturlash.uz.repository.ProfileRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final ProfileRepository profileRepository;

    public CustomUserDetailsService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<ProfileEntity> profile = profileRepository.findByPhoneOrEmailAndVisibleIsTrue(username);
        if (profile.isPresent()) throw new AppBadExp("Username not found");

        ProfileEntity profileEntity = profile.get();
        if (profileEntity.getEmail().equals(username)) {
            return new CustomUserDetails(
                    profileEntity.getEmail(),
                    profileEntity.getPassword(),
                    profileEntity.getStatus());
        }

        return new CustomUserDetails(
                profileEntity.getPhone(),
                profileEntity.getPassword(),
                profileEntity.getStatus());

    }
}
