package com.megalab.articlesite.controller;

import com.megalab.articlesite.model.Commentary;
import com.megalab.articlesite.request.RequestCommentary;
import com.megalab.articlesite.response.ResponseCommentary;
import com.megalab.articlesite.security.jwt.response.ResponseMessage;
import com.megalab.articlesite.service.CommentaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentaryController {
    @Autowired
    CommentaryService commentaryService;

    @GetMapping("/article/{article_id}/commentary")
    public ResponseEntity<List<ResponseCommentary>>getArticleCommentaries(@PathVariable long article_id){
        try {
            return ResponseEntity.ok(commentaryService.getArticleCommentaries(article_id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
        }
    }
    @PostMapping("/commentary")
    public ResponseEntity<ResponseMessage>saveCommentary(@RequestBody RequestCommentary requestCommentary){
        try{
            commentaryService.saveCommentary(requestCommentary);
            String message = "Commentary was successfully added!";
            return ResponseEntity.ok(new ResponseMessage(message));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(e.getMessage()));
        }
    }
    @PutMapping("/commentary/{id}")
    public ResponseEntity<ResponseMessage>editCommentary(@RequestBody RequestCommentary requestCommentary, @PathVariable long id){
        try{

            commentaryService.editCommentary(id, requestCommentary.getBody());
            String message = "Commentary was successfully edited!";
            return ResponseEntity.ok(new ResponseMessage(message));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(e.getMessage()));
        }
    }
    @DeleteMapping("/commentary/{id}")
    public ResponseEntity<ResponseMessage>deleteCommentary( @PathVariable long id){
        try{
            commentaryService.deleteCommentary(id);
            String message = "Commentary was successfully deleted!";
            return ResponseEntity.ok(new ResponseMessage(message));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(e.getMessage()));
        }
    }

}
