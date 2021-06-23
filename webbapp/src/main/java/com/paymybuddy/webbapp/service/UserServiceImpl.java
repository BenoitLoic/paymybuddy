package com.paymybuddy.webbapp.service;

import com.paymybuddy.webbapp.entity.User;
import com.paymybuddy.webbapp.exception.DataNotFindException;
import com.paymybuddy.webbapp.model.UserModel;
import com.paymybuddy.webbapp.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
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
    public void save(User theUser) {

        // Hash password using BCrypt
        if (theUser.getPassword() != null) {
            theUser.setPassword(bCryptPasswordEncoder.encode(theUser.getPassword()));
        }
        // Save user using JpaRepository.save
        userRepository.save(theUser);
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


    @Override
    public void deleteUserById(int theId) {

        // Check if user exist
        if (!userRepository.existsById(theId)) {
            throw new DataNotFindException("KO - can't find user with id: " + theId);
        }
        // Delete user
        userRepository.deleteById(theId);

    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(int theId) {
        return userRepository.findById(theId);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
