package com.paymybuddy.webbapp.controller;

import com.paymybuddy.webbapp.entity.User;
import com.paymybuddy.webbapp.model.UserModel;

import java.util.List;

public interface UserController {


    String deleteUser(int thId);

    String updateUser(UserModel user);

    List<User> findAll();


}
