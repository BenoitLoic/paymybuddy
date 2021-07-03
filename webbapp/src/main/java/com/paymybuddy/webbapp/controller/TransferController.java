package com.paymybuddy.webbapp.controller;

import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

public interface TransferController {
    /**
     * this method will add cash to the current user balance.
     *
     * @param theAmount of money to add
     * @param principal the current user
     * @return should redirect to user's home page
     */
    RedirectView addCash(int theAmount, Principal principal);

    /**
     * this method will remove cash from the current user balance.
     *
     * @param theAmount of money to remove
     * @param principal the current user
     * @return should redirect to user's home page
     */
    RedirectView removeCash(int theAmount, Principal principal);
}
