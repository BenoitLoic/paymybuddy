package com.paymybuddy.webbapp.controller;

import com.paymybuddy.webbapp.entity.User;
import com.paymybuddy.webbapp.model.UserModel;
import com.paymybuddy.webbapp.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.security.Principal;

@Controller
@RequestMapping("/home")
public class AccountController {


//TODO ajouter /home/** en .authenticated()


    @Autowired
    UserService userService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String getUserAccount(Principal principal, UserModel userModel, BindingResult bindingResult) {

        String email = principal.getName();
        if (bindingResult.hasErrors()
                || email == null
                || userService.findByEmail(email).isEmpty()) {
            throw new IllegalArgumentException("Can't find account for user: " + email);
        }

        User user = userService.findByEmail(email).get();
        BeanUtils.copyProperties(user, userModel);
        return "home";
    }

    @GetMapping("/contact")
    @ResponseStatus(HttpStatus.OK)
    public String getUserContact() {
        return "todo";
    }

    @GetMapping("/profile")
    @ResponseStatus(HttpStatus.OK)
    public String getUserProfile(Principal principal, UserModel userModel) {
        String email = principal.getName();
        if (email == null
                || userService.findByEmail(email).isEmpty()) {
            throw new IllegalArgumentException("Can't find account for user: " + email);
        }

        User user = userService.findByEmail(email).get();
        BeanUtils.copyProperties(user, userModel);

        return "edit-user-profile";
    }


}
