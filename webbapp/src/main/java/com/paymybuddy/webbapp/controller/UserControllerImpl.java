package com.paymybuddy.webbapp.controller;


import com.paymybuddy.webbapp.entity.User;
import com.paymybuddy.webbapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@EnableWebSecurity(debug = true)
@RestController
@RequestMapping("/V1")
public class UserControllerImpl implements UserController {

    @Autowired
    private UserService userService;


    /**
     * This method add a new user in database.
     * @param user email, firstName, lastName and password are mandatory.
     * @return the user created with its id
     */
    @Override
    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody @Valid User user) {

        //set id to 0 to force save in Dao

        user.setId(0);
        userService.save(user);

        return user;
    }

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
