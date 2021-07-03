package com.paymybuddy.webbapp.service;

import com.paymybuddy.webbapp.dao.TransferDao;
import com.paymybuddy.webbapp.dto.GetTransferDto;
import com.paymybuddy.webbapp.dto.NewTransferDto;
import com.paymybuddy.webbapp.entity.Transfer;
import com.paymybuddy.webbapp.entity.User;
import com.paymybuddy.webbapp.exception.BadArgumentException;
import com.paymybuddy.webbapp.exception.DataNotFindException;
import com.paymybuddy.webbapp.exception.IllegalContactException;
import com.paymybuddy.webbapp.exception.UnicornException;
import com.paymybuddy.webbapp.repository.TransferRepository;
import com.paymybuddy.webbapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TransferServiceImpl implements TransferService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    TransferRepository transferRepository;
    @Autowired
    TransferDao transferDao;

    /**
     * This method will add some cash to User balance.
     *
     * @param amount the integer to add
     * @return true if success
     */
    @Override
    public boolean addCash(int amount, String email) {

        // Check amount > 0
        if (amount < 1) {
            throw new BadArgumentException("KO - Amount must be > 0.");
        }
        // Get User
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new DataNotFindException("KO - Can't find user: " + email);
        }
        // Check User Balance
        User user = userOptional.get();
        long checkResult = (long) amount + (long) user.getBalance();
        // Add Amount and Check if initialBalance + amount < Integer.MAX
        if (checkResult <= Integer.MAX_VALUE) {
            user.setBalance(amount + user.getBalance());
            userRepository.save(user);
            // save
            return true;
        } else {
            throw new UnicornException("Error - Balance over max value.");
        }


    }

    /**
     * This method will remove some cash to User balance.
     *
     * @param amount the integer to subtract
     * @return true if success
     */
    @Override
    public boolean removeCash(int amount, String email) {
        // Check amount > 0
        if (amount < 1) {
            throw new BadArgumentException("KO - Amount must be > 0.");
        }
        // Get User
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new DataNotFindException("KO - Can't find user: " + email);
        }
        // Check User Balance
        User user = userOptional.get();
        long checkResult = (long) user.getBalance() - (long) amount;
        // Add Amount and Check if initialBalance + amount < Integer.MAX
        if (checkResult >= 0) {
            user.setBalance(user.getBalance() - amount);
            userRepository.save(user);
            // save
            return true;
        } else {
            throw new UnicornException("Error - Balance over max value.");
        }

    }

    /**
     * This method will create a new Transfer between two user
     *
     * @param newTransferDto dto with creditorEmail, debtorEmail, amount and description
     */
    @Override
    public GetTransferDto createTransfer(NewTransferDto newTransferDto) {

        // get user (debtor)
        Optional<User> opUser = userRepository.findByEmail(newTransferDto.getDebtorEmail());
        User debtor = new User();
        if (opUser.isPresent()) {
            debtor = opUser.get();
        }
        // check user balance
        if (debtor.getBalance() < newTransferDto.getAmount()) {
            throw new UnicornException("Error - Amount > balance for user : " + debtor.getEmail());
        }
        // check if creditor is part of contacts
        User creditor = new User();
        for (User contact : debtor.getContacts()) {
            if (contact.getEmail().equals(newTransferDto.getCreditorEmail())) {
                creditor = contact;
            }
        }

        if (creditor.getEmail() == null) {
            throw new IllegalContactException("Error - User: " + newTransferDto.getCreditorEmail() + " in contacts.");
        }

        // add amount to contact
        creditor.setBalance(creditor.getBalance() + newTransferDto.getAmount());
        // remove amount from user
        debtor.setBalance(debtor.getBalance() - newTransferDto.getAmount());
        // create entity Transfer
        Transfer transfer = new Transfer();
        transfer.setCreditorId(creditor.getId());
        transfer.setDebtorId(debtor.getId());
        transfer.setAmount(newTransferDto.getAmount());
        transfer.setDescription(newTransferDto.getDescription());
        // save in repository

        return transferDao.saveNewTransfer(creditor, debtor, transfer);
    }

    /**
     * This method will get all transfer for the given user
     *
     * @param userEmail the email of the current user.
     */
    @Override
    public List<GetTransferDto> getTransfers(String userEmail) {

        User user = null;
        Optional<User> userOp = userRepository.findByEmail(userEmail);


        if (userOp.isPresent()) {
            user = userOp.get();
        }
        System.out.println(user);
        System.out.println("friend : " + user.getContacts());
        System.out.println("as creditor : "+user.getTransfersAsCreditor());
        System.out.println("as debtor : "+user.getTransfersAsDebtor());
        List<GetTransferDto> transactions = new ArrayList<>();

        for (Transfer transfer : user.getTransfersAsDebtor()) {
            //String contactName, String description, int amount
            GetTransferDto temp = new GetTransferDto(transfer.getCreditor().getFirstName(),transfer.getDescription(), transfer.getAmount());
            transactions.add(temp);
            System.out.println(temp);
        }

        for (Transfer transfer : user.getTransfersAsCreditor()) {
            GetTransferDto temp = new GetTransferDto(transfer.getDebtor().getFirstName(),transfer.getDescription(), transfer.getAmount());
            transactions.add(temp);
            System.out.println(temp);

        }

        return transactions;
    }
}
