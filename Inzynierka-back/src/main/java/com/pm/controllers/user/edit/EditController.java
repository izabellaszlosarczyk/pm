package com.pm.controllers.user.edit;

import com.pm.database.ReadFromDatabase;
import com.pm.database.SaveUpdateDatabase;
import com.pm.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
        System.out.println("DUUUUUUUUUUUUUUUPA");
        System.out.println(user);
        return ResponseEntity.status(HttpStatus.OK).body(user);

    }


    @RequestMapping(value = "/saveEdited", method=RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseEntity<String> saveUser(@RequestBody EditRequest editedUser){
        System.out.println(editedUser);
//        if (editedUser == null ) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        if (editedUser.getPassword() == null || editedUser.getEmail() == null){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//        editClass.saveUser(editedUser);
       return  ResponseEntity.status(HttpStatus.OK).body("OK");
    }
}
