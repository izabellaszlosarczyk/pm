package com.pm.controllers.user.register;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pm.controllers.contentService.userContent.FileOperations;
import com.pm.model.User;
import com.pm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

/**
 * Created by izabella on 23.07.16.
 */

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    MongoTemplate mongoTemplate;

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> newUser(@RequestBody RegisterRequest registerRequest) {
        ObjectMapper objectMapper = new ObjectMapper();
        if (registerRequest == null){
            try {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(objectMapper.writeValueAsString("Error has occured"));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        System.out.println("REEEEEEEJESTRACJA");
        System.out.println(registerRequest.getEmail());
        System.out.println(registerRequest.getName());
        User u = new User();
        u.setLogin(registerRequest.getLogin());
        u.setEmail(registerRequest.getEmail());
        u.setPassword(registerRequest.getPassword());
        u.setFirstName(registerRequest.getName());
        u.setLastName(registerRequest.getSurname());
        u.setLastLog(LocalDate.now().toString());

        u.setPreviousLog(LocalDate.now().toString());
        u.setProfilePhoto("image69"); //zdjecie domyslne
        System.out.println("dupa");
        System.out.println(u.getLastLog());
//        String name = "file.py";
//        Resource resource = resourceLoader.getResource("classpath:/static/"+name);

        u.setProfilePhoto("zbEebNB.jpg");

        userRepository.save(u);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(objectMapper.writeValueAsString("ok"));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
