package com.megalab.articlesite.controller;

import com.megalab.articlesite.model.Category;
import com.megalab.articlesite.repository.CategoryRepository;
import com.megalab.articlesite.security.jwt.response.ResponseMessage;
import com.megalab.articlesite.service.CategoryService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    @PostMapping("/add")
    public ResponseEntity<ResponseMessage> addCategory(@RequestParam String categoryName){
       return categoryService.addCategory(categoryName);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseMessage> deleteCategory(@RequestParam String categoryName){
       return categoryService.deleteCategory(categoryName);
    }
}
