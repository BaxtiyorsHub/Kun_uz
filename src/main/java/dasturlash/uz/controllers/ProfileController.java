package dasturlash.uz.controllers;

import dasturlash.uz.enums.RolesEnum;
import dasturlash.uz.request.FilterRequestDTO;
import dasturlash.uz.request.ProfileRequestDTO;
import dasturlash.uz.responseDto.ProfileResponseDTO;
import dasturlash.uz.services.connectedServices.ProfileRoleService;
import dasturlash.uz.services.ProfileService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/profile")
@RestController
public class ProfileController {

    private final ProfileService profileService;
    private final ProfileRoleService profileRoleService;

    public ProfileController(ProfileRoleService profileRoleService, ProfileService profileService) {
        this.profileRoleService = profileRoleService;
        this.profileService = profileService;
    }

    @PostMapping("/create")
    public ResponseEntity<ProfileResponseDTO> create(@Valid @RequestBody ProfileRequestDTO profile) {
        return ResponseEntity.ok(profileService.create(profile));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(profileService.getById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProfileResponseDTO> update(@PathVariable Integer id,
                                                     @RequestBody ProfileRequestDTO profileDTO) {
        return ResponseEntity.ok(profileService.update(id, profileDTO));
    }

    @DeleteMapping("/delete/role/{id}")
    public ResponseEntity<Boolean> deleteRole(@PathVariable Integer id,
                                              @RequestBody List<RolesEnum> rolesEnumList) {
        return ResponseEntity.ok(profileRoleService.deleteRole(id,rolesEnumList));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(profileService.delete(id));
    }

    @PutMapping("/profilePhoto/{id}")
    public ResponseEntity<ProfileResponseDTO> updatePhoto(@PathVariable Integer id,
                                                          @RequestBody String photoId) {
        return ResponseEntity.ok(profileService.photoUpdate(id, photoId));
    }

    @GetMapping("/pagination")
    public ResponseEntity<Page<ProfileResponseDTO>> getPagination(
            @RequestParam Integer page,
            @RequestParam Integer size
    ) {
        return ResponseEntity.ok(profileService.pagination(page - 1, size));
    }

    @PostMapping("/pagination/filter")
    public ResponseEntity<Page<ProfileResponseDTO>> getPaginationFilter(
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestBody FilterRequestDTO dto
    ) {
        return ResponseEntity.ok(profileService.filter(page - 1, size, dto));
    }

}
