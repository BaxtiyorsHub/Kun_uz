package dasturlash.uz.controllers;

import dasturlash.uz.dto.RegionDTO;
import dasturlash.uz.entities.RegionEntity;
import dasturlash.uz.services.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/region")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @PostMapping("")
    public ResponseEntity<RegionDTO> create(@RequestBody RegionDTO regionDTO) {
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

    @PutMapping("/{id}")
    public ResponseEntity<RegionDTO> update(@PathVariable Integer id,
                                            @RequestBody RegionDTO regionDTO) {
        return ResponseEntity.ok(regionService.update(regionDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(regionService.delete(id));
    }

    @GetMapping("/byLang")
    public ResponseEntity<List<RegionEntity>> getByLang(@RequestParam String lang){
        return ResponseEntity.ok(regionService.getListByLang(lang));
    }
}
