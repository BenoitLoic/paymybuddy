package com.paymybuddy.webbapp.service;

import org.springframework.stereotype.Service;

/**
 * Interface for transfer service.
 * Contain some method to add/remove currency from user balance.
 */
@Service
public interface TransferService {
    /**
     * This method will add some cash to User balance.
     *
     * @param amount the integer to add
     * @param email  the email of the current user
     * @return true if success
     */
    boolean addCash(int amount, String email);

    /**
     * This method will remove some cash to User balance.
     *
     * @param amount the integer to subtract
     * @param email  the email of the current user
     * @return true if success
     */
    boolean removeCash(int amount, String email);


}
