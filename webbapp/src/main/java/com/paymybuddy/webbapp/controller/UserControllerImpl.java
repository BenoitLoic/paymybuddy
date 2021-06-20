package com.paymybuddy.webbapp.controller;


import com.paymybuddy.webbapp.entity.User;
import com.paymybuddy.webbapp.model.CrmUser;
import com.paymybuddy.webbapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public User createUser(@Valid @ModelAttribute("crmUser") CrmUser crmUser, BindingResult bindingResult, Model model) {

        // Validation form
//        if (bindingResult.hasErrors()){
//            throw new IllegalArgumentException("There is error in registration form.");
//        }

        // Check if user already exist
//        if (userService.findByEmail(user.getEmail()).isPresent()){
//            throw new DataAlreadyExistException("Account already exist for email: "+user.getEmail());
//        }

        // create user
User user = new User();
        user.setFirstName(crmUser.getFirstName());
        user.setLastName(crmUser.getLastName());
        user.setEmail(crmUser.getEmail());
        user.setPassword(crmUser.getPassword());
        user.setAddressNumber(crmUser.getAddressNumber());
        user.setAddressPrefix(crmUser.getAddressPrefix());
        user.setAddressStreet(crmUser.getAddressStreet());
        user.setPhone(crmUser.getPhone());
        user.setZip(crmUser.getZip());
        user.setCity(crmUser.getCity());
        System.out.println(user);
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
