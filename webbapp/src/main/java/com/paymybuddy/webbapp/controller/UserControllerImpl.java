package com.paymybuddy.webbapp.controller;


import com.paymybuddy.webbapp.model.UserModel;
import com.paymybuddy.webbapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/home")
public class UserControllerImpl implements UserController {

    @Autowired
    private UserService userService;


    /**
     * This method will delete an user account.
     * @param id the id of the user to delete.
     * @return confirmation message.
     */
    @Override
    @DeleteMapping("/user")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String deleteUser(@RequestParam int id) {

        userService.deleteUserById(id);

        return "success";
    }


    /**
     * This method will update the account of the given user.
     * @param theUser the user to update.
     * @return confirmation message
     */
    @Override
    @PutMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public String updateUser(UserModel theUser) {

        userService.update(theUser);

        return "success";
    }


    /**
     * This method will return a list of all the user account in data base.
     * @return the list of users
     */
    @Override
    @GetMapping("/users")
    @ResponseBody
    public List<UserModel> findAll() {
        return userService.findAll();
    }


}
