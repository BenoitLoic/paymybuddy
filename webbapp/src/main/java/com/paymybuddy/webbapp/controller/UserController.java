package com.paymybuddy.webbapp.controller;

import com.paymybuddy.webbapp.model.UserModel;

import java.util.List;

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
}
