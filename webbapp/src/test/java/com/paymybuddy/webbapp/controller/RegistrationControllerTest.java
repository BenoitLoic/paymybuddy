package com.paymybuddy.webbapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.paymybuddy.webbapp.dto.NewUserDto;
import com.paymybuddy.webbapp.exception.DataAlreadyExistException;
import com.paymybuddy.webbapp.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@WebMvcTest(controllers = RegistrationControllerImpl.class)
@AutoConfigureMockMvc(addFilters = false)
public class RegistrationControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    UserServiceImpl userServiceMock;
    @InjectMocks
    UserControllerImpl userController;


    private final int id = 1;
    private final String email = "testmail";
    private final String lastName = "John";
    private final String firstName = "Doe";
    private final String password = "testpassword";
    private final int balance = 0;
    private String phone;
    private String addressPrefix;
    private String addressNumber;
    private String addressStreet;
    private String zip;
    private String city;

    public NewUserDto getUserTest() {

        NewUserDto user = new NewUserDto();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }


    @Test
    public void createUserValid() throws Exception {
        //GIVEN
        ObjectMapper obm = new ObjectMapper();
        String json = obm.writeValueAsString(getUserTest());

        //WHEN

        //THEN
        mockMvc.perform(
                MockMvcRequestBuilders.post("/registration/createNewUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated());

    }

    @Test
    public void createUserInvalid() throws Exception {

        //GIVEN
        ObjectMapper obm = new ObjectMapper();
        ObjectNode jsonNodes = obm.createObjectNode();
        jsonNodes.set("firstName", TextNode.valueOf(firstName));
        jsonNodes.set("lastName", TextNode.valueOf(lastName));
        jsonNodes.set("email", TextNode.valueOf(email));
        jsonNodes.set("password", TextNode.valueOf(" "));

        //WHEN

        //THEN
        mockMvc.perform(
                MockMvcRequestBuilders.post("/registration/createNewUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonNodes.toString()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    public void createUserWhen_UserAlreadyExist() throws Exception {

//        GIVEN
        ObjectMapper obm = new ObjectMapper();
        ObjectNode jsonNodes = obm.createObjectNode();
        jsonNodes.set("firstName", TextNode.valueOf(firstName));
        jsonNodes.set("lastName", TextNode.valueOf(lastName));
        jsonNodes.set("email", TextNode.valueOf(email));
        jsonNodes.set("password", TextNode.valueOf(password));

//        WHEN
        Mockito.doThrow(DataAlreadyExistException.class)
                .when(userServiceMock)
                .save(Mockito.any());

//        THEN

        mockMvc.perform(
                MockMvcRequestBuilders.post("/registration/createNewUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonNodes.toString()))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

}
