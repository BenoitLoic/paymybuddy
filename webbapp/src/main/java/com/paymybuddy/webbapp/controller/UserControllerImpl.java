package com.paymybuddy.webbapp.controller;


import com.paymybuddy.webbapp.entity.User;
import com.paymybuddy.webbapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@EnableWebSecurity
@RestController
@RequestMapping("/V1")
public class UserControllerImpl implements UserController {

    @Autowired
    private UserService userService;



    @Override
    public String deleteUser(int id) {

        return null;
    }

    @Override
    @PutMapping("/user")
    public User updateUser(@RequestBody User theUser) {

        userService.save(theUser);

        return theUser;
    }

    @Override
    @GetMapping("/users")
    public List<User> findAll() {
        return userService.findAll();
    }

    @Override
    public User detUserById(int theId) {
        return null;
    }


}
