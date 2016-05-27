package com.pm.controllers.LoginOrRegistration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pm.database.ReadFromDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class LoginController {

    @Autowired
    private ReadFromDatabase readDatabase;


    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseEntity<String> logInUser(@RequestBody String receivedLoginRequest) {
        // temporary fake values
        ObjectMapper jsonMapper = new ObjectMapper();
        LoginRequest loginRequest = null;
        try {
            loginRequest = jsonMapper.readValue(receivedLoginRequest, LoginRequest.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(loginRequest);
        ResponseEntity responseEntity =  new AuthorizationService(readDatabase).getServerLoginResponse(loginRequest, AuthorizationCore.getInstance());
        return responseEntity;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ResponseBody
    LogoutResponse logOutUser() {
        // temporary fake values
        return LogoutResponse.builder().token(556646).build();
    }
}
