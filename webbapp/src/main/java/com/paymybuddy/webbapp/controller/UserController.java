package com.paymybuddy.webbapp.controller;

import com.paymybuddy.webbapp.model.UserModel;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.List;

/**
 * Controller for User account administration.
 *
 * <p>Contains method to delete or update the user account.
 */
public interface UserController {

  /**
   * This method will delete an user account.
   *
   * @param thId of the user to delete.
   * @return confirmation message.
   */
  String deleteUser(int thId);

  /**
   * This method will update the account of the given user.
   *
   * @param user the user to update.
   * @return confirmation message
   */
  String updateUser(UserModel user);

  /**
   * This method will return a list of all the user account in data base.
   *
   * @return the list of users
   */
  List<UserModel> findAll();
  /**
   * This method will return the home page of the current user if authenticated.
   *
   * @param principal the current authenticated user.
   * @param model the user information saved.
   * @return the html page for user account
   */
  String getUserAccount(Principal principal, Model model);

  /**
   * This method will return the html form to update the current user.
   *
   * @param principal the current user.
   * @param model the user account.
   * @return the html form.
   */
  String getUserProfile(Principal principal, Model model);
}
