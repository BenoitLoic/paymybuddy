package com.paymybuddy.webbapp.controller;


import com.paymybuddy.webbapp.entity.Transaction;

public interface TransactionController {

    void createTransaction(Transaction transaction);
    void deleteTransaction(int id);
    void updateTransaction(Transaction transaction);

}
