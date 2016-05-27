package com.pm.controllers.LoginOrRegistration;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserLoginData {
    private String id;
    private String email;
    private String password;
    private AuthorizationToken token;
    private String firstName;
    private String lastName;


    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }


    public String getPassword() {
        return password;
    }


    public AuthorizationToken getToken() {
        return token;
    }


    public String getFirstName() {
        return firstName;
    }


    public String getLastName() {
        return lastName;
    }

}
