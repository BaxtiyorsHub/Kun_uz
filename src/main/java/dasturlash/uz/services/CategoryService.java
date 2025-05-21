package dasturlash.uz.services;

import dasturlash.uz.dto.CategoryDTO;
import dasturlash.uz.entities.CategoryEntity;
import dasturlash.uz.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        category.setVisible(true);

        categoryRepository.save(category);
        return toDTO(category);
    }

    public CategoryDTO getById(Integer id) {
        return null;
    }

    public CategoryDTO update(Integer id) {
        return null;
    }

    public Boolean delete(Integer id) {
        return null;
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
}
