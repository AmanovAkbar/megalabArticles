package com.megalab.articlesite.service;

import com.megalab.articlesite.model.Category;
import com.megalab.articlesite.repository.CategoryRepository;
import com.megalab.articlesite.security.jwt.response.ResponseMessage;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    public ResponseEntity<ResponseMessage> addCategory(String categoryName){
        Category category = new Category(categoryName);
        if(categoryRepository.existsByName(categoryName)){
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseMessage("There is already a category with this name!"));
        }else{
            categoryRepository.save(category);
            return ResponseEntity.ok(new ResponseMessage("Category was saved"));
        }
    }

    @Transactional
    public ResponseEntity<ResponseMessage> deleteCategory(String categoryName){

        if(categoryRepository.existsByName(categoryName)){

            categoryRepository.deleteCategoryByName(categoryName);
            return ResponseEntity.ok(new ResponseMessage("Category was deleted"));
        }else{
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseMessage("There is no category with this name!"));

        }
    }
}
