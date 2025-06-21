package dasturlash.uz.services.connectedServices;

import dasturlash.uz.entities.ProfileEntity;
import dasturlash.uz.entities.ProfileRolesEntity;
import dasturlash.uz.enums.RolesEnum;
import dasturlash.uz.exp.AppBadExp;
import dasturlash.uz.repository.ProfileRolesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileRoleService {

    private final ProfileRolesRepository profileRolesRepository;

    public ProfileRoleService(ProfileRolesRepository profileRolesRepository) {
        this.profileRolesRepository = profileRolesRepository;
    }

    /**
     * @param profile
     * @param rolesEnumList
     * @return true or false create and update method
     */
    public Boolean createAndUpdate(ProfileEntity profile, List<RolesEnum> rolesEnumList) {
        List<RolesEnum> profileRoles = profileRolesRepository.findProfileRole(profile.getId());
        List<RolesEnum> needToAddRole = List.of();
        if (profileRoles != null) {
            // kirib kelgan roles bilan bazada mavjud roleslarni check qiladi duplicatelarni o'chiradi
            needToAddRole = rolesCheck(rolesEnumList, profileRoles);
        }

        for (RolesEnum roleEnum : needToAddRole) {
            ProfileRolesEntity rolesEntity = new ProfileRolesEntity();
            rolesEntity.setProfileId(profile.getId());
            rolesEntity.setProfile(profile);
            rolesEntity.setRole(roleEnum);
            profileRolesRepository.save(rolesEntity);
        }
        return true;
    }

    private List<RolesEnum> rolesCheck(List<RolesEnum> newRoles, List<RolesEnum> baseRoles) {
        return newRoles.stream()
                .filter(roles -> !baseRoles.contains(roles))
                .toList();
    }

    public Boolean deleteRole(Integer id, List<RolesEnum> rolesEnumList) {
        List<RolesEnum> profileRoles = profileRolesRepository.findProfileRole(id);
        if (profileRoles == null) throw new AppBadExp("Profile not found");

        for (RolesEnum roleEnum : rolesEnumList) {
            if (profileRoles.contains(roleEnum)) {
                profileRolesRepository.deleteProfileRole(id, roleEnum);
            }
        }
        return true;
    }

    public void deleteRolesByProfileId(Integer profileId) {
        profileRolesRepository.deleteById(profileId);
    }
}
