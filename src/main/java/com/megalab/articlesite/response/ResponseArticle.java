package com.megalab.articlesite.response;

import com.megalab.articlesite.model.Category;
import com.megalab.articlesite.model.Picture;
import com.megalab.articlesite.model.User;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

public class ResponseArticle {
    private long id;

    private String title;


    private String pictureUri;

    private String description;

    private String body;

    private Instant createdAt;

    private long creatorId;

    private String categoryName;

    public ResponseArticle(long id, String title, String pictureUri, String description,
                           String body, Instant createdAt, long creatorId, String categoryName) {
        this.id = id;
        this.title = title;
        this.pictureUri = pictureUri;
        this.description = description;
        this.body = body;
        this.createdAt = createdAt;
        this.creatorId = creatorId;
        this.categoryName = categoryName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPictureUri() {
        return pictureUri;
    }

    public void setPictureUri(String pictureUri) {
        this.pictureUri = pictureUri;
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

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(long creatorId) {
        this.creatorId = creatorId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
