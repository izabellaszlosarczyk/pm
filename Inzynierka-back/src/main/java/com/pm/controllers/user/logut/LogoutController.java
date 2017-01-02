package com.pm.controllers.user.logut;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pm.model.File;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by izabella on 23.07.16.
 */

@Controller
@RequestMapping("/logout")
public class LogoutController {

    @RequestMapping(value = "/out", method= RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> logout() {

        return  ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
