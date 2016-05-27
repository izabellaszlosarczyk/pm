package com.pm.controllers.LoginOrRegistration;

import lombok.Data;

import java.util.List;

@Data
public class LoginResponseModel {
    private String id;
    private AuthorizationToken token;
    private String indexNumber;
    private String firstName;
    private String lastName;
    private String email;

    public LoginResponseModel(UserLoginData userLoginData) {
        this.id = userLoginData.getId();
        this.token = userLoginData.getToken();
        this.firstName = userLoginData.getFirstName();
        this.lastName = userLoginData.getLastName();
        this.email = userLoginData.getEmail();
    }

    public LoginResponseModel(AuthorizationToken token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public AuthorizationToken getToken() {
        return token;
    }

    public String getIndexNumber() {
        return indexNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }
}
