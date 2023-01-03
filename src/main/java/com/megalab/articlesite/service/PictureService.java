package com.megalab.articlesite.service;

import com.megalab.articlesite.model.Picture;
import com.megalab.articlesite.repository.PictureRepository;
import com.megalab.articlesite.security.jwt.response.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@Service
public class PictureService {
    @Autowired
    PictureRepository pictureRepository;

    public Picture savePicture(MultipartFile multipartFile){
        if(!multipartFile.getContentType().startsWith("image/")){
            throw new IllegalArgumentException("This is not an image!");

        }
        try{
            Picture picture = new Picture();
            picture.setName(multipartFile.getOriginalFilename());
            picture.setData(multipartFile.getBytes());
            picture.setContentType(multipartFile.getContentType());

            return pictureRepository.save(picture);
        } catch (IOException e) {
            throw new RuntimeException("There was a mistake reading contents of an image.");
        }
    }
    public ResponseEntity<byte[]>getPicture(Picture picture){
        return  ResponseEntity.ok().contentLength(picture.getData().length)
                .contentType(MediaType.parseMediaType(picture.getContentType()))
                .body(picture.getData());
    }
    public ResponseEntity<ResponseMessage>deletePicture(long id){
        if(pictureRepository.existsById(id)){
            pictureRepository.deleteById(id);
            return ResponseEntity.ok().body(new ResponseMessage("Successfully Deleted!"));
        }else{
            return ResponseEntity.badRequest().body(new ResponseMessage("The image was not found!"));
        }
    }
}
