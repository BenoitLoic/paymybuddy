package com.paymybuddy.webbapp.controller;

import com.paymybuddy.webbapp.dto.ContactDto;
import com.paymybuddy.webbapp.exception.UnicornException;
import com.paymybuddy.webbapp.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;

@Log4j2
@Controller
public class ContactControllerImpl implements ContactController {

    private final
    UserService userService;

    @Autowired
    public ContactControllerImpl(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/newContactPage")
    public String newContactForm() {
        return "newcontact-page";
    }

    /**
     * This method add a new contact to a user ContactList with the email of its future contact
     *
     * @param email of the contact to add
     *              //     * @param principal    current user logged in
     */
    @Override
    @PostMapping("/newContact")
    @ResponseStatus(HttpStatus.CREATED)
    public String addContact(@RequestParam String email, Principal principal) {

        if (email == null || email.isBlank()) {
            System.out.println("error contactEmail == null");
            throw new UnicornException("Hé Oh c'est nul !");
        }
        if (principal == null || principal.getName() == null) {
            log.error("Error - user not logged in.");
            return "plain-login";
        }
        String userEmail = principal.getName();

        System.out.println(email);
        userService.addNewContact(email, userEmail);
        return "redirect://home/getContact";

    }

    /**
     * This method will delete a contact from the ContactList of ths user.
     *
     * @param email      the email of the contact to delete
     * @param principal the current user logged in
     */
    @Override
    @DeleteMapping("/deleteContact")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteContact(@RequestParam String email, Principal principal) {

        if (principal.getName() == null) {
//            return "plain-login";
        }
        String userEmail = principal.getName();
        userService.deleteContact(email, userEmail);

    }

    /**
     * This method return all contact for the current user.
     *
     * @param principal the current user.
     * @return the collection of ContactDto
     */
    @Override
    @GetMapping("/home/getContact")
    @ResponseStatus(HttpStatus.OK)
    public String getContact(Principal principal, Model model) {

        if (principal == null || principal.getName() == null) {

            log.error("Error - user not logged in.");
            return "plain-login";


        }
        String userEmail = principal.getName();

        Collection<ContactDto> contacts = userService.getAllContact(userEmail);

        model.addAttribute("contacts", contacts);
        return "contact-page";
    }
}
