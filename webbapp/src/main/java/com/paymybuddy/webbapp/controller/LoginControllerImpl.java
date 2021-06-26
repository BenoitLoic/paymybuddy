package com.paymybuddy.webbapp.controller;

import com.paymybuddy.webbapp.entity.User;
import com.paymybuddy.webbapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
    public void testUserAccount() {

//        System.out.println("param: " + theUser);


        System.out.println("appel de service");

        User myUser= new User();
        myUser = userService.findById(1).get();
        System.out.println("MY CONTACT LIST : ");
        for (User user: myUser.getContactList()
             ) {
            System.out.println(user);
        }
        System.out.println("AS CONTACT LIST: ");
        for (User user: myUser.getAsContactOfList()
        ) {
            System.out.println(user);
        }

        System.out.println(userService.findById(1).get());

    }
}
