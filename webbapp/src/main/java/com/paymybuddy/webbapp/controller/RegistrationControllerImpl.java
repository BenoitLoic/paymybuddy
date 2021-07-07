package com.paymybuddy.webbapp.controller;

import com.paymybuddy.webbapp.dto.NewUserDto;
import com.paymybuddy.webbapp.exception.BadArgumentException;
import com.paymybuddy.webbapp.exception.DataAlreadyExistException;
import com.paymybuddy.webbapp.model.UserModel;
import com.paymybuddy.webbapp.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;

@Controller
@RequestMapping("/registration")
public class RegistrationControllerImpl implements RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationControllerImpl(UserService userService) {
        this.userService = userService;
    }

    /**
     * This method return the registration form to create a new user.
     *
     * @return html page with registration form
     */
    @Override
    @GetMapping("/signUp")
    public String signUp() {
        return "registration-page";
    }

    /**
     * This method create a new user in db.
     *
     * @param newUser       the new user with all field mandatory (except balance and id).
     * @return html page that confirm registration
     */
    @Override
    @PostMapping(value = "/createNewUser")
    @ResponseStatus(HttpStatus.CREATED)
    public String registerNewUser(@Valid NewUserDto newUser, BindingResult bindingResult) {

        // Check if there is error in validation
        if (bindingResult.hasErrors()) {
            throw new BadArgumentException("KO - error in registration form.");
        }

        // Check if email already exist in DB.

        if (userService.findByEmail(newUser.getEmail()).isPresent()) {
            throw new DataAlreadyExistException("KO - user: " + newUser.getEmail() + " already exist.");
        }

        UserModel user = new UserModel();
        BeanUtils.copyProperties(newUser, user);

        // Create user
        userService.save(user);

        return "registration-confirmation";
    }
}
