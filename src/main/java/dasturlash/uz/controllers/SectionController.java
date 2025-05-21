package dasturlash.uz.controllers;

import dasturlash.uz.dto.SectionDTO;
import dasturlash.uz.services.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/section")
public class SectionController {

    @Autowired
    private SectionService sectionService;

    @PostMapping("")
    public ResponseEntity<SectionDTO> create(
            @RequestBody SectionDTO sectionDTO){
        return ResponseEntity.ok(sectionService.create(sectionDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SectionDTO> get(@PathVariable Integer id){
        return ResponseEntity.ok(sectionService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SectionDTO> update(@PathVariable Integer id){
        return ResponseEntity.ok(sectionService.update(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Integer id){
        return ResponseEntity.ok(sectionService.delete(id));
    }
}
