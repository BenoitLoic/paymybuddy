package com.paymybuddy.webapp.integrationTest;

import com.paymybuddy.webapp.dto.GetUserAccountDto;
import com.paymybuddy.webapp.dto.NewUserDto;
import com.paymybuddy.webapp.entity.User;
import com.paymybuddy.webapp.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserIT {

  @Autowired MockMvc mockMvc;
  @Autowired
  UserRepository userRepo;


  @Transactional
  @WithMockUser(username = "smaug@mail.com", password = "test")
  @Test
  public void deleteUser() throws Exception {

    // When valid
    mockMvc
        .perform(
            MockMvcRequestBuilders.delete("/home/user")
                .param("id", "7")
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
        .andExpect(MockMvcResultMatchers.status().isAccepted());

    // When user doesn't exist
    mockMvc
        .perform(
            MockMvcRequestBuilders.delete("/home/user")
                .param("id", "7")
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
        .andExpect(MockMvcResultMatchers.status().isNotFound());

    // When csrf token is missing
    mockMvc
        .perform(
            MockMvcRequestBuilders.delete("/home/user")
                .param("id", "2")
                .with(SecurityMockMvcRequestPostProcessors.user("user")))
        .andExpect(MockMvcResultMatchers.status().isForbidden());
  }

  @Transactional
  @Test
  public void createUserValid() throws Exception {

    NewUserDto newUserDto = new NewUserDto();
    newUserDto.setFirstName("Gandalf");
    newUserDto.setLastName("The Grey");

    newUserDto.setEmail("wizz.wiiiiiiizzz.gandalf@mail.com");
    newUserDto.setPassword("testPass");


    mockMvc
        .perform(
            post("/registration/createNewUser")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .content(
                    "firstName="
                        + newUserDto.getFirstName()
                        + "&lastName="
                        + newUserDto.getLastName()
                        + "&email="
                        + newUserDto.getEmail()
                        + "&password="
                        + newUserDto.getPassword())
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
        .andExpect(status().isCreated());

    // Check if new user new user is present in DB
    Optional<User> check = userRepo.findByEmail("wizz.wiiiiiiizzz.gandalf@mail.com");
    Assertions.assertThat(check.isPresent()).isTrue();
  }

  @Test
  public void requestWhenNotAuthenticated() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders.get("/home")).andExpect(status().isUnauthorized());
    mockMvc
        .perform(MockMvcRequestBuilders.get("/home/profile"))
        .andExpect(status().isUnauthorized());
    mockMvc
        .perform(MockMvcRequestBuilders.get("/home/contact"))
        .andExpect(status().isUnauthorized());
    mockMvc
        .perform(MockMvcRequestBuilders.get("/home/transfer"))
        .andExpect(status().isUnauthorized());
    mockMvc
        .perform(
            MockMvcRequestBuilders.put("/home/user")
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
        .andExpect(status().isUnauthorized());
    mockMvc
            .perform(
                    MockMvcRequestBuilders.delete("/home/user")
                            .with(SecurityMockMvcRequestPostProcessors.csrf()))
            .andExpect(status().isUnauthorized());
  }

  @Test
  public void authentication() throws Exception {

    // When  user + pass are valid -> user redirected to /home
    mockMvc
        .perform(formLogin("/authenticateTheUser").user("frodo@mail.com").password("test"))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/home"));

    // When user is invalid
    mockMvc
        .perform(formLogin("/authenticateTheUser").user("wk.angmar@mail.com").password("test"))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/showLoginPage?error"));

    // When password is invalid
    mockMvc
        .perform(formLogin("/authenticateTheUser").user("frodo@mail.com").password("fail"))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/showLoginPage?error"));
  }

  @WithMockUser(username = "frodo@mail.com")
  @Test
  public void getUserAccountValid() throws Exception {

    GetUserAccountDto getUserAccountDto = new GetUserAccountDto();
    getUserAccountDto.setId(2);
    getUserAccountDto.setFirstName("Frodo");
    getUserAccountDto.setLastName("Baggins");
    getUserAccountDto.setBalance(new BigDecimal("5000.00"));
    getUserAccountDto.setEmail("frodo@mail.com");

    mockMvc
        .perform(MockMvcRequestBuilders.get("/home"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.model().attributeExists("theUser"))
        .andExpect(MockMvcResultMatchers.model().attribute("theUser", getUserAccountDto));
  }

  @WithMockUser(username = "frodo@mail.com", password = "test")
  @Test
  public void getUserProfileValid() throws Exception {

    GetUserAccountDto getUserAccountDto = new GetUserAccountDto();
    getUserAccountDto.setId(2);
    getUserAccountDto.setFirstName("Frodo");
    getUserAccountDto.setLastName("Baggins");
    getUserAccountDto.setBalance(new BigDecimal("5000.00"));
    getUserAccountDto.setEmail("frodo@mail.com");

    mockMvc
        .perform(MockMvcRequestBuilders.get("/home/profile"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.model().attributeExists("theUser"))
        .andExpect(MockMvcResultMatchers.model().attribute("theUser", getUserAccountDto));
  }

  @Transactional
  @WithMockUser(username = "frodo@mail.com", password = "test")
  @Test
  public void updateUserProfileValid() throws Exception {

    mockMvc
        .perform(
            MockMvcRequestBuilders.put("/home/user")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content("id=" + "2" + "&firstName=" + "" + "&lastName=" + "Brandybuck")
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
        .andExpect(MockMvcResultMatchers.status().isCreated());
    // Check if last name is updated
    User user = userRepo.findByEmail("frodo@mail.com").get();
    Assertions.assertThat(user.getLastName()).isEqualTo("Brandybuck");
  }
}
