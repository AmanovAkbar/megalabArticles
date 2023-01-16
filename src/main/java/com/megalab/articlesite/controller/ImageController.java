package com.megalab.articlesite.controller;

import com.megalab.articlesite.repository.CommentaryRepository;
import com.megalab.articlesite.security.jwt.response.ResponseMessage;
import com.megalab.articlesite.service.PictureService;
import com.megalab.articlesite.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class ImageController {
    @Autowired
    UserService userService;
    @Autowired
    PictureService pictureService;
    @Operation(summary = "Upload User's profile picture")
    @PostMapping("/user/pfp")
    public ResponseEntity<ResponseMessage> uploadPfp(@RequestParam MultipartFile multipartFile){
        return userService.saveProfilePicture(multipartFile);
    }
    @Operation(summary = "Get profile picture of a user")
    @GetMapping("/pfp/{id}")
    public ResponseEntity<byte[]> getPfp(@PathVariable long id){
        try{
            return userService.getProfilePicture(id);
        } catch (RuntimeException e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @Operation(summary = "Delete profile picture")
    @DeleteMapping("/user/pfp")
    public ResponseEntity<ResponseMessage>deletePfp(){
        return userService.deleteProfilePicture();
    }
    @Operation(summary = "Upload profile picture for Admin")
    @PostMapping("/admin/pfp")
    public ResponseEntity<ResponseMessage> uploadPfpAdmin(@RequestParam MultipartFile multipartFile){
        return userService.saveProfilePicture(multipartFile);
    }
    @Operation(summary = "Get any profile picture by id for admin")
    @GetMapping("/admin/pfp/{id}")
    public ResponseEntity<byte[]> getPfpAdmin(@PathVariable long id){
        try{
            return userService.getProfilePicture(id);
        } catch (RuntimeException e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Operation(summary = "Delete admin's profile picture")
    @DeleteMapping("/admin/pfp")
    public ResponseEntity<ResponseMessage>deletePfpAdmin(){
        return userService.deleteProfilePicture();
    }

    @Operation(summary = "Delete any profile picture by admin.")
    @DeleteMapping("/admin/deletepfp/{id}")
    public ResponseEntity<ResponseMessage>deletePfpAdmin(@PathVariable long id){
        return userService.deleteProfilePicture(id);
    }
    @Operation(summary = "Get any image by Id. ")
    @GetMapping("/images/{id}")
    public ResponseEntity<byte[]>getPicture(@PathVariable long id){
        return pictureService.getPictureById(id);
    }

}
