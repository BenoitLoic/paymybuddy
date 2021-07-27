package com.paymybuddy.webbapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.paymybuddy.webbapp.entity.User;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.security.Principal;
import java.util.Optional;

@WebMvcTest(controllers = UserControllerImpl.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

  @Autowired MockMvc mockMvc;
  @MockBean UserServiceImpl userServiceMock;
  @Mock Principal principalMock;
  @InjectMocks UserControllerImpl userController;

  private final String getUserAccountUrl = "/home";
  private final String getUserProfileUrl = "/home/profile";
  private final int id = 1;
  private final String email = "testmail";
  private final String lastName = "John";
  private final String firstName = "Doe";
  private final String password = "testpassword";
  private final int balance = 0;

  public User getUserTest() {

    User user = new User();
    user.setFirstName(firstName);
    user.setLastName(lastName);
    user.setEmail(email);
    user.setPassword(password);
    return user;
  }

  @Test
  public void findAllValid() throws Exception {

    // GIVEN

    // WHEN

    // THEN
    mockMvc
        .perform(MockMvcRequestBuilders.get("/home/users"))
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
    mockMvc
        .perform(
            MockMvcRequestBuilders.put("/home/user")
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
    mockMvc
        .perform(
            MockMvcRequestBuilders.put("/home/user")
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
    mockMvc
        .perform(MockMvcRequestBuilders.delete("/home/user").param("id", validParam))
        .andExpect(MockMvcResultMatchers.status().isAccepted());
  }

  @Test
  public void deleteUserInvalid() throws Exception {

    // GIVEN
    String invalidParam = null;

    // WHEN

    // THEN
    mockMvc
        .perform(MockMvcRequestBuilders.delete("/home/user").param("id", invalidParam))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  public void deleteUserValidWhenUserDontExist_ShouldThrowDataNotFindException() throws Exception {

    // GIVEN
    String validParam = "1";
    // WHEN
    Mockito.doThrow(DataNotFindException.class)
        .when(userServiceMock)
        .deleteUserById(Mockito.anyInt());
    // THEN
    mockMvc
        .perform(MockMvcRequestBuilders.delete("/home/user").param("id", validParam))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  public void getUserAccountValid() throws Exception {

    // GIVEN

    // WHEN
    Mockito.when(principalMock.getName()).thenReturn(email);
    Mockito.when(userServiceMock.findByEmail(email)).thenReturn(Optional.of(this.getUserTest()));

    // THEN
    mockMvc
        .perform(MockMvcRequestBuilders.get(getUserAccountUrl).principal(principalMock))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }
}
