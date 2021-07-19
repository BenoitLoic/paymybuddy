package com.paymybuddy.webbapp.controller;

import com.paymybuddy.webbapp.dto.ContactDto;
import com.paymybuddy.webbapp.exception.BadArgumentException;
import com.paymybuddy.webbapp.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;

@Controller
public class ContactControllerImpl implements ContactController {

  private static final Logger log = LogManager.getLogger(ContactControllerImpl.class);
  private final UserService userService;

  @Autowired
  public ContactControllerImpl(UserService userService) {
    this.userService = userService;
  }

  /**
   * This method add a new contact to a user ContactList with the email of its future contact
   *
   * @param email of the contact to add // * @param principal current user logged in
   */
  @Override
  @PostMapping("/newContact")
  @ResponseStatus(HttpStatus.CREATED)
  public String addContact(@RequestParam String email, Principal principal) {

    if (email == null || email.isBlank()) {
      throw new BadArgumentException("Error - blank email.");
    }
    String userEmail = principal.getName();

    System.out.println(email);
    userService.addNewContact(email, userEmail);
    return "success";
  }

  /**
   * This method will delete a contact from the ContactList of ths user.
   *
   * @param email the email of the contact to delete
   * @param principal the current user logged in
   * @return success page
   */
  @Override
  @DeleteMapping("/deleteContact")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public String deleteContact(@RequestParam String email, Principal principal) {

    String userEmail = principal.getName();
    userService.deleteContact(email, userEmail);
    return "success";
  }

  /**
   * This method return all contact for the current user.
   *
   * @param principal the current user.
   * @return the collection of ContactDto
   */
  @Override
  @GetMapping("/home/contact")
  @ResponseStatus(HttpStatus.OK)
  public String getContact(Principal principal, Model model) {

    String userEmail = principal.getName();

    Collection<ContactDto> contacts = userService.getAllContact(userEmail);

    model.addAttribute("contacts", contacts);
    return "contact-page";
  }
}
