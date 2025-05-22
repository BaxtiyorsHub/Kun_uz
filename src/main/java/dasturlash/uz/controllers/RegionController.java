package dasturlash.uz.controllers;

import dasturlash.uz.dto.RegionDTO;
import dasturlash.uz.dto.RegionResponseDTO;
import dasturlash.uz.entities.RegionEntity;
import dasturlash.uz.enums.Lang;
import dasturlash.uz.services.RegionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/region")
@RestController
public class RegionController {

    private final RegionService regionService;

    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    @PostMapping("/create")
    public ResponseEntity<RegionDTO> create(@Valid
            @RequestBody RegionDTO regionDTO) {
        return ResponseEntity.ok(regionService.create(regionDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegionDTO> get(@PathVariable Integer id) {
        return ResponseEntity.ok(regionService.getById(id));
    }

    @GetMapping("/list")
    public ResponseEntity<List<RegionEntity>> getList() {
        return ResponseEntity.ok(regionService.getListAll());
    }

    @PutMapping("")
    public ResponseEntity<RegionDTO> update(@PathVariable Integer id,
            @RequestBody RegionDTO regionDTO) {
        return ResponseEntity.ok(regionService.update(id, regionDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(regionService.delete(id));
    }

    @GetMapping("/byLang")
    public ResponseEntity<List<RegionResponseDTO>> getByLang(@RequestParam(defaultValue = "uz") Lang lang){
        return ResponseEntity.ok(regionService.getListByLang(lang));
    }
}
