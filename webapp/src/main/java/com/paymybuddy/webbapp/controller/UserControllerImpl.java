package com.paymybuddy.webbapp.controller;

import com.paymybuddy.webbapp.dto.GetUserAccountDto;
import com.paymybuddy.webbapp.entity.User;
import com.paymybuddy.webbapp.model.UserModel;
import com.paymybuddy.webbapp.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Controller for User account administration.
 *
 * <p>Contains method to delete or update the user account.
 */
@Controller
@RequestMapping("/home")
public class UserControllerImpl implements UserController {

  @Autowired private UserService userService;

  private final Logger log = LogManager.getLogger(getClass().getName());

  /**
   * This method will delete an user account.
   *
   * @param id the id of the user to delete.
   * @return confirmation message.
   */
  @Override
  @DeleteMapping("/user")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public String deleteUser(@RequestParam int id) {

    log.info("Deleting User with id : " + id);
    userService.deleteUserById(id);
    log.info("OK - user deleted.");
    return "plain-login";
  }

  /**
   * This method will update the account of the given user.
   *
   * @param theUser the user to update.
   * @return confirmation message
   */
  @Override
  @PutMapping("/user")
  @ResponseStatus(HttpStatus.CREATED)
  public String updateUser(UserModel theUser) {

    log.info("Updating User : " + theUser.getId());
    userService.update(theUser);
    log.info("OK - user updated.");

    return "success";
  }

  /**
   * This method will return the home page of the current user if authenticated. Else -> redirect to
   * login page.
   *
   * @param principal the current authenticated user.
   * @param model the user information saved.
   * @return the html page for user account
   */
  @Override
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public String getUserAccount(Principal principal, Model model) {

    String email = principal.getName();
    User user = userService.findByEmail(email).get();
    GetUserAccountDto theUser = new GetUserAccountDto();
    BeanUtils.copyProperties(user, theUser, "password");
    model.addAttribute("theUser", theUser);

    log.info("Getting user account for user : " + email);

    return "home";
  }

  /**
   * This method will return the html form to update the current user.
   *
   * @param principal the current user.
   * @param model the user account.
   * @return the html form.
   */
  @Override
  @GetMapping("/profile")
  @ResponseStatus(HttpStatus.OK)
  public String getUserProfile(Principal principal, Model model) {

    String email = principal.getName();

    log.info("Getting user account for user : " + email );

    User user = userService.findByEmail(email).get();
    GetUserAccountDto theUser = new GetUserAccountDto();
    BeanUtils.copyProperties(user, theUser, "password");
    model.addAttribute("theUser", theUser);

    return "edit-user-profile";
  }
}
