package com.megalab.articlesite.controller;

import com.megalab.articlesite.security.jwt.response.ResponseMessage;
import com.megalab.articlesite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.lang.annotation.Repeatable;

@RestController
public class MainController {
    @Autowired
    UserService userService;

    @PostMapping("/user/pfp")
    public ResponseEntity<ResponseMessage> uploadPfp(@RequestParam MultipartFile multipartFile){
        return userService.saveProfilePicture(multipartFile);
    }
    @GetMapping("user/pfp/{id}")
    public ResponseEntity<byte[]> getPfp(@PathVariable long id){
        return userService.getProfilePicture(id);
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
        return userService.getProfilePicture(id);
    }

    @DeleteMapping("admin/pfp")
    public ResponseEntity<ResponseMessage>deletePfpAdmin(){
        return userService.deleteProfilePicture();
    }

}
