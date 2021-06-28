package com.paymybuddy.webbapp.controller;

import com.paymybuddy.webbapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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


    @GetMapping("/test")
    @ResponseBody
    public void testUserAccount(@RequestParam String contactEmail) {
//        contactEmail="test4";
String userEmail = "loic@mail.com";

        System.out.println(contactEmail);
        userService.addNewContact(contactEmail, userEmail);
    }
}
