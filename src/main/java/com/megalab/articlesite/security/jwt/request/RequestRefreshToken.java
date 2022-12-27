package com.megalab.articlesite.security.jwt.request;

import jakarta.validation.constraints.NotBlank;

public class RequestRefreshToken {
    @NotBlank
    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
