package com.megalab.articlesite.controller;

import com.megalab.articlesite.model.Category;
import com.megalab.articlesite.repository.CategoryRepository;
import com.megalab.articlesite.security.jwt.response.ResponseMessage;
import com.megalab.articlesite.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    @Operation(summary = "Add new category (Admin only)")
    @PostMapping("/add")
    public ResponseEntity<ResponseMessage> addCategory(@RequestParam String categoryName){
       return categoryService.addCategory(categoryName);
    }
    @Operation(summary = "Delete existing category (Admin only)")
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseMessage> deleteCategory(@RequestParam String categoryName){
       return categoryService.deleteCategory(categoryName);
    }
}
