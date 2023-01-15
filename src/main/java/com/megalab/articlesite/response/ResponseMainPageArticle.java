package com.megalab.articlesite.response;

import java.time.Instant;
import java.time.LocalDate;

public class ResponseMainPageArticle {
    Long id;
    String title;
    String pictureUri;
    String description;
    String categoryName;
    Instant createdAt;
    String articleUrl;

    public ResponseMainPageArticle(Long id, String title, String pictureUri,
                                   String description, Instant createdAt, String articleUrl, String categoryName) {
        this.id = id;
        this.title = title;
        this.pictureUri = pictureUri;
        this.description = description;
        this.createdAt = createdAt;
        this.articleUrl = articleUrl;
        this.categoryName = categoryName;
    }

    public Long getId() {
        return id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }

    public void setId(Long id) {
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

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
