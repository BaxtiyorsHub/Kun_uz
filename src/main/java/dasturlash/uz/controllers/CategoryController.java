package dasturlash.uz.controllers;

import dasturlash.uz.dto.CategoryDTO;
import dasturlash.uz.entities.CategoryEntity;
import dasturlash.uz.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/list")
    public ResponseEntity<List<CategoryEntity>> getList() {
        return ResponseEntity.ok(categoryService.getListAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> update(@RequestBody CategoryDTO categoryDTO){
        return ResponseEntity.ok(categoryService.update(categoryDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Integer id){
        return ResponseEntity.ok(categoryService.delete(id));
    }
}
