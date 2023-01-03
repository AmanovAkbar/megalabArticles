package com.megalab.articlesite.security.request;

import jakarta.validation.constraints.*;

import java.util.Set;

public class RequestSignup {
    @NotBlank
    @Size( max = 20)
    private String firstname;
    @NotBlank
    @Size( max = 20)
    private String lastname;
    @NotBlank
    @Size( max = 50)
    private String username;
    @NotBlank
    @Size(min = 5, max = 50)
    private String password;
    @NotBlank
    @Size(min = 5, max = 50)
    private String secondPassword;
    private Set<String> role;


    public String getSecondPassword() {
        return secondPassword;
    }

    public void setSecondPassword(String secondPassword) {
        this.secondPassword = secondPassword;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRole() {
        return role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }
}
