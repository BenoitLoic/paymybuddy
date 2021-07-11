package com.paymybuddy.webbapp.controller;

import com.paymybuddy.webbapp.dto.NewTransferDto;
import com.paymybuddy.webbapp.exception.BadArgumentException;
import com.paymybuddy.webbapp.exception.IllegalContactException;
import com.paymybuddy.webbapp.exception.InvalidBalanceException;
import com.paymybuddy.webbapp.service.TransferServiceImpl;
import com.paymybuddy.webbapp.service.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.security.Principal;
import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TransferControllerImpl.class)
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

    private final String addCashUrl = "/home/balance/add";
    private final String removeCashUrl = "/home/balance/remove";
    private final String createTransferUrl = "/home/transfer";
    private final String getTransferUrl = "/home/transfer";
    private final String debtorTest = "John";
    private final String creditorTest = "Doe";
    private final String descriptionTest = "test payment";
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
                        post(addCashUrl + "?amount=" + amount)
                                .principal(principalMock)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                )
                .andExpect(status().isAccepted());
    }


    @Test
    public void addCashInvalid() throws Exception {

        // GIVEN

        // WHEN

        Mockito.when(principalMock.getName()).thenReturn(userEmail);
        // THEN

        mockMvc
                .perform(
                        post(addCashUrl + "?amount=" + 0.1)
                                .principal(principalMock).contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                )
                .andExpect(status().isBadRequest());

    }


    @Test
    public void addCashWhenAmountEquals0_ShouldThrowIllegalArgumentException() throws Exception {

        // GIVEN

        // WHEN

        Mockito.when(principalMock.getName()).thenReturn(userEmail);
        Mockito.doThrow(BadArgumentException.class)
                .when(transferServiceMock)
                .addCash(Mockito.anyInt(), Mockito.anyString());
        // THEN

        mockMvc
                .perform(
                        post(addCashUrl + "?amount=" + 0)
                                .principal(principalMock)
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))

                .andExpect(status().isBadRequest());

    }


    @Test
    public void addCashWhenFinalBalanceIsOverMax_ShouldThrowAnException() throws Exception {

        // GIVEN

        // WHEN

        Mockito.when(principalMock.getName()).thenReturn(userEmail);
        Mockito.doThrow(InvalidBalanceException.class)
                .when(transferServiceMock)
                .addCash(Mockito.anyInt(), Mockito.anyString());
        // THEN

        mockMvc
                .perform(
                        post(addCashUrl + "?amount=" + 50)
                                .principal(principalMock)
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void removeCashValid() throws Exception {

        // GIVEN

        // WHEN
        Mockito.when(principalMock.getName()).thenReturn(userEmail);
        Mockito.doNothing().when(transferServiceMock).removeCash(Mockito.anyInt(), Mockito.anyString());
        // THEN
        mockMvc
                .perform(
                        post(removeCashUrl + "?amount=" + amount)
                                .principal(principalMock)
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isAccepted());

    }


    @Test
    public void removeCashInvalid() throws Exception {

        // GIVEN

        // WHEN
        Mockito.when(principalMock.getName()).thenReturn(userEmail);
        Mockito.doThrow(BadArgumentException.class).when(transferServiceMock).removeCash(Mockito.anyDouble(), Mockito.anyString());
        // THEN
        mockMvc
                .perform(
                        post(removeCashUrl + "?amount=" + -0.1)
                                .principal(principalMock)
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isBadRequest());

    }


    @Test
    public void removeCashWhenAmountIsOverBalance_ShouldThrowException() throws Exception {

        // GIVEN

        // WHEN
        Mockito.when(principalMock.getName()).thenReturn(userEmail);
        Mockito.doThrow(InvalidBalanceException.class).when(transferServiceMock).removeCash(Mockito.anyDouble(), Mockito.anyString());
        // THEN
        mockMvc
                .perform(
                        post(removeCashUrl + "?amount=" + amount)
                                .principal(principalMock)
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void createTransferValid() throws Exception {

        // GIVEN
        //NewTransferDto(String creditorEmail, int amount, String description)
        NewTransferDto transferDto = new NewTransferDto(creditorTest, amount, descriptionTest);

        String urlEncoded = "creditorEmail=" + transferDto.getCreditorEmail()
                + "&amount=" + transferDto.getAmount()
                + "&description=" + transferDto.getDescription();


        // WHEN
        Mockito.when(principalMock.getName()).thenReturn(userEmail);
        // THEN
        mockMvc
                .perform(
                        post(createTransferUrl)
                                .principal(principalMock)
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                                .content(urlEncoded))
                .andExpect(status().isCreated());

    }

    @Test
    public void createTransferInvalidEmail() throws Exception {

        // GIVEN
        //NewTransferDto(String creditorEmail, int amount, String description)
        NewTransferDto transferDto = new NewTransferDto("", amount, descriptionTest);

        String urlEncoded = "creditorEmail=" + transferDto.getCreditorEmail()
                + "&amount=" + transferDto.getAmount()
                + "&description=" + transferDto.getDescription();

        // WHEN
        Mockito.when(principalMock.getName()).thenReturn(userEmail);
        // THEN
        mockMvc
                .perform(
                        post(createTransferUrl)
                                .principal(principalMock)
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                                .content(urlEncoded))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void createTransferInvalidAmount() throws Exception {
        // GIVEN

        NewTransferDto transferDto = new NewTransferDto(creditorTest, -0.01, descriptionTest);

        String urlEncoded = "creditorEmail=" + transferDto.getCreditorEmail()
                + "&amount=" + transferDto.getAmount()
                + "&description=" + transferDto.getDescription();

        // WHEN
        Mockito.when(principalMock.getName()).thenReturn(userEmail);
        // THEN
        mockMvc
                .perform(
                        post(createTransferUrl)
                                .principal(principalMock)
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                                .content(urlEncoded))
                .andExpect(status().isBadRequest());


    }

    @Test
    public void createTransferInvalidPrincipal() throws Exception {
        // GIVEN
        NewTransferDto transferDto = new NewTransferDto(creditorTest, amount, descriptionTest);

        String urlEncoded = "creditorEmail=" + transferDto.getCreditorEmail()
                + "&amount=" + transferDto.getAmount()
                + "&description=" + transferDto.getDescription();

        // WHEN
        Mockito.when(principalMock.getName()).thenReturn(null);
        // THEN
        mockMvc
                .perform(
                        post(createTransferUrl)
                                .principal(principalMock)
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                                .content(urlEncoded))
                .andExpect(status().isForbidden())
                .andExpect(
                        mvcResult -> Assertions.assertEquals(
                                "KO - User must be authenticated",
                                Objects.requireNonNull(mvcResult.getResolvedException()).getMessage()));

    }

    @Test
    public void createTransferWhenContactIsInvalid_ShouldThrowIllegalContactException() throws Exception {

        // GIVEN
        NewTransferDto transferDto = new NewTransferDto(creditorTest, amount, descriptionTest);

        String urlEncoded = "creditorEmail=" + transferDto.getCreditorEmail()
                + "&amount=" + transferDto.getAmount()
                + "&description=" + transferDto.getDescription();

        // WHEN
        Mockito.when(principalMock.getName()).thenReturn(userEmail);
        Mockito.doThrow(IllegalContactException.class).when(transferServiceMock).createTransfer(Mockito.any());
        // THEN
        mockMvc
                .perform(
                        post(createTransferUrl)
                                .principal(principalMock)
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                                .content(urlEncoded))
                .andExpect(status().isConflict());
    }

    @Test
    public void createTransferWhenAmountVerificationFail_ShouldThrowException() throws Exception {

        // GIVEN
        NewTransferDto transferDto = new NewTransferDto(creditorTest, amount, descriptionTest);

        String urlEncoded = "creditorEmail=" + transferDto.getCreditorEmail()
                + "&amount=" + transferDto.getAmount()
                + "&description=" + transferDto.getDescription();

        // WHEN
        Mockito.when(principalMock.getName()).thenReturn(userEmail);
        Mockito.doThrow(InvalidBalanceException.class).when(transferServiceMock).createTransfer(Mockito.any());
        // THEN
        mockMvc
                .perform(
                        post(createTransferUrl)
                                .principal(principalMock)
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                                .content(urlEncoded))
                .andExpect(status().isBadRequest());

    }
}
