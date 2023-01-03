package com.megalab.articlesite.service;

import com.megalab.articlesite.model.Picture;
import com.megalab.articlesite.model.User;
import com.megalab.articlesite.repository.UserRepository;
import com.megalab.articlesite.security.jwt.response.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserService {
    @Autowired
    PictureService pictureService;
    @Autowired
    UserRepository userRepository;
    public ResponseEntity<ResponseMessage> saveProfilePicture(MultipartFile multipartFile){

        Picture picture = pictureService.savePicture(multipartFile);
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getCredentials();

        User user = userRepository.findUserByUsername(userDetails.getUsername()).get();
        user.setPicture(picture);
        return ResponseEntity.ok().body(new ResponseMessage("Successfully uploaded!"));
    }

    public ResponseEntity<byte[]>getProfilePicture(long userId){
        User user = userRepository.findUserById(userId).orElseThrow(()-> new RuntimeException("User not found!"));
        Picture picture = user.getPicture();
        if(picture==null){
            return ResponseEntity.notFound().build();
        }
        return pictureService.getPicture(picture);
    }
    public ResponseEntity<ResponseMessage>deleteProfilePicture(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        User user = userRepository.findUserByUsername(userDetails.getUsername()).get();
        long pictureId = user.getPicture().getId();
        return pictureService.deletePicture(pictureId);
    }
}
