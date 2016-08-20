package com.pm.controllers.user.login;

import com.pm.database.ReadFromDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

/**
 * Created by izabella on 23.07.16.
 */

@Controller
//@RequestMapping("/login")
public class LoginController {

    @Autowired
    ReadFromDatabase readClass;

    /*
    @RequestMapping(value = "/normal", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest receivedLoginRequest) {
        LoginResponse response = new LoginResponse();
        User user = readClass.searchOneByEmail(receivedLoginRequest.getEmail());
        if (user.getPassword().equals(receivedLoginRequest.getPassword())){
            System.out.println("good");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


    @RequestMapping(value = "/loginFb", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseEntity<LoginResponse> loginFacebook(@RequestBody LoginRequest receivedLoginRequest) {
        LoginResponse response = new LoginResponse();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @RequestMapping(value = "/loginGm", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseEntity<LoginResponse> loginGmail(@RequestBody LoginRequest receivedLoginRequest) {
        LoginResponse response = new LoginResponse();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @RequestMapping(value = "/loginTw", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseEntity<LoginResponse> loginTwitter(@RequestBody LoginRequest receivedLoginRequest) {
        LoginResponse response = new LoginResponse();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }*/
}
