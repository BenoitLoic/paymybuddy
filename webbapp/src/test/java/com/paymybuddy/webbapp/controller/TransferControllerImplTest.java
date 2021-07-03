package com.paymybuddy.webbapp.controller;

import com.paymybuddy.webbapp.exception.IllegalArgumentException;
import com.paymybuddy.webbapp.exception.UnicornException;
import com.paymybuddy.webbapp.service.TransferServiceImpl;
import com.paymybuddy.webbapp.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.security.Principal;

@WebMvcTest
@AutoConfigureMockMvc(addFilters = false)
public class TransferControllerImplTest {


    @Autowired
    MockMvc mockMvc;
    @MockBean
    UserServiceImpl userServiceMock;
    @MockBean
    TransferServiceImpl transferServiceMock;
    @Mock
    Principal principalMock;

    private final String addCashUrl = "/balance/add";
    private final String removeCashUrl = "/balance/remove";
    private final String debtorTest = "John";
    private final String creditorTest = "Doe";
    private final String descriptionTest = "paiement test";
    private final int amount = 50;
    private final String dateTest = "dataTest";
    private final int balance = 100;
    private final String userEmail = "testEmail";


    @Test
    public void addCashValid() throws Exception {

        // GIVEN

        // WHEN
        Mockito.when(principalMock.getName()).thenReturn(userEmail);
        // THEN

        mockMvc
                .perform(
                        MockMvcRequestBuilders.post("/balance/add?amount=" + amount)
                                .principal(principalMock)
                )
                .andExpect(MockMvcResultMatchers.status().isAccepted());
    }


    @Test
    public void addCashInvalid() throws Exception {

        // GIVEN

        // WHEN

        Mockito.when(principalMock.getName()).thenReturn(userEmail);
        // THEN

        mockMvc
                .perform(
                        MockMvcRequestBuilders.post("/balance/add?amount=" + 0.1)
                                .principal(principalMock))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }


    @Test
    public void addCashWhenAmountEquals0_ShouldThrowIllegalArgumentException() throws Exception {

        // GIVEN

        // WHEN

        Mockito.when(principalMock.getName()).thenReturn(userEmail);
        Mockito.doThrow(IllegalArgumentException.class)
                .when(transferServiceMock)
                .addCash(Mockito.anyInt(), Mockito.anyString());
        // THEN

        mockMvc
                .perform(
                        MockMvcRequestBuilders.post("/balance/add?amount=" + 0)
                                .principal(principalMock))

                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }


    @Test
    public void addCashWhenFinalBalanceIsOverMax_ShouldThrowAnException() throws Exception {

        // GIVEN

        // WHEN

        Mockito.when(principalMock.getName()).thenReturn(userEmail);
        Mockito.doThrow(UnicornException.class)
                .when(transferServiceMock)
                .addCash(Mockito.anyInt(), Mockito.anyString());
        // THEN

        mockMvc
                .perform(
                        MockMvcRequestBuilders.post("/balance/add?amount=" + 50)
                                .principal(principalMock))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void removeCashValid() throws Exception {

        // GIVEN

        // WHEN
        Mockito.when(principalMock.getName()).thenReturn(userEmail);
        Mockito.when(transferServiceMock.addCash(Mockito.anyInt(), Mockito.anyString())).thenReturn(true);
        // THEN
        mockMvc
                .perform(
                        MockMvcRequestBuilders.post("/balance/remove?amount=" + amount)
                                .principal(principalMock))
                .andExpect(MockMvcResultMatchers.status().isAccepted());

    }


    @Test
    public void removeCashInvalid() throws Exception {

        // GIVEN

        // WHEN
        Mockito.when(principalMock.getName()).thenReturn(userEmail);
        // THEN
        mockMvc
                .perform(
                        MockMvcRequestBuilders.post("/balance/add?amount=" + 0.1)
                                .principal(principalMock))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }


    @Test
    public void removeCashWhenAmountIsOverBalance_ShouldThrowException() throws Exception {

        // GIVEN

        // WHEN
        Mockito.when(principalMock.getName()).thenReturn(userEmail);
        Mockito.doThrow(UnicornException.class).when(transferServiceMock).addCash(Mockito.anyInt(), Mockito.anyString());
        // THEN
        mockMvc
                .perform(
                        MockMvcRequestBuilders.post("/balance/add?amount=" + amount)
                                .principal(principalMock))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }


}
