package com.paymybuddy.webbapp.controller;

import com.paymybuddy.webbapp.dto.GetUserAccountDto;
import com.paymybuddy.webbapp.entity.User;
import com.paymybuddy.webbapp.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.security.Principal;

/** This class contains some method to manage the user account. */
@Controller
@RequestMapping("/home")
public class AccountControllerImpl implements AccountController {

  @Autowired UserService userService;

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

    if (principal.getName() == null) {
      return "plain-login";
    }

    User user = userService.findByEmail(email).get();
    GetUserAccountDto theUser = new GetUserAccountDto();
    BeanUtils.copyProperties(user, theUser, "password");

    model.addAttribute("theUser", theUser);
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
    if (email == null || userService.findByEmail(email).isEmpty()) {
      throw new IllegalArgumentException("Can't find account for user: " + email);
    }

    User user = userService.findByEmail(email).get();
    GetUserAccountDto theUser = new GetUserAccountDto();
    BeanUtils.copyProperties(user, theUser, "password");

    model.addAttribute("theUser", theUser);

    return "edit-user-profile";
  }
}
