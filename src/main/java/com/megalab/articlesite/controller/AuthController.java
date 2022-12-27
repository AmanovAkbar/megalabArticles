package com.megalab.articlesite.controller;

import com.megalab.articlesite.security.jwt.request.RequestRefreshToken;
import com.megalab.articlesite.security.jwt.response.ResponseJwt;
import com.megalab.articlesite.security.jwt.response.ResponseMessage;
import com.megalab.articlesite.security.jwt.response.ResponseRefreshToken;
import com.megalab.articlesite.security.request.RequestLogin;
import com.megalab.articlesite.security.request.RequestSignup;
import com.megalab.articlesite.security.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<ResponseJwt> authenticateUser(@Valid @RequestBody RequestLogin requestLogin){
        return authService.authenticateUser(requestLogin);
    }
    @PostMapping("/register")
    public ResponseEntity<ResponseMessage>registerUser(@Valid @RequestBody RequestSignup requestSignup){
        return authService.registerUser(requestSignup);
    }


    @PostMapping("/refreshtoken")
    public ResponseEntity<ResponseRefreshToken> refreshToken(@Valid @RequestBody RequestRefreshToken requestRefreshToken){
        return authService.refreshToken(requestRefreshToken);
    }
}

