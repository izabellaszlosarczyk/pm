package com.pm.controllers.LoginOrRegistration;

import com.pm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/users/{login}", method = RequestMethod.GET)
    @ResponseBody
    public int getAttr(@PathVariable(value="login") String login) {
        return 0;
    }
}
