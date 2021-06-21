package com.paymybuddy.webbapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginControllerImpl implements LoginController {

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


}
