package com.paymybuddy.webbapp.controller;

import com.paymybuddy.webbapp.dto.GetTransferDto;
import com.paymybuddy.webbapp.dto.NewTransferDto;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.List;

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

    /**
     * This method will create a new transfer for the current user and the given contact.
     *
     * @param transfer Dto with creditorEmail, amount, description
     * @param principal the current user (debtor)
     * @return
     */
    GetTransferDto createTransfer(NewTransferDto transfer, Principal principal);

    /**
     * This method will get all the transfer of the current user.
     * @param principal the current user
     * @return the view for transfer
     */
    List<GetTransferDto> getTransfers(Principal principal);

}
