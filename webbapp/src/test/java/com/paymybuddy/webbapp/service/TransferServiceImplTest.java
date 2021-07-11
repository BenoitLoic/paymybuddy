package com.paymybuddy.webbapp.service;

import com.paymybuddy.webbapp.dto.GetTransferDto;
import com.paymybuddy.webbapp.dto.NewTransferDto;
import com.paymybuddy.webbapp.entity.User;
import com.paymybuddy.webbapp.exception.BadArgumentException;
import com.paymybuddy.webbapp.exception.IllegalContactException;
import com.paymybuddy.webbapp.exception.InvalidBalanceException;
import com.paymybuddy.webbapp.repository.TransferRepository;
import com.paymybuddy.webbapp.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class TransferServiceImplTest {

  @Mock UserRepository userRepositoryMock;
  @Mock TransferRepository transferRepositoryMock;

  @InjectMocks TransferServiceImpl transferService;

  private int amount = 50;
  private BigDecimal balance = BigDecimal.valueOf(100);
  private final String userEmail = "testEmail";
  private final String debtor = "debtorTest";
  private final String creditor = "creditorTest";
  private final String descriptionTest = "Test0018574685";
  private User user;

  @BeforeEach
  void setUp() {
    user = new User(userEmail, "userLastName", "userFirstName", "pass");
    user.setBalance(BigDecimal.ZERO);
  }

  @Test
  void addCashValid() {

    // GIVEN
    // valeur à ajouter = 50
    // valeur déjà présente = 100
    user.setBalance(balance);
    // WHEN
    // mock des appels au repo
    Mockito.when(userRepositoryMock.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));

    // appel du service
    transferService.addCash(amount, userEmail);
    // THEN
    //  verif que résult = add + init
    Assertions.assertThat(user.getBalance()).isEqualTo(balance.add(BigDecimal.valueOf(amount)));
  }

  @Test
  public void addCashWhenAdd0_ShouldThrowIllegalArgumentException() {
    // GIVEN
    // valeur à ajouter == 0

    // valeur déjà présente
    user.setBalance(balance);
    // WHEN
    // mock des appels au repo

    // appel du service

    // THEN
    //  verif que l'exception est bien lancée
    org.junit.jupiter.api.Assertions.assertThrows(
        BadArgumentException.class, () -> transferService.addCash(0, userEmail));
  }

  @Test
  void removeCashValid() {

    // GIVEN
    // valeur à soustraire 50
    double amountD = 50.5;
    // valeur déjà présente 100
    user.setBalance(balance);

    // WHEN
    // Mock des appels au repo
    Mockito.when(userRepositoryMock.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
    // appel du service
    transferService.removeCash(amountD, userEmail);
    // THEN
    // vérif que result = 100 - 50
    Assertions.assertThat(user.getBalance())
        .isEqualTo(balance.subtract(BigDecimal.valueOf(amountD)));
  }

  @Test
  public void removeCashWhenBalanceEqualsRemove_ResultBalanceEqual0() {

    // GIVEN
    // valeur à soustraire 100
    amount = balance.intValue();
    // valeur déjà présente 100
    user.setBalance(balance);

    // WHEN
    // Mock des appels au repo
    Mockito.when(userRepositoryMock.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
    // appel du service
    transferService.removeCash(amount, userEmail);
    // THEN
    // vérif que result = 100 - 100
    Assertions.assertThat(user.getBalance().signum()).isEqualTo(0);
  }

  @Test
  public void removeCashWhenBalanceIsInferiorToRemove_ShouldThrowAnException() {

    // GIVEN
    // valeur à soustraire 50

    // valeur déjà présente 20

    user.setBalance(BigDecimal.valueOf(20));

    // WHEN
    // Mock des appels au repo
    Mockito.when(userRepositoryMock.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
    // appel du service

    // THEN
    // vérif qu'on jette bien l'exception
    org.junit.jupiter.api.Assertions.assertThrows(
        InvalidBalanceException.class, () -> transferService.removeCash(amount, userEmail));
  }

  // quand tout est OK
  @Test
  public void createTransferValid() {

    // GIVEN
    double amountD = 50.5;

    NewTransferDto newTransfer = new NewTransferDto();
    newTransfer.setDebtorEmail(debtor);
    newTransfer.setCreditorEmail(creditor);
    newTransfer.setAmount(amountD);
    newTransfer.setDescription(descriptionTest);

    User user = new User(debtor, "lastNameUser", "firstNameUser", "pass");
    user.setBalance(balance);
    user.setId(1);
    User contact = new User(creditor, "lastNameContact", "firstNameContact", "pass");
    contact.setBalance(balance);
    //        contact.setId(2);
    user.getContacts().add(contact);

    // WHEN
    Mockito.when(userRepositoryMock.findByEmail(debtor)).thenReturn(Optional.of(user));

    // GetTransferDto(String contactName, String description, int amount)
    GetTransferDto expected =
        new GetTransferDto(contact.getFirstName(), descriptionTest, BigDecimal.valueOf(amountD));
    GetTransferDto actual = transferService.createTransfer(newTransfer);

    // THEN
    Assertions.assertThat(user.getBalance())
        .isEqualTo(balance.subtract(BigDecimal.valueOf(amountD)));
    Assertions.assertThat(contact.getBalance()).isEqualTo(balance.add(BigDecimal.valueOf(amountD)));
    Assertions.assertThat(actual).isEqualTo(expected);
  }

  // when contactEmail is not part of user's contacts
  @Test
  void createTransferWhenCreditorIsNotAContact_ShouldThrowIllegalContactException() {

    // GIVEN
    NewTransferDto newTransfer = new NewTransferDto();
    newTransfer.setDebtorEmail(debtor);
    newTransfer.setCreditorEmail(creditor);
    newTransfer.setAmount(amount);
    newTransfer.setDescription(descriptionTest);

    User user = new User(debtor, "lastNameUser", "firstNameUser", "pass");
    user.setBalance(balance);
    User contact = new User(creditor, "lastNameContact", "firstNameContact", "pass");

    // WHEN
    Mockito.when(userRepositoryMock.findByEmail(debtor)).thenReturn(Optional.of(user));
    // GetTransferDto(String contactName, String description, int amount)

    // THEN
    org.junit.jupiter.api.Assertions.assertThrows(
        IllegalContactException.class, () -> transferService.createTransfer(newTransfer));
  }

  // when debtor have not enough money
  @Test
  void createTransferWhenAmountIsOverDebtorBalance_ShouldThrowException() {

    // GIVEN
    NewTransferDto newTransfer = new NewTransferDto();
    newTransfer.setDebtorEmail(debtor);
    newTransfer.setCreditorEmail(creditor);
    newTransfer.setAmount(balance.intValue() + 500);
    newTransfer.setDescription(descriptionTest);

    User user = new User(debtor, "lastNameUser", "firstNameUser", "pass");
    user.setBalance(balance);
    User contact = new User(creditor, "lastNameContact", "firstNameContact", "pass");
    user.getContacts().add(contact);

    // WHEN
    Mockito.when(userRepositoryMock.findByEmail(debtor)).thenReturn(Optional.of(user));
    // GetTransferDto(String contactName, String description, int amount)

    // THEN
    org.junit.jupiter.api.Assertions.assertThrows(
        InvalidBalanceException.class, () -> transferService.createTransfer(newTransfer));
  }
}