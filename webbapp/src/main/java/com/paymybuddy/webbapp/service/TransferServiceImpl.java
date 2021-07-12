package com.paymybuddy.webbapp.service;

import com.paymybuddy.webbapp.dto.GetTransferDto;
import com.paymybuddy.webbapp.dto.NewTransferDto;
import com.paymybuddy.webbapp.entity.Transfer;
import com.paymybuddy.webbapp.entity.User;
import com.paymybuddy.webbapp.exception.BadArgumentException;
import com.paymybuddy.webbapp.exception.DataNotFindException;
import com.paymybuddy.webbapp.exception.IllegalContactException;
import com.paymybuddy.webbapp.exception.InvalidBalanceException;
import com.paymybuddy.webbapp.repository.TransferRepository;
import com.paymybuddy.webbapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of transferService. Contain some method to add/remove currency from user balance.
 * Contain some method to create/get transfer.
 */
@Service
public class TransferServiceImpl implements TransferService {

  private final UserRepository userRepository;
  private final TransferRepository transferRepository;

  @Autowired
  public TransferServiceImpl(UserRepository userRepository, TransferRepository transferRepository) {
    this.userRepository = userRepository;
    this.transferRepository = transferRepository;
  }

  /**
   * This method will add some cash to User balance.
   *
   * @param theAmount the integer to add
   */
  @Override
  public void addCash(int theAmount, String email) {

    // Check amount > 0
    if (theAmount < 1) {
      throw new BadArgumentException("KO - Amount must be > 0.");
    }
    // Get User
    Optional<User> userOptional = userRepository.findByEmail(email);

    if (userOptional.isEmpty()) {
      throw new DataNotFindException("KO - Can't find user: " + email);
    }
    System.out.println(theAmount);
    BigDecimal amount = BigDecimal.valueOf(theAmount).setScale(3, RoundingMode.HALF_DOWN);
    System.out.println(amount);
    User user = userOptional.get();

    // Add Amount
    BigDecimal userBalance  = user.getBalance();
    System.out.println("maount : " + amount + " balance : " + userBalance);
    user.setBalance(amount.add(userBalance));
    System.out.println("userBalance : "+userBalance);
    System.out.println("final user balance : " +user.getBalance());
    // save
    userRepository.save(user);
  }

  /**
   * This method will remove some cash to User balance.
   *
   * @param theAmount the integer to subtract
   */
  @Override
  public void removeCash(String theAmount, String email) {
    // Check theAmount > 0
    BigDecimal amount = new BigDecimal(theAmount).setScale(3,RoundingMode.HALF_DOWN);

    if (amount.signum() <= 0) {
      throw new BadArgumentException("KO - Amount must be > 0.");
    }
    // Get User
    Optional<User> userOptional = userRepository.findByEmail(email);
    if (userOptional.isEmpty()) {
      throw new DataNotFindException("KO - Can't find user: " + email);
    }
    // Check User Balance
    User user = userOptional.get();
    BigDecimal userBalance = user.getBalance().setScale(3,RoundingMode.HALF_DOWN);
    BigDecimal checkResult = userBalance.subtract(amount);
    // Add Amount and Check if initialBalance + theAmount < Integer.MAX
    if (checkResult.signum() >= 0) {
      user.setBalance(checkResult.setScale(3,RoundingMode.HALF_DOWN));

      // save
      userRepository.save(user);
    } else {
      throw new InvalidBalanceException("Error - Balance can't be negative.");
    }
  }

  /**
   * This method will create a new Transfer between two user
   *
   * @param newTransferDto dto with creditorEmail, debtorEmail, amount and description
   */
  @Override
  @Transactional
  public GetTransferDto createTransfer(NewTransferDto newTransferDto) {

    // get user (debtor)
    Optional<User> opUser = userRepository.findByEmail(newTransferDto.getDebtorEmail());
    User debtor = new User();
    if (opUser.isPresent()) {
      debtor = opUser.get();
    }

    BigDecimal amount = BigDecimal.valueOf(newTransferDto.getAmount());

    // check user balance
    if (debtor.getBalance().compareTo(amount) < 0) {
      throw new InvalidBalanceException("Error - Amount > balance for user : " + debtor.getEmail());
    }

    // check if creditor is part of contacts
    User creditor = new User();
    for (User contact : debtor.getContacts()) {
      if (contact.getEmail().equals(newTransferDto.getCreditorEmail())) {
        creditor = contact;
      }
    }

    if (creditor.getEmail() == null) {
      throw new IllegalContactException(
          "Error - User: " + newTransferDto.getCreditorEmail() + " in contacts.");
    }

    // add amount to contact
    creditor.setBalance(creditor.getBalance().add(amount));

    // remove amount from user
    debtor.setBalance(debtor.getBalance().subtract(amount));

    // create entity Transfer
    Transfer transfer = new Transfer();
    transfer.setCreditorId(creditor.getId());
    transfer.setDebtorId(debtor.getId());
    transfer.setAmount(amount);
    transfer.setDescription(newTransferDto.getDescription());

    // save in repository
    userRepository.save(creditor);
    userRepository.save(debtor);
    transferRepository.save(transfer);

    return new GetTransferDto(
        creditor.getFirstName(), transfer.getDescription(), transfer.getAmount());
  }

  /**
   * This method will get all transfer for the given user
   *
   * @param userEmail the email of the current user.
   */
  @Override
  public List<GetTransferDto> getTransfers(String userEmail) {

    Optional<User> userOp = userRepository.findByEmail(userEmail);

    int userId = 0;
    if (userOp.isPresent()) {
      userId = userOp.get().getId();
    }

    List<GetTransferDto> transactions = new ArrayList<>();

    for (Transfer transfer : transferRepository.findAllByCreditorId(userId)) {
      GetTransferDto temp =
          new GetTransferDto(
              transfer.getDebtor().getFirstName(), transfer.getDescription(), transfer.getAmount());
      transactions.add(temp);
    }

    for (Transfer transfer : transferRepository.findAllByDebtorId(userId)) {
      GetTransferDto temp =
          new GetTransferDto(
              transfer.getCreditor().getFirstName(),
              transfer.getDescription(),
              transfer.getAmount().negate());
      transactions.add(temp);
    }

    return transactions;
  }
}
