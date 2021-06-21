package com.paymybuddy.webbapp.controller;

import com.paymybuddy.webbapp.entity.User;
import com.paymybuddy.webbapp.model.UserModel;

import java.util.List;

public interface UserController {




    public String deleteUser(int thId);

    public String updateUser(UserModel user);

    public List<User> findAll();

    public User detUserById(int theId);





}
