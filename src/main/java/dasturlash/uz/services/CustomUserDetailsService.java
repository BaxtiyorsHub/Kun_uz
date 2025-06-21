package dasturlash.uz.services;

import dasturlash.uz.config.CustomUserDetails;
import dasturlash.uz.entities.ProfileEntity;
import dasturlash.uz.enums.RolesEnum;
import dasturlash.uz.exceptions.AppBadExp;
import dasturlash.uz.repository.ProfileRepository;
import dasturlash.uz.repository.ProfileRolesRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final ProfileRepository profileRepository;
    private final ProfileRolesRepository profileRolesRepository;

    public CustomUserDetailsService(ProfileRepository profileRepository, ProfileRolesRepository profileRolesRepository) {
        this.profileRepository = profileRepository;
        this.profileRolesRepository = profileRolesRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<ProfileEntity> profile = profileRepository.findByPhoneOrEmailAndVisibleIsTrue(username);
        if (profile.isPresent()) throw new AppBadExp("Username not found");

        ProfileEntity profileEntity = profile.get();
        List<RolesEnum> roleList = profileRolesRepository.findProfileRole(profileEntity.getId());
        if (profileEntity.getEmail().equals(username)) {
            return new CustomUserDetails(
                    profileEntity.getId(),
                    profileEntity.getEmail(),
                    profileEntity.getPassword(),
                    profileEntity.getStatus(),
                    roleList);
        }

        return new CustomUserDetails(
                profileEntity.getId(),
                profileEntity.getPhone(),
                profileEntity.getPassword(),
                profileEntity.getStatus(),
                roleList);

    }
}
