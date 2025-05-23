package dasturlash.uz.controllers;

import dasturlash.uz.dto.CategoryDTO;
import dasturlash.uz.responseDto.CategoryResponseDTO;
import dasturlash.uz.entities.CategoryEntity;
import dasturlash.uz.enums.Lang;
import dasturlash.uz.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/category")
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/create")
    public ResponseEntity<CategoryDTO> create(@Valid
            @RequestBody CategoryDTO categoryDTO){
        return ResponseEntity.ok(categoryService.create(categoryDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> get(@PathVariable Integer id){
        return ResponseEntity.ok(categoryService.getById(id));
    }

    @GetMapping("/list")
    public ResponseEntity<List<CategoryEntity>> getList() {
        return ResponseEntity.ok(categoryService.getListAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> update(@PathVariable Integer id,
            @RequestBody CategoryDTO categoryDTO){
        return ResponseEntity.ok(categoryService.update(id,categoryDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Integer id){
        return ResponseEntity.ok(categoryService.delete(id));
    }

    @GetMapping("/byLang")
    public ResponseEntity<List<CategoryResponseDTO>> getByLang(@RequestParam Lang lang){
        return ResponseEntity.ok(categoryService.getListByLang(lang));
    }
}
