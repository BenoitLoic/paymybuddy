package com.paymybuddy.webbapp.controller;

import com.paymybuddy.webbapp.entity.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionControllerImpl implements TransactionController {


    @Override
    @PostMapping("/transaction")
    @ResponseStatus(HttpStatus.CREATED)
    public void createTransaction(Transaction transaction) {

    }

    @Override
    public void deleteTransaction(int id) {

    }

    @Override
    public void updateTransaction(Transaction transaction) {

    }
}
