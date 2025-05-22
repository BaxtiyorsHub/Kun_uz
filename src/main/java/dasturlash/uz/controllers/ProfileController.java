package dasturlash.uz.controllers;

import dasturlash.uz.dto.ProfileDTO;
import dasturlash.uz.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/profile")
@RestController
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @PostMapping("")
    public ResponseEntity<ProfileDTO> create(
            @RequestBody ProfileDTO sectionDTO) {
        return ResponseEntity.ok(profileService.create(sectionDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileDTO> get(@PathVariable Integer id) {
        return ResponseEntity.ok(profileService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfileDTO> update(@PathVariable Integer id,
                                             @RequestBody ProfileDTO profileDTO) {
        return ResponseEntity.ok(profileService.update(id, profileDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(profileService.delete(id));
    }
}
