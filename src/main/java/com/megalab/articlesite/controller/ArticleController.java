package com.megalab.articlesite.controller;

import com.megalab.articlesite.model.Article;
import com.megalab.articlesite.model.User;
import com.megalab.articlesite.request.RequestArticle;
import com.megalab.articlesite.request.RequestCommentary;
import com.megalab.articlesite.response.ResponseArticle;
import com.megalab.articlesite.security.jwt.response.ResponseMessage;
import com.megalab.articlesite.security.service.UserDetailsImpl;
import com.megalab.articlesite.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
public class ArticleController {
    @Autowired
    ArticleService articleService;
    @Operation(summary = "Get articles for main page representation with pagination and search")
    @GetMapping("/main")
    public ResponseEntity<Map<String, Object>> getArticlesForMainPage(@RequestParam(defaultValue = "") String query, @RequestParam(defaultValue = "0") int page,
                                                                      @RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "") String categoryName){
       return articleService.getArticlesForMainPage(query, page, size, categoryName);
    }
    @Operation(summary = "Save new Article")
    @PostMapping("/user/article")
    public ResponseEntity<ResponseMessage> saveArticle(@ModelAttribute RequestArticle requestArticle){
        try{
            articleService.saveArticle(requestArticle);
            String message = "Article was successfully saved!";
            return ResponseEntity.ok(new ResponseMessage(message));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(e.getMessage()));
        }
    }
    @Operation(summary = "Edit existing article")
    @PutMapping("/user/article/{id}")
    public ResponseEntity<ResponseMessage> editArticle(@ModelAttribute RequestArticle requestArticle, @PathVariable long id){
        try{
            articleService.editArticle(id, requestArticle);
            String message = "Article was successfully edited!";
            return ResponseEntity.ok(new ResponseMessage(message));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(e.getMessage()));
        }
    }
    @Operation(summary = "Delete existing article by id")
    @DeleteMapping("/user/article/{id}")
    public ResponseEntity<ResponseMessage> deleteArticle(@PathVariable long id){
        try{
            articleService.deleteArticle(id);
            String message = "Article was successfully deleted!";
            return ResponseEntity.ok(new ResponseMessage(message));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(e.getMessage()));
        }
    }
    @Operation(summary = "Get Article by id")
    @GetMapping("/article/{id}")
    public ResponseEntity<ResponseArticle>getArticle(@PathVariable long id){
        try{
            Article article = articleService.getArticle(id);
            String pictureUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/images/").path(String.valueOf(article.getPicture().getId())).toUriString();
            long creatorId = article.getCreator().getId();
            String categoryName = article.getCategory().getName();
            return ResponseEntity.ok(new ResponseArticle(id, article.getTitle(), pictureUri,
                    article.getDescription(), article.getBody(), article.getCreatedAt(), creatorId, categoryName));
        }catch (Exception e){
            return  ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
        }
    }
    @Operation(summary = "Get list of user's favourite articles with pagination")
    @GetMapping("/user/{user_id}/favourite")
    public ResponseEntity<Map<String, Object>> getFavouriteArticle(@RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "5") int size,
                                                                   @PathVariable long user_id){
        return articleService.getFavouriteArticle(page, size, user_id);
    }
    @Operation(summary = "Get list of articles created by user")
    @GetMapping("/user/{user_id}/created")
    public ResponseEntity<Map<String, Object>> getArticlesByCreator(@RequestParam(defaultValue = "0")int page,
                                                                    @RequestParam(defaultValue = "5")int size,
                                                                    @PathVariable long user_id){
        return articleService.getArticlesByCreator(page, size, user_id);
    }
    @Operation(summary = "Add article to user's favourite")
    @PutMapping("/user/article/{id}/favourite")
    public ResponseEntity<ResponseMessage>addToFavourites(@PathVariable long id){
       return articleService.addToFavourites(id);
    }

}
