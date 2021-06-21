package com.paymybuddy.webbapp.controller;

import com.paymybuddy.webbapp.entity.User;

import java.util.List;

public interface UserController {




    public String deleteUser(int thId);

    public User updateUser(User user);

    public List<User> findAll();

    public User detUserById(int theId);





}
