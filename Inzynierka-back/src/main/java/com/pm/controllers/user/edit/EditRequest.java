package com.pm.controllers.user.edit;

/**
 * Created by izabella on 23.07.16.
 */
public class EditRequest {

    private String name;
    private String surname;
    private String email;
    private String oldemail;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOldemail() {
        return oldemail;
    }

    public void setOldemail(String oldemail) {
        this.oldemail = oldemail;
    }
}
