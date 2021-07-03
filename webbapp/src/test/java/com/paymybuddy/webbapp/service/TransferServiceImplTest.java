package com.paymybuddy.webbapp.service;

import com.paymybuddy.webbapp.dao.TransferDao;
import com.paymybuddy.webbapp.dto.GetTransferDto;
import com.paymybuddy.webbapp.dto.NewTransferDto;
import com.paymybuddy.webbapp.entity.User;
import com.paymybuddy.webbapp.exception.BadArgumentException;
import com.paymybuddy.webbapp.exception.IllegalContactException;
import com.paymybuddy.webbapp.exception.UnicornException;
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

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class TransferServiceImplTest {

    @Mock
    UserRepository userRepositoryMock;
    @Mock
    TransferRepository transferRepositoryMock;
    @Mock
    TransferDao transferDaoMock;

    @InjectMocks
    TransferServiceImpl transferService;

    private int amount = 50;
    private int balance = 100;
    private String userEmail = "testEmail";
    private String debtor = "debtorTest";
    private String creditor = "creditorTest";
    private String descriptionTest = "Test0018574685";
    private User user;

    @BeforeEach
    void setUp() {
        user = new User(userEmail, "userLastName", "userFirstName", "pass");
        user.setBalance(0);
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
        Assertions.assertThat(user.getBalance()).isEqualTo(balance + amount);
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
        org.junit.jupiter.api.Assertions.assertThrows(BadArgumentException.class, () -> transferService.addCash(0, userEmail));

    }

    @Test
    public void addCashWhenFinalBalanceIsOverMax_ShouldThrowAnException() {

        // GIVEN
        // valeur à ajouter 1

        // valeur déjà présente 2147483647
        user.setBalance(Integer.MAX_VALUE);
        // WHEN
        // mock des appels au repo
        Mockito.when(userRepositoryMock.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
        // appel du service

        // THEN
        //  verif que l'exception est bien lancée
        org.junit.jupiter.api.Assertions.assertThrows(UnicornException.class, () -> transferService.addCash(1, userEmail));
    }

    @Test
    void removeCashValid() {

        // GIVEN
        // valeur à soustraire 50

        // valeur déjà présente 100
        user.setBalance(balance);

        // WHEN
        // Mock des appels au repo
        Mockito.when(userRepositoryMock.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
        // appel du service
        transferService.removeCash(amount, userEmail);
        // THEN
        //vérif que result = 100 - 50
        Assertions.assertThat(user.getBalance()).isEqualTo(balance - amount);
    }


    @Test
    public void removeCashWhenBalanceEqualsRemove_ResultBalanceEqual0() {

        // GIVEN
        // valeur à soustraire 100
        amount = balance;
        // valeur déjà présente 100
        user.setBalance(balance);

        // WHEN
        // Mock des appels au repo
        Mockito.when(userRepositoryMock.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
        // appel du service
        transferService.removeCash(amount, userEmail);
        // THEN
        //vérif que result = 100 - 100
        Assertions.assertThat(user.getBalance()).isEqualTo(0);

    }

    @Test
    public void removeCashWhenBalanceIsInferiorToRemove_ShouldThrowAnException() {

        // GIVEN
        // valeur à soustraire 50

        // valeur déjà présente 20
        balance = 20;
        user.setBalance(balance);

        // WHEN
        // Mock des appels au repo
        Mockito.when(userRepositoryMock.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
        // appel du service

        // THEN
        //vérif qu'on jette bien l'exception
        org.junit.jupiter.api.Assertions.assertThrows(UnicornException.class, () -> transferService.removeCash(amount, userEmail));

    }

    // quand tout est OK
    @Test
    public void createTransferValid() {

        // GIVEN
        NewTransferDto newTransfer = new NewTransferDto();
        newTransfer.setDebtorEmail(debtor);
        newTransfer.setCreditorEmail(creditor);
        newTransfer.setAmount(amount);
        newTransfer.setDescription(descriptionTest);

        User user = new User(debtor, "lastNameUser","firstNameUser", "pass");
        user.setBalance(balance);
        user.setId(1);
        User contact = new User(creditor, "lastNameContact","firstNameContact", "pass");
        contact.setBalance(balance);
//        contact.setId(2);
        user.getContacts().add(contact);

        // WHEN
        Mockito.when(userRepositoryMock.findByEmail(debtor)).thenReturn(Optional.of(user));

        //GetTransferDto(String contactName, String description, int amount)
        GetTransferDto expected = new GetTransferDto(contact.getFirstName(), descriptionTest, amount);
        GetTransferDto actual = transferService.createTransfer(newTransfer);

        // THEN
        Assertions.assertThat(user.getBalance()).isEqualTo(balance-amount);
        Assertions.assertThat(contact.getBalance()).isEqualTo(balance+amount);
        Mockito.verify(transferDaoMock,Mockito.times(1)).saveNewTransfer(Mockito.any(),Mockito.any(),Mockito.any());

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

        User user = new User(debtor, "lastNameUser","firstNameUser", "pass");
        user.setBalance(balance);
        User contact = new User(creditor, "lastNameContact","firstNameContact", "pass");


        // WHEN
        Mockito.when(userRepositoryMock.findByEmail(debtor)).thenReturn(Optional.of(user));
        //GetTransferDto(String contactName, String description, int amount)

        // THEN
        org.junit.jupiter.api.Assertions.assertThrows(IllegalContactException.class, ()-> transferService.createTransfer(newTransfer));

    }

    // when debtor have not enough money
    @Test
    void createTransferWhenAmountIsOverDebtorBalance_ShouldThrowException() {

        // GIVEN
        NewTransferDto newTransfer = new NewTransferDto();
        newTransfer.setDebtorEmail(debtor);
        newTransfer.setCreditorEmail(creditor);
        newTransfer.setAmount(balance+500);
        newTransfer.setDescription(descriptionTest);

        User user = new User(debtor, "lastNameUser","firstNameUser", "pass");
        user.setBalance(balance);
        User contact = new User(creditor, "lastNameContact","firstNameContact", "pass");
        user.getContacts().add(contact);

        // WHEN
        Mockito.when(userRepositoryMock.findByEmail(debtor)).thenReturn(Optional.of(user));
        //GetTransferDto(String contactName, String description, int amount)

        // THEN
        org.junit.jupiter.api.Assertions.assertThrows(UnicornException.class, ()-> transferService.createTransfer(newTransfer));
    }
}