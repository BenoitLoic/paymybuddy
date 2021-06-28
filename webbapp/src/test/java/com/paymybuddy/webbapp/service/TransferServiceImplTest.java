package com.paymybuddy.webbapp.service;

import com.paymybuddy.webbapp.entity.User;
import com.paymybuddy.webbapp.exception.UnicornException;
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

    @InjectMocks
    TransferServiceImpl transferService;

    private int amount = 50;
    private int balance = 100;
    private String userEmail = "testEmail";
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
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> transferService.addCash(0, userEmail));

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
}