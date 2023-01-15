package com.megalab.articlesite.repository;

import com.megalab.articlesite.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category>findByName(String name);
    Boolean existsByName(String name);
    void deleteCategoryById(Long id);

    void deleteCategoryByName(String name);
}