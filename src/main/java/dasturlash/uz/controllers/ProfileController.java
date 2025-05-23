package dasturlash.uz.controllers;

import dasturlash.uz.responseDto.ProfileInfoDTO;
import dasturlash.uz.dto.ProfileRequestDTO;
import dasturlash.uz.services.ProfileService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/profile")
@RestController
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("/create")
    public ResponseEntity<ProfileInfoDTO> create(@Valid
                                                    @RequestBody ProfileRequestDTO profile
    ) {
        return ResponseEntity.ok(profileService.create(profile));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileInfoDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(profileService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfileInfoDTO> update(@PathVariable Integer id,
                                             @RequestBody ProfileRequestDTO profileDTO) {
        return ResponseEntity.ok(profileService.update(id, profileDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(profileService.delete(id));
    }


}
