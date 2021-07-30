package com.paymybuddy.webapp.controller;

import com.paymybuddy.webapp.dto.NewUserDto;
import org.springframework.validation.BindingResult;

/**
 * Controller for registration endpoints.
 *
 * <p>Contains method to get and post the register form.
 */
public interface RegistrationController {

  /**
   * This method return the registration form to create a new user.
   *
   * @return html page with registration form
   */
  String signUp();

  /**
   * This method create a new account in database for the given user.
   *
   * @param newUser the new user with all field mandatory (except balance and id). id is generated
   *     by Database and balance init to 0.
   */
  String registerNewUser(NewUserDto newUser, BindingResult bindingResult);
}
