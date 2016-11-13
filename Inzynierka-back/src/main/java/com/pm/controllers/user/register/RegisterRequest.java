package com.pm.controllers.user.register;

/**
 * Created by izabella on 23.07.16.
 */
public class RegisterRequest {

    private String name;
    private String surname;
    private String login;
    private String email;
    private String password;
    private String lastLog;


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastLog() {
        return lastLog;
    }

    public void setLastLog(String lastLog) {
        this.lastLog = lastLog;
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
}
