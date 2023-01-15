package com.megalab.articlesite.repository;

import com.megalab.articlesite.model.Article;
import com.megalab.articlesite.model.Commentary;
import com.megalab.articlesite.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentaryRepository extends JpaRepository<Commentary, Long> {
    List<Commentary>findCommentariesByCreator(User user);
    List<Commentary>findCommentariesByArticleAndRepliedIsNull(Article article);
    List<Commentary>findCommentariesByReplied(Commentary commentary);
    List<Commentary>findCommentariesByRepliedIsNull();

    //Page<Commentary> findCommentariesByCreator(User user, Pageable pageable);
    //Page<Commentary>findCommentariesByArticle(Article article, Pageable pageable);
    //Page<Commentary>findCommentariesByReplied(Commentary commentary, Pageable pageable);
}