package com.paymybuddy.webbapp.controller;

import com.paymybuddy.webbapp.model.UserModel;
import com.paymybuddy.webbapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
@Slf4j
@Controller
public class LoginControllerImpl implements LoginController {


    @Autowired
    private UserService userService;


    @Override
    @GetMapping("/")
    public String welcomePage() {
        return "welcome-page";
    }

    @Override
    @GetMapping("/showLoginPage")
    public String showLoginPage() {
        return "plain-login";
    }



    @PutMapping("/test")
    public String testUserAccount(UserModel theUser) {

        System.out.println("param: "+ theUser);


        System.out.println("appel de service");

        userService.update(theUser);
return "plain-login";
    }
}
