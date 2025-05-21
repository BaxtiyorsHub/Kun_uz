package dasturlash.uz.controllers;

import dasturlash.uz.dto.CategoryDTO;
import dasturlash.uz.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("")
    public ResponseEntity<CategoryDTO> create(
            @RequestBody CategoryDTO categoryDTO){
        return ResponseEntity.ok(categoryService.create(categoryDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> get(@PathVariable Integer id){
        return ResponseEntity.ok(categoryService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> update(@PathVariable Integer id){
        return ResponseEntity.ok(categoryService.update(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Integer id){
        return ResponseEntity.ok(categoryService.delete(id));
    }

}
