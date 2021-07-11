package com.paymybuddy.webbapp.controller;


import com.paymybuddy.webbapp.dto.ContactDto;
import com.paymybuddy.webbapp.exception.DataAlreadyExistException;
import com.paymybuddy.webbapp.exception.DataNotFindException;
import com.paymybuddy.webbapp.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * ajout d'un contact à partir d'un email
 * suppression à partir d'un email
 */
@WebMvcTest(controllers = ContactControllerImpl.class)
@AutoConfigureMockMvc(addFilters = false)
public class ContactControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    UserServiceImpl userServiceMock;
    @Mock
    Principal principalMock;
    @InjectMocks
    ContactControllerImpl contactController;

    private final int idTest = 5;
    private final String emailTest = "test@mail.com";
    private final String firstNameTest = "firstName";
    private final String lastNameTest = "lastName";
    private final String createContactUrl = "/newContact";
    private final String deleteContactUrl = "/deleteContact";
    private final String getContactUrl = "/home/contact";

    @Test
    public void createNewContactValid() throws Exception {

        // GIVEN

        // WHEN
        Mockito.when(principalMock.getName()).thenReturn(emailTest);
//Mockito.when(userServiceMock.addNewContact(Mockito.anyString(),Mockito.anyString())).thenReturn("SUCCESS");
        // THEN
        mockMvc.perform(
                MockMvcRequestBuilders.post(createContactUrl)
                        .principal(principalMock)
                        .param("email", emailTest))

                .andExpect(
                        MockMvcResultMatchers.status().isCreated());


    }

    @Test
    public void createNewContactInvalid() throws Exception {

        // GIVEN

        // WHEN
        Mockito.when(principalMock.getName()).thenReturn(emailTest);
        // THEN
        mockMvc.perform(
                MockMvcRequestBuilders.post(createContactUrl)
                        .param("email", " ")
                        .principal(principalMock)
        )
                .andExpect(
                        MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    public void createNewContactWhenContactIdDontExist_ShouldThrowDataNotFindException() throws Exception {

        // GIVEN


        // WHEN
        Mockito.when(principalMock.getName()).thenReturn("email");
        Mockito.doThrow(DataNotFindException.class)
                .when(userServiceMock).addNewContact(Mockito.anyString(), Mockito.anyString());


        // THEN
        mockMvc.perform(
                MockMvcRequestBuilders.post(createContactUrl)
                        .principal(principalMock)
                        .param("email", emailTest))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    @Test
    public void createNewContactWhenContactAlreadyExist_ShouldThrowDataAlreadyExistException() throws Exception {

        // GIVEN

        // WHEN
        Mockito.when(principalMock.getName()).thenReturn("email");
        Mockito.doThrow(DataAlreadyExistException.class)
                .when(userServiceMock).addNewContact(Mockito.anyString(), Mockito.anyString());
        // THEN
        mockMvc.perform(
                MockMvcRequestBuilders.post(createContactUrl)
                        .principal(principalMock)
                        .param("email", emailTest))
                .andExpect(
                        MockMvcResultMatchers.status().isConflict());

    }

    @Test
    public void deleteContactValid() throws Exception {

        // GIVEN

        // WHEN
        Mockito.when(principalMock.getName()).thenReturn("email");

        // THEN
        mockMvc
                .perform(
                        MockMvcRequestBuilders.delete(deleteContactUrl)
                                .param("email", emailTest)
                                .principal(principalMock))
                .andExpect(MockMvcResultMatchers.status().isAccepted());

    }

    @Test
    public void deleteContactInvalid() throws Exception {

        // GIVEN

        // WHEN
        Mockito.when(principalMock.getName()).thenReturn("email");

        // THEN
        mockMvc
                .perform(
                        MockMvcRequestBuilders.delete(deleteContactUrl)
                                .param("id", " ")
                                .principal(principalMock))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    public void deleteContactWhenContactIdDontExist_ShouldThrowDataNotFindException() throws Exception {

        // GIVEN

        // WHEN
        Mockito.when(principalMock.getName()).thenReturn("email");
        Mockito.doThrow(DataNotFindException.class).when(userServiceMock).deleteContact(Mockito.anyString(), Mockito.anyString());
        // THEN
        mockMvc
                .perform(
                        MockMvcRequestBuilders.delete(deleteContactUrl)
                                .param("email", emailTest)
                                .principal(principalMock))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    @Test
    public void getContactValid_ShouldReturnAListOfTwo() throws Exception {

        // GIVEN
        ContactDto contact1 = new ContactDto(firstNameTest, lastNameTest, emailTest);
        ContactDto contact2 = new ContactDto(firstNameTest + 2, lastNameTest + 2, emailTest + 2);
        Collection<ContactDto> collection = Arrays.asList(contact1, contact2);
        // WHEN
        Mockito.when(userServiceMock.getAllContact(Mockito.anyString())).thenReturn(collection);
        Mockito.when(principalMock.getName()).thenReturn("test");
        // THEN
        mockMvc
                .perform(
                        MockMvcRequestBuilders.get(getContactUrl)
                                .principal(principalMock))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void getContactWithNoContact_ShouldReturnAnEmptyList() throws Exception {

        // GIVEN
        Collection<ContactDto> collection = new ArrayList<>();

        // WHEN
        Mockito.when(userServiceMock.getAllContact(Mockito.anyString())).thenReturn(collection);
        Mockito.when(principalMock.getName()).thenReturn("test");
        // THEN
        mockMvc
                .perform(
                        MockMvcRequestBuilders.get(getContactUrl)
                                .principal(principalMock))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
