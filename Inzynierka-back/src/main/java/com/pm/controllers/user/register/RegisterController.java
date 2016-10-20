package com.pm.controllers.user.register;

import com.pm.model.User;
import com.pm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * Created by izabella on 23.07.16.
 */

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/new", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseEntity<String> newUser(@RequestBody RegisterRequest registerRequest) {
        if (registerRequest == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error has occured");
        }
        String response = "";
        User u = new User();
        u.setLogin(registerRequest.getLogin());
        u.setEmail(registerRequest.getEmail());
        u.setPassword(registerRequest.getPassword());
        u.setFirstName(registerRequest.getFirstName());
        u.setLastName(registerRequest.getLastName());
        u.setLastLog((new Date().toString()));
        userRepository.save(u);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
