package com.paymybuddy.webbapp.service;

import com.paymybuddy.webbapp.dto.ContactDto;
import com.paymybuddy.webbapp.entity.User;
import com.paymybuddy.webbapp.exception.DataAlreadyExistException;
import com.paymybuddy.webbapp.exception.DataNotFindException;
import com.paymybuddy.webbapp.model.UserModel;
import com.paymybuddy.webbapp.repository.UserRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;



@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    /**
     * This method save the given user in DB by calling Dao.
     * this method is used to create a new user
     * or update an existing user
     *
     * @param theUser to save
     */
    @Override
    public void save( UserModel theUser) {

        User user = new User();
        BeanUtils.copyProperties(theUser,user);

        // Hash password using BCrypt
        if (theUser.getPassword() != null) {
            user.setPassword(bCryptPasswordEncoder.encode(theUser.getPassword()));
        }
        // Save user using JpaRepository.save
        userRepository.save(user);
    }


    /**
     * This method update the user account by calling repository.
     *
     * @param theUser to update
     */
    @Override
    public void update(UserModel theUser) {

        if (!userRepository.existsById(theUser.getId())) {
            throw new DataNotFindException("Can't find account for user: " + theUser.getEmail());
        }
        // Copy non null field from user model to user entity
        User userEntity = userRepository.getById(theUser.getId());

        if (theUser.getFirstName() != null && !theUser.getFirstName().isBlank()) {
            userEntity.setFirstName(theUser.getFirstName());
        }

        if (theUser.getLastName() != null && !theUser.getLastName().isBlank()) {
            userEntity.setLastName(theUser.getLastName());
        }

        if (theUser.getEmail() != null && !theUser.getEmail().isBlank()) {
            userEntity.setEmail(theUser.getEmail());
        }

        if (theUser.getPassword() != null && !theUser.getPassword().isBlank()) {
            userEntity.setPassword(bCryptPasswordEncoder.encode(theUser.getPassword()));
        }
        if (theUser.getPhone() != null && !theUser.getPhone().isBlank()) {
            userEntity.setPhone(theUser.getPhone());
        }

        if (theUser.getAddressPrefix() != null && !theUser.getAddressPrefix().isBlank()) {
            userEntity.setAddressPrefix(theUser.getAddressPrefix());
        }
        if (theUser.getAddressStreet() != null && !theUser.getAddressStreet().isBlank()) {
            userEntity.setAddressStreet(theUser.getAddressStreet());
        }
        if (theUser.getAddressStreet() != null && !theUser.getAddressNumber().isBlank()) {
            userEntity.setAddressNumber(theUser.getAddressNumber());
        }
        if (theUser.getCity() != null && !theUser.getCity().isBlank()) {
            userEntity.setCity(theUser.getCity());
        }

        if (theUser.getZip() != null && !theUser.getZip().isBlank()) {
            userEntity.setZip(theUser.getZip());
        }

        userRepository.save(userEntity);
    }


    /**
     * This method will delete an user from database with its id.
     *
     * @param theId of the user to delete
     */
    @Override
    public void deleteUserById(int theId) {

        // Check if user exist
        if (!userRepository.existsById(theId)) {
            throw new DataNotFindException("KO - can't find user with id: " + theId);
        }
        // Delete user
        userRepository.deleteById(theId);

    }


    /**
     * This method will return all user from repository
     *
     * @return List of all users
     */
    @Override
    public List<UserModel> findAll() {

        List<UserModel> users = new ArrayList<>();
        for (User user : userRepository.findAll()){
            UserModel temp = new UserModel();
            temp.setEmail(user.getEmail());
            temp.setFirstName(user.getFirstName());
            temp.setId(user.getId());
            users.add(temp);
        }

        return users;
    }


    /**
     * This method will return an user from repository based on its id.
     *
     * @param theId of the user retrieve
     * @return the user
     */
    @Override
    public Optional<User> findById(int theId) {
        return userRepository.findById(theId);
    }


    /**
     * This method will return an user from repository based on its email.
     *
     * @param email of the user to retrieve
     * @return the user
     */
    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    /**
     * This method will add a new contact to the contact list of the current user using repository.
     *
     * @param contactEmail the email of the contact to add
     * @param userEmail    the email of the current user
     * @return the firstName + " " + lastName of the new contact
     */
    @Override
    public String addNewContact(String contactEmail, String userEmail) {

        // check if user for contactEmail exist
        Optional<User> contactOp = userRepository.findByEmail(contactEmail);
        if (contactOp.isEmpty()) {
            throw new DataNotFindException("KO - can't find user: " + contactEmail);
        }
        User contact = contactOp.get();

        // check if contact already exist in userContactList
        User user = userRepository.findByEmail(userEmail).get();
        if (user.getContacts().contains(contact)) {
            throw new DataAlreadyExistException("KO - This contact is already linked, contact: " + contactEmail);
        }

        // save and return firstName and lastName of contact
        user.getContacts().add(contact);
        userRepository.save(user);

        return contact.getFirstName() + " " + contact.getLastName();
    }


    /**
     * This method will delete the given contact from the contact list of the current user
     *
     * @param email     the email of the contact to delete from list
     * @param userEmail the email of the current user
     * @return the firstName + " " + lastName of the deleted contact
     */
    @Override
    public ContactDto deleteContact(String email, String userEmail) {

        // get current user
        User user = userRepository.findByEmail(userEmail).get();
        ContactDto contactDto = new ContactDto();

        // check if contact is present in contactList
        Set<User> contactList = user.getContacts();
        User userToDelete = new User();
        boolean deleted = false;
        for (User contact : contactList) {
            if (Objects.equals(contact.getEmail(), email)) {
                // create dto of contact for return
                contactDto.setEmail(contact.getEmail());
                contactDto.setFirstName(contact.getFirstName());
                contactDto.setLastName(contact.getLastName());
                deleted = true;
                // remove contact from list
                userToDelete = contact;

            }
        }
        if (!deleted) {
            throw new DataNotFindException("Error - can't find user with email: " + email);
        }
        contactList.remove(userToDelete);
        //  save
        userRepository.save(user);
        return contactDto;

    }


    /**
     * This method returns a collection of all the contacts of the given user.
     *
     * @param userEmail the user email.
     * @return all the contacts
     */
    @Override
    public Collection<ContactDto> getAllContact(String userEmail) {

        // Get User from repo
        Optional<User> optionalUser = userRepository.findByEmail(userEmail);
        User user = new User();
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        }

        // Get contact List
        Set<User> contactList = user.getContacts();

        // Map every contact to its ContactDto
        Collection<ContactDto> dtoCollection = new ArrayList<>();
        for (User contact : contactList) {
            ContactDto temp = new ContactDto(
                    contact.getFirstName(),
                    contact.getLastName(),
                    contact.getEmail());
            dtoCollection.add(temp);
        }

        // return a collection of ContactDto
        return dtoCollection;
    }
}
