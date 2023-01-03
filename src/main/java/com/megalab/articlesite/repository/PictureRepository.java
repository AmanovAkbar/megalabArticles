package com.megalab.articlesite.repository;

import com.megalab.articlesite.model.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PictureRepository extends JpaRepository<Picture, Long> {
    Optional<Picture>findByName(String name);
}