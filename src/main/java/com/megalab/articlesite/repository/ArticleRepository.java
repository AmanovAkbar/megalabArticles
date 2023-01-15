package com.megalab.articlesite.repository;

import com.megalab.articlesite.model.Article;
import com.megalab.articlesite.model.Category;
import com.megalab.articlesite.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;


public interface ArticleRepository extends JpaRepository<Article, Long> {

    Optional<Article>findByTitle(String title);

    Page<Article>findArticlesByCreator(User creator, Pageable pageable);


    Page<Article>findArticlesByCategoryId(Long category_id, Pageable pageable);
    @Query("SELECT a FROM Article a WHERE lower(a.title) LIKE lower(concat('%', :keyword,'%'))" +
            "OR lower(a.body) LIKE lower(concat('%', :keyword,'%'))" +
            "OR lower(a.description) LIKE lower(concat('%', :keyword,'%'))")
    Page<Article>searchArticles(String keyword, Pageable pageable);
    @Query("SELECT a FROM Article a WHERE (lower(a.title) LIKE lower(concat('%', :keyword,'%'))" +
            "OR lower(a.body) LIKE lower(concat('%', :keyword,'%'))" +
            "OR lower(a.description) LIKE lower(concat('%', :keyword,'%')))" +
            "AND (a.category.id = :categoryId)")
    Page<Article>searchArticlesByCategoryId(String keyword, Long categoryId,  Pageable pageable);


    Page<Article>findArticlesByFavoured_Id(Long userId, Pageable pageable);



}