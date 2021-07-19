package com.paymybuddy.webbapp.controller;

import org.springframework.ui.Model;

import java.security.Principal;

/** This interface contains some method to manage the user account. */
public interface AccountController {

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
