package com.pm.controllers.user.edit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.gridfs.GridFSDBFile;
import com.pm.database.ReadFromDatabase;
import com.pm.database.SaveUpdateDatabase;
import com.pm.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * Created by izabella on 23.07.16.
 */
@Controller
@RequestMapping("/edit")
public class EditController {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    ReadFromDatabase readClass;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    SaveUpdateDatabase editClass;

    @RequestMapping(value = "/getUser", method= RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<User> editUser(@RequestBody String id) {
        User user = null;
        if (id == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(user);
        }else {
            System.out.println("edit id user:" + id);
            String ajdi = (StringUtils.substringsBetween(id , "\"", "\""))[0];
            user = readClass.searchOneById(id);
            if (user ==null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }

    }

    @RequestMapping(value = "/getAfterLogin", method= RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<User> getByEmailAndPassword(@RequestBody DetailsRequest userRequest) {
        User user = null;
        if (userRequest == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(user);
        System.out.println("edit id user:" + userRequest.getEmail() + userRequest.getPassword());
        user = readClass.searchOneByEmailAndPassword(userRequest.getEmail(), userRequest.getPassword());
        if (user ==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        user.setPreviousLog(LocalDate.now().toString());
        user.setLastLog(LocalDate.now().toString());
        editClass.saveUser(user);
        System.out.println(user);
        return ResponseEntity.status(HttpStatus.OK).body(user);

    }

    @RequestMapping(value = "/editTime/{email:.+}", method= RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> userSubs(@PathVariable String email) {
        System.out.println("WHYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY");
        System.out.println(email);
        ObjectMapper ob = new ObjectMapper();
        User user = readClass.searchOneByEmail(email);
        System.out.println(user);
        if (user == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"value\":\"bad request\"}");
        user.setPreviousLog(LocalDate.now().toString());
        user.setLastLog(LocalDate.now().toString());
        editClass.saveUser(user);
        return  ResponseEntity.status(HttpStatus.OK).body("{\"value\":\"OK\"}");
    }

    @RequestMapping(value = "/saveEdited", method=RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> saveUser(@RequestBody EditRequest editedUser){
        System.out.println(editedUser);
        User user = readClass.searchOneByEmail(editedUser.getOldemail());
        System.out.println(user);
        if (user == null)return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"value\":\"NOPE\"}");
        if (!editedUser.getEmail().isEmpty()) user.setEmail(editedUser.getEmail());
        if (!editedUser.getPassword().isEmpty()) user.setPassword(editedUser.getPassword());
        if (!editedUser.getName().isEmpty()) user.setFirstName(editedUser.getName());
        if (!editedUser.getSurname().isEmpty()) user.setLastName(editedUser.getSurname());

        editClass.saveUser(user);
       return  ResponseEntity.status(HttpStatus.OK).body("{\"value\":\"OK\"}");
    }
    @RequestMapping(value = "/saveEditedPhoto", method=RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> saveEditedPhoto(@RequestBody EditPhotoRequest editedUser){
        System.out.println(editedUser.getTitle());
        User user = readClass.searchOneByEmail(editedUser.getEmail());
        System.out.println(user.getEmail());
        System.out.println(user.getProfilePhoto());
        if (user == null)return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"value\":\"NOPE\"}");
        if (!editedUser.getTitle().isEmpty()) user.setProfilePhoto(editedUser.getTitle());

        editClass.saveUser(user);
        System.out.println(user.getProfilePhoto());
        return  ResponseEntity.status(HttpStatus.OK).body("{\"value\":\"OK\"}");
    }

}
