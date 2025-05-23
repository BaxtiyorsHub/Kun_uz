package dasturlash.uz.controllers;

import dasturlash.uz.dto.SectionDTO;
import dasturlash.uz.responseDto.SectionResponseDTO;
import dasturlash.uz.enums.Lang;
import dasturlash.uz.services.SectionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/section")
@RestController
public class SectionController {

    private final SectionService sectionService;

    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @PostMapping("/create")
    public ResponseEntity<SectionDTO> create(@Valid
                                             @RequestBody SectionDTO sectionDTO) {
        return ResponseEntity.ok(sectionService.create(sectionDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SectionDTO> get(@PathVariable Integer id) {
        return ResponseEntity.ok(sectionService.getById(id));
    }

    @GetMapping("/list")
    public ResponseEntity<List<SectionDTO>> getList() {
        return ResponseEntity.ok(sectionService.getListAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SectionDTO> update(@PathVariable Integer id,
                                             @RequestBody SectionDTO sectionDTO) {
        return ResponseEntity.ok(sectionService.update(id, sectionDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(sectionService.delete(id));
    }

    @GetMapping("/byLang")
    public ResponseEntity<List<SectionResponseDTO>> getByLang(
            @RequestParam(defaultValue = "uz") Lang lang) {
        return ResponseEntity.ok(sectionService.getListLang(lang));
    }
}
