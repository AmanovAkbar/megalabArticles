package com.megalab.articlesite.request;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public class RequestArticle {
    @Size(max=100)
    private String title;

    @Size(max=215)
    private String description;

    private String body;

    private String category;

    private MultipartFile articleCover;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public MultipartFile getArticleCover() {
        return articleCover;
    }

    public void setArticleCover(MultipartFile articleCover) {
        this.articleCover = articleCover;
    }

}
