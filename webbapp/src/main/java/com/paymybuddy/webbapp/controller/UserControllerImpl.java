package com.paymybuddy.webbapp.controller;


import com.paymybuddy.webbapp.entity.User;
import com.paymybuddy.webbapp.model.UserModel;
import com.paymybuddy.webbapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @DeleteMapping("/user")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String deleteUser(@RequestParam int id) {

        userService.deleteUserById(id);

        return "Account for id: " + id + " deleted.";
    }

    @Override
    @PutMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public String updateUser(UserModel theUser) {

        userService.update(theUser);

        return "user: " + theUser.getEmail() + " updated.";
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
