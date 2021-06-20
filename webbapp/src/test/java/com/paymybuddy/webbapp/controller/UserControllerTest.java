package com.paymybuddy.webbapp.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.paymybuddy.webbapp.entity.User;
import com.paymybuddy.webbapp.exception.DataAlreadyExistException;
import com.paymybuddy.webbapp.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = UserControllerImpl.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    UserServiceImpl userServiceMock;

    private int id = 1;
    private String email = "testmail";
    private String lastName = "John";
    private String firstName = "Doe";
    private String password = "testpassword";
    private int balance = 0;
    private String phone;
    private String addressPrefix;
    private String addressNumber;
    private String addressStreet;
    private String zip;
    private String city;

    public User getUserTest() {

        User user = new User();
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
        ObjectNode jsonNodes = obm.createObjectNode();
        jsonNodes.set("firstName", TextNode.valueOf(firstName));
        jsonNodes.set("lastName", TextNode.valueOf(lastName));
        jsonNodes.set("email", TextNode.valueOf(email));
        jsonNodes.set("password", TextNode.valueOf(password));

        //WHEN

        //THEN
        mockMvc.perform(
                MockMvcRequestBuilders.post("/V1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonNodes.toString()))
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
                MockMvcRequestBuilders.post("/V1/user")
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
                MockMvcRequestBuilders.post("/V1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonNodes.toString()))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    public void updateUserValid() throws Exception {

        //        GIVEN
        // création du Json en param
        ObjectMapper obm = new ObjectMapper();
        ObjectNode objectNode = obm.createObjectNode();
        objectNode.set("id", TextNode.valueOf(String.valueOf(id)));
        objectNode.set("firstName", TextNode.valueOf(firstName));
        objectNode.set("lastName", TextNode.valueOf(lastName));

        //        WHEN
        // on mock la  vérification de la présence de l'id par les services en renvoyant un nouvel user
        Mockito.when(userServiceMock.findById(Mockito.any())).thenReturn(new User());

        //        THEN
        //on vérifie qu'on a bien un retour http 201
        mockMvc.perform(
                MockMvcRequestBuilders.put("/V1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectNode.toString()))
                .andExpect(MockMvcResultMatchers.status().isCreated());

    }

}
