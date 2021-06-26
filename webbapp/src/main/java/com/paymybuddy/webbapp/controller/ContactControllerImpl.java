package com.paymybuddy.webbapp.controller;

import com.paymybuddy.webbapp.dto.ContactDto;
import com.paymybuddy.webbapp.exception.UnicornException;
import com.paymybuddy.webbapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;

@Controller
public class ContactControllerImpl implements ContactController {

    private final
    UserService userService;

    @Autowired
    public ContactControllerImpl(UserService userService) {
        this.userService = userService;
    }


    /**
     * This method add a new contact to a user ContactList with the email of its future contact
     *
     * @param contactEmail email of the contact to add
     * @param principal    current user logged in
     */
    @Override
    @PostMapping("/newContact")
    @ResponseStatus(HttpStatus.CREATED)
    public String addContact(String contactEmail, Principal principal) {

        if (contactEmail == null || contactEmail.isBlank()) {
            throw new UnicornException("Hé Oh c'est nul !");
        }
        if (principal.getName() == null) {
            return "plain-login";
        }
        String userEmail = principal.getName();

        return userService.addNewContact(contactEmail, userEmail);

    }

    /**
     * This method will delete a contact from the ContactList of ths user.
     *
     * @param id        the id of the contact to delete
     * @param principal the current user logged in
     */
    @Override
    @DeleteMapping("/deleteContact")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteContact(@RequestParam int id, Principal principal) {

        if (principal.getName() == null) {
//            return "plain-login";
        }
        String userEmail = principal.getName();
        userService.deleteContact(id, userEmail);

    }

    /**
     * This method return all contact for the current user.
     *
     * @param principal the current user.
     * @return the collection of ContactDto
     */
    @Override
    @GetMapping("/getContact")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Collection<ContactDto> getContact(Principal principal) {

        if (principal.getName() == null) {
            // TODO
            throw new UnicornException("Je sais pas encore quoi mettre ici...");
        }
        String userEmail = principal.getName();

        Collection<ContactDto> collection = userService.getAllContact(userEmail);

        return collection;
    }
}
