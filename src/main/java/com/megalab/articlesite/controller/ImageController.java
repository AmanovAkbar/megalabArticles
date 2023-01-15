package com.megalab.articlesite.controller;

import com.megalab.articlesite.repository.CommentaryRepository;
import com.megalab.articlesite.security.jwt.response.ResponseMessage;
import com.megalab.articlesite.service.PictureService;
import com.megalab.articlesite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ImageController {
    @Autowired
    UserService userService;
    @Autowired
    PictureService pictureService;
    @PostMapping("/user/pfp")
    public ResponseEntity<ResponseMessage> uploadPfp(@RequestParam MultipartFile multipartFile){
        return userService.saveProfilePicture(multipartFile);
    }
    @GetMapping("user/pfp/{id}")
    public ResponseEntity<byte[]> getPfp(@PathVariable long id){
        try{
            return userService.getProfilePicture(id);
        } catch (RuntimeException e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @DeleteMapping("user/pfp")
    public ResponseEntity<ResponseMessage>deletePfp(){
        return userService.deleteProfilePicture();
    }
    @PostMapping("/admin/pfp")
    public ResponseEntity<ResponseMessage> uploadPfpAdmin(@RequestParam MultipartFile multipartFile){
        return userService.saveProfilePicture(multipartFile);
    }
    @GetMapping("admin/pfp/{id}")
    public ResponseEntity<byte[]> getPfpAdmin(@PathVariable long id){
        try{
            return userService.getProfilePicture(id);
        } catch (RuntimeException e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("admin/pfp")
    public ResponseEntity<ResponseMessage>deletePfpAdmin(){
        return userService.deleteProfilePicture();
    }

    @DeleteMapping("admin/deletepfp/{id}")
    public ResponseEntity<ResponseMessage>deletePfpAdmin(@PathVariable long id){
        return userService.deleteProfilePicture(id);
    }

    @GetMapping("images/{id}")
    public ResponseEntity<byte[]>getPicture(@PathVariable long id){
        return pictureService.getPictureById(id);
    }

}
