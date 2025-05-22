package dasturlash.uz.services;

import dasturlash.uz.dto.CategoryDTO;
import dasturlash.uz.entities.CategoryEntity;
import dasturlash.uz.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryDTO create(CategoryDTO categoryDTO) {
        CategoryEntity category = new CategoryEntity();
        category.setNameUz(categoryDTO.getNameUz());
        category.setNameRu(categoryDTO.getNameRu());
        category.setNameEn(categoryDTO.getNameEn());
        category.setKey(categoryDTO.getKey());

        categoryRepository.save(category);
        return toDTO(category);
    }

    public CategoryDTO getById(Integer id) {
        CategoryEntity category = categoryRepository.findByIdAndVisibleIsTrue(id);
        return category != null ? toDTO(category) : null;
    }

    public CategoryDTO update(Integer id, CategoryDTO categoryDTO) {
        CategoryEntity category = categoryRepository.findByIdAndVisibleIsTrue(id);
        if (category == null) {
            throw new EntityNotFoundException("Region not found or not visible with id: " + categoryDTO.getId());
        }

        if (categoryDTO.getNameUz() != null) category.setNameUz(categoryDTO.getNameUz());
        if (categoryDTO.getNameRu() != null) category.setNameRu(categoryDTO.getNameRu());
        if (categoryDTO.getNameEn() != null) category.setNameEn(categoryDTO.getNameEn());
        if (categoryDTO.getKey() != null) category.setKey(categoryDTO.getKey());

        categoryRepository.save(category);
        return toDTO(category);
    }

    public Boolean delete(Integer id) {
        CategoryEntity category = categoryRepository.findByIdAndVisibleIsTrue(id);
        if (category == null) {
            throw new EntityNotFoundException("Region not found or already deleted");
        }

        category.setVisible(false);
        categoryRepository.save(category);
        return true;
    }

    private CategoryDTO toDTO(CategoryEntity category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setNameUz(category.getNameUz());
        dto.setNameRu(category.getNameRu());
        dto.setNameEn(category.getNameEn());
        dto.setKey(category.getKey());
        dto.setCreatedDate(category.getCreatedDate());
        return dto;
    }

    public List<CategoryEntity> getListAll() {
        List<CategoryEntity> allCategory = new LinkedList<>();
        categoryRepository.findAll().forEach(allCategory::add);
        return allCategory;
    }
}
