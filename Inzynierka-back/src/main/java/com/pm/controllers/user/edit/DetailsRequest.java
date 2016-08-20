package com.pm.controllers.user.edit;

/**
 * Created by izabella on 15.08.16.
 */
public class DetailsRequest {
    private String email;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
