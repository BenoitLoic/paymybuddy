package com.paymybuddy.webbapp.service;

import com.paymybuddy.webbapp.entity.User;

import java.util.List;

public interface UserService {
    /**
     * This method save the given user by calling UserDao.
     * @param theUser to save
     */
    public void save(User theUser);

    public void deleteUserById(int theId);

    public List<User> findAll();

    public User findById(int theId);
}
