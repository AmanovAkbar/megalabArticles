package com.megalab.articlesite.controller;

import com.megalab.articlesite.model.Article;
import com.megalab.articlesite.model.User;
import com.megalab.articlesite.request.RequestArticle;
import com.megalab.articlesite.request.RequestCommentary;
import com.megalab.articlesite.response.ResponseArticle;
import com.megalab.articlesite.security.jwt.response.ResponseMessage;
import com.megalab.articlesite.security.service.UserDetailsImpl;
import com.megalab.articlesite.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.NoSuchElementException;

@RestController
public class ArticleController {
    @Autowired
    ArticleService articleService;
    @GetMapping("/main")
    public ResponseEntity<Map<String, Object>> getArticlesForMainPage(@RequestParam(defaultValue = "") String query, @RequestParam(defaultValue = "0") int page,
                                                                      @RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "") String categoryName){
       return articleService.getArticlesForMainPage(query, page, size, categoryName);
    }
    @PostMapping("/article")
    public ResponseEntity<ResponseMessage> saveArticle(@ModelAttribute RequestArticle requestArticle){
        try{
            articleService.saveArticle(requestArticle);
            String message = "Article was successfully saved!";
            return ResponseEntity.ok(new ResponseMessage(message));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(e.getMessage()));
        }
    }
    @PutMapping("/article/{id}")
    public ResponseEntity<ResponseMessage> editArticle(@ModelAttribute RequestArticle requestArticle, @PathVariable long id){
        try{
            articleService.editArticle(id, requestArticle);
            String message = "Article was successfully edited!";
            return ResponseEntity.ok(new ResponseMessage(message));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(e.getMessage()));
        }
    }

    @DeleteMapping("/article/{id}")
    public ResponseEntity<ResponseMessage> deleteArticle(@PathVariable long id){
        try{
            articleService.deleteArticle(id);
            String message = "Article was successfully deleted!";
            return ResponseEntity.ok(new ResponseMessage(message));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(e.getMessage()));
        }
    }
    @GetMapping("/article/{id}")
    public ResponseEntity<ResponseArticle>getArticle(@PathVariable long id){
        try{
            return articleService.getArticle(id);
        }catch (Exception e){
            return  ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
        }
    }
    @GetMapping("/user/{user_id}/favourite")
    public ResponseEntity<Map<String, Object>> getFavouriteArticle(@RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "5") int size,
                                                                   @PathVariable long user_id){
        return articleService.getFavouriteArticle(page, size, user_id);
    }
    @GetMapping("/user/{user_id}/created")
    public ResponseEntity<Map<String, Object>> getArticlesByCreator(@RequestParam(defaultValue = "0")int page,
                                                                    @RequestParam(defaultValue = "5")int size,
                                                                    @PathVariable long user_id){
        return articleService.getArticlesByCreator(page, size, user_id);
    }
    @PutMapping("article/{id}/favourite")
    public ResponseEntity<ResponseMessage>addToFavourites(@PathVariable long id){
       return articleService.addToFavourites(id);
    }

}
