package com.paymybuddy.webbapp.service;

import com.paymybuddy.webbapp.dto.ContactDto;
import com.paymybuddy.webbapp.entity.User;
import com.paymybuddy.webbapp.model.UserModel;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserService {
    /**
     * This method save the given user by calling the repository.
     *
     * @param theUser to save
     */
    void save(User theUser);

    /**
     * This method update the user account by calling repository.
     *
     * @param theUser to update
     */
    void update(UserModel theUser);


    /**
     * This method will delete an user from database with its id.
     *
     * @param theId of the user to delete
     */
    void deleteUserById(int theId);

    /**
     * This method will return all user from repository
     *
     * @return List of all users
     */
    List<User> findAll();

    /**
     * This method will return an user from repository based on its id.
     *
     * @param theId of the user retrieve
     * @return the user
     */
    Optional<User> findById(int theId);

    /**
     * This method will return an user from repository based on its email.
     *
     * @param email of the user to retrieve
     * @return the user
     */
    Optional<User> findByEmail(String email);

    /**
     * This method will add a new contact to the contact list of the current user using repository.
     *
     * @param contactEmail the email of the contact to add
     * @param userEmail    the email of the current user
     * @return the firstName + " " + lastName of the new contact
     */
    String addNewContact(String contactEmail, String userEmail);

    /**
     * This method will delete the given contact from the contact list of the current user
     *
     * @param email the email of the contact to delete from list
     * @param userEmail the email of the current user
     * @return the firstName + " " + lastName of the deleted contact
     */
    ContactDto deleteContact(String email, String userEmail);

    /**
     * This method returns a collection of all the contacts of the given user.
     *
     * @param userEmail the user email.
     * @return all the contacts
     */
    Collection<ContactDto> getAllContact(String userEmail);
}
