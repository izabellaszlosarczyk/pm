package com.pm.controllers.LoginOrRegistration;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;

@Data
@NoArgsConstructor
public class LoginResponse {

    private String userId;
    private String token;
    private String firstName;
    private String lastName;
    private String email;


    public LoginResponse(String userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public LoginResponse(LoginResponseModel loginResponseModel) {
        this.userId = loginResponseModel.getId();
        this.token = loginResponseModel.getToken().name();
        this.firstName = loginResponseModel.getFirstName();
        this.lastName = loginResponseModel.getLastName();
        this.email = loginResponseModel.getEmail();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
