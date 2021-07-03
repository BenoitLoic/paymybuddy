package com.paymybuddy.webbapp.dao;

import com.paymybuddy.webbapp.dto.GetTransferDto;
import com.paymybuddy.webbapp.entity.Transfer;
import com.paymybuddy.webbapp.entity.User;

public interface TransferDao {
    /**
     * This method will save a new transfer in transaction table and update both user.
     * @param creditor the user who get the money
     * @param debtor the user who give the money
     * @param transfer the transfer entity
     * @return GetTransferDto( creditorFirstName, description, amount)
     */
    GetTransferDto saveNewTransfer(User creditor, User debtor, Transfer transfer);

}
