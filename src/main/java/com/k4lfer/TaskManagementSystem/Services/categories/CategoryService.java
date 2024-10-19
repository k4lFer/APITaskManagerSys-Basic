package com.k4lfer.TaskManagementSystem.Services.categories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.k4lfer.TaskManagementSystem.Dto.Objects.DtoCategory;
import com.k4lfer.TaskManagementSystem.models.Category;
import com.k4lfer.TaskManagementSystem.repositories.CategoryRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<DtoCategory> getAll(){
        List<Category> categories = categoryRepository.findAll();
        List<DtoCategory> dtoCategories = new ArrayList<>();

        for(Category category : categories){
            DtoCategory dtoCategory = new DtoCategory();
            dtoCategory.setId(category.getId());
            dtoCategory.setName(category.getName());
            dtoCategory.setCreatedAt(category.getCreatedAt());
            dtoCategory.setUpdatedAt(category.getUpdatedAt());      
            dtoCategories.add(dtoCategory);
        }
        return dtoCategories;
    } 
}
