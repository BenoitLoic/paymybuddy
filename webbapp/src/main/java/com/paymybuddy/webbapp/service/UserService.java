package com.paymybuddy.webbapp.service;

import com.paymybuddy.webbapp.entity.User;
import com.paymybuddy.webbapp.model.UserModel;

import java.util.List;
import java.util.Optional;

public interface UserService {
    /**
     * This method save the given user by calling the repository.
     *
     * @param theUser to save
     */
    public void save(User theUser);

    /**
     * This method update the user account by calling repository.
     * @param theUser to update
     */
    public void update(UserModel theUser);

    public void deleteUserById(int theId);

    public List<User> findAll();

    public Optional<User> findById(int theId);

    public Optional<User> findByEmail(String email);
}
