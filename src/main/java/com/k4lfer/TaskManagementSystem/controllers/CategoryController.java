package com.k4lfer.TaskManagementSystem.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.k4lfer.TaskManagementSystem.Dto.Objects.DtoCategory;
import com.k4lfer.TaskManagementSystem.Services.categories.CategoryService;

import lombok.AllArgsConstructor;


@RestController
@RequestMapping("/category")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/")
    public ResponseEntity<List<DtoCategory>> getAll() {
        return ResponseEntity.ok(categoryService.getAll());
    }
    
}
