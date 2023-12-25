package com.relatablecode.ecommerce.controller;

import com.relatablecode.ecommerce.dto.CategoryDTO;
import com.relatablecode.ecommerce.model.Category;
import com.relatablecode.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<CategoryDTO> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id)
                .map(ResponseEntity::ok) // If the product is found, wrap it in a ResponseEntity with an OK status
                .orElseGet(() -> ResponseEntity.notFound().build()); // If not found, return a Not Found response
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {
        CategoryDTO createdCategory = categoryService.saveCategory(categoryDTO);
        return ResponseEntity.ok(createdCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
        return categoryService.getCategoryById(id)
                .map(existingCategory -> {
                    categoryDTO.setId(id); // Ensure the ID is set
                    if (categoryDTO.getProducts() == null || categoryDTO.getProducts().isEmpty()) {
                        categoryDTO.setProducts(existingCategory.getProducts());
                    }
                    CategoryDTO updatedCategory = categoryService.saveCategory(categoryDTO);
                    return ResponseEntity.ok(updatedCategory);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        if (categoryService.getCategoryById(id).isPresent()) {
            categoryService.deleteCategory(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
