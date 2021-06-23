package com.paymybuddy.webbapp.controller;


import com.paymybuddy.webbapp.model.UserModel;

public interface LoginController {


   String welcomePage();

   String showLoginPage();

   String  testUserAccount(UserModel theUser);
}
