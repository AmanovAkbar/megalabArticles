package com.megalab.articlesite.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RequestCommentary {
    @NotBlank
    private String body;

    private Long replied_id;
    @NotNull
    private Long article_id;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Long getReplied_id() {
        return replied_id;
    }

    public void setReplied_id(Long replied_id) {
        this.replied_id = replied_id;
    }

    public long getArticle_id() {
        return article_id;
    }

    public void setArticle_id(Long article_id) {
        this.article_id = article_id;
    }
}
