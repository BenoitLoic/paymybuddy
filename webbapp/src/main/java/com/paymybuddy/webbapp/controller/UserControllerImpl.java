package com.paymybuddy.webbapp.controller;

import com.paymybuddy.webbapp.model.UserModel;
import com.paymybuddy.webbapp.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    return "success";
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
   * This method will return a list of all the user account in data base.
   *
   * @return the list of users
   */
  @Override
  @GetMapping("/users")
  @ResponseBody
  public List<UserModel> findAll() {

    log.info("Getting all user.");
    return userService.findAll();
  }
}
