package com.paymybuddy.webbapp.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.paymybuddy.webbapp.entity.User;
import com.paymybuddy.webbapp.exception.DataNotFindException;
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

@WebMvcTest(controllers = UserControllerImpl.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    UserServiceImpl userServiceMock;
    @InjectMocks
    UserControllerImpl userController;


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

//
//    @Test
//    public void createUserValid() throws Exception {
//        //GIVEN
//        ObjectMapper obm = new ObjectMapper();
//        ObjectNode jsonNodes = obm.createObjectNode();
//        jsonNodes.set("firstName", TextNode.valueOf(firstName));
//        jsonNodes.set("lastName", TextNode.valueOf(lastName));
//        jsonNodes.set("email", TextNode.valueOf(email));
//        jsonNodes.set("password", TextNode.valueOf(password));
//
//        //WHEN
//
//        //THEN
//        mockMvc.perform(
//                MockMvcRequestBuilders.post("/V1/user")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(jsonNodes.toString()))
//                .andExpect(MockMvcResultMatchers.status().isCreated());
//
//    }
//
//    @Test
//    public void createUserInvalid() throws Exception {
//
//        //GIVEN
//        ObjectMapper obm = new ObjectMapper();
//        ObjectNode jsonNodes = obm.createObjectNode();
//        jsonNodes.set("firstName", TextNode.valueOf(firstName));
//        jsonNodes.set("lastName", TextNode.valueOf(lastName));
//        jsonNodes.set("email", TextNode.valueOf(email));
//        jsonNodes.set("password", TextNode.valueOf(" "));
//
//        //WHEN
//
//        //THEN
//        mockMvc.perform(
//                MockMvcRequestBuilders.post("/V1/user")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(jsonNodes.toString()))
//                .andExpect(MockMvcResultMatchers.status().isBadRequest());
//
//    }
//
//    @Test
//    public void createUserWhen_UserAlreadyExist() throws Exception {
//
////        GIVEN
//        ObjectMapper obm = new ObjectMapper();
//        ObjectNode jsonNodes = obm.createObjectNode();
//        jsonNodes.set("firstName", TextNode.valueOf(firstName));
//        jsonNodes.set("lastName", TextNode.valueOf(lastName));
//        jsonNodes.set("email", TextNode.valueOf(email));
//        jsonNodes.set("password", TextNode.valueOf(password));
//
////        WHEN
//        Mockito.doThrow(DataAlreadyExistException.class)
//                .when(userServiceMock)
//                .save(Mockito.any());
//
////        THEN
//
//        mockMvc.perform(
//                MockMvcRequestBuilders.post("/register/user")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(jsonNodes.toString()))
//                .andExpect(MockMvcResultMatchers.status().isConflict());
//    }

    @Test
    public void findAllValid() throws Exception {

        // GIVEN

        // WHEN

        // THEN
        mockMvc.perform(
                MockMvcRequestBuilders.get("/V1/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));


    }

    @Test
    public void updateUserValid() throws Exception {
        // GIVEN
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonNodes = objectMapper.createObjectNode();
        jsonNodes.set("id", TextNode.valueOf("1"));
        jsonNodes.set("firstName", TextNode.valueOf(firstName));
        jsonNodes.set("lastName", TextNode.valueOf(lastName));
        jsonNodes.set("email", TextNode.valueOf(email));

        // WHEN

        // THEN
        mockMvc.perform(
                MockMvcRequestBuilders.put("/V1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonNodes.toString()))
                .andExpect(MockMvcResultMatchers.status().isCreated());

    }

//    @Test
//    public void updateUserInvalid() throws Exception {
//
//        // GIVEN
//        ObjectMapper objectMapper = new ObjectMapper();
//        ObjectNode jsonNodes = objectMapper.createObjectNode();
//        jsonNodes.set("id", TextNode.valueOf("0"));
//        jsonNodes.set("firstName", TextNode.valueOf(firstName));
//        jsonNodes.set("lastName", TextNode.valueOf(lastName));
//        jsonNodes.set("email", TextNode.valueOf(email));
//
//        // WHEN
//
//        // THEN
//        mockMvc.perform(
//                MockMvcRequestBuilders.put("/V1/user")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(jsonNodes.toString()))
//                .andExpect(MockMvcResultMatchers.status().isBadRequest());
//
//
//    }


    @Test
    public void updateUserValidWhenUserDontExist_ShouldThrowDataNotFindException() throws Exception {
        // GIVEN
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonNodes = objectMapper.createObjectNode();
        jsonNodes.set("id", TextNode.valueOf("1"));
        jsonNodes.set("firstName", TextNode.valueOf(firstName));
        jsonNodes.set("lastName", TextNode.valueOf(lastName));
        jsonNodes.set("email", TextNode.valueOf(email));

        // WHEN
        Mockito.doThrow(DataNotFindException.class).when(userServiceMock).update(Mockito.any());
        // THEN
        mockMvc.perform(
                MockMvcRequestBuilders.put("/V1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonNodes.toString()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    @Test
    public void deleteUserValid() throws Exception {

        // GIVEN
        String validParam = String.valueOf(id);
        // WHEN

        // THEN
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/V1/user")
                        .param("id", validParam))
                .andExpect(MockMvcResultMatchers.status().isAccepted());

    }

    @Test
    public void deleteUserInvalid() throws Exception {

        // GIVEN
        String invalidParam = null;

        // WHEN

        // THEN
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/V1/user")
                        .param("id", invalidParam))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    public void deleteUserValidWhenUserDontExist_ShouldThrowDataNotFindException() throws Exception {

        // GIVEN
        String validParam = "1";
        // WHEN
        Mockito.doThrow(DataNotFindException.class).when(userServiceMock).deleteUserById(Mockito.anyInt());
        // THEN
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/V1/user")
                        .param("id", validParam))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }

}
