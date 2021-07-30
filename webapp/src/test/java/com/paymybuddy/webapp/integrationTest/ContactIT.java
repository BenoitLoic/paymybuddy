package com.paymybuddy.webapp.integrationTest;

import com.paymybuddy.webapp.dto.ContactDto;
import com.paymybuddy.webapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ContactIT {

  @Autowired
  UserRepository userRepo;
  @Autowired MockMvc mockMvc;

  private final String createUrl = "/newContact";
  private final String deleteUrl = "/deleteContact";
  private final String readUrl = "/home/contact";

  @Test
  public void requestWhenNotAuthenticated() throws Exception {

    // POST:/newContact DELETE:/deleteContact GET:/home/contact
    mockMvc
        .perform(post(createUrl).with(SecurityMockMvcRequestPostProcessors.csrf()))
        .andExpect(status().isUnauthorized());
    mockMvc
        .perform(delete(deleteUrl).with(SecurityMockMvcRequestPostProcessors.csrf()))
        .andExpect(status().isUnauthorized());
    mockMvc.perform(get(readUrl)).andExpect(status().isUnauthorized());
  }

  @Test
  public void getContactValid() throws Exception {

    // When no contact -> should return an empty list
    mockMvc
        .perform(get(readUrl).with(user("smaug@mail.com")))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.model().attribute("contacts", new ArrayList<>()));

    // When there are contact, should return all of them

    ContactDto contactDto1 = new ContactDto("Sam", "Gamgee", "sam@mail.com");
    ContactDto contactDto2 = new ContactDto("Pipin", "Took", "peregrin@mail.com");
    ContactDto contactDto3 = new ContactDto("Merry", "Took", "merry@mail.com");

    mockMvc
        .perform(get(readUrl).with(user("frodo@mail.com")))
        .andExpect(status().isOk())
        .andExpect(
            MockMvcResultMatchers.model()
                .attribute("contacts", containsInAnyOrder(contactDto1, contactDto2, contactDto3)));
  }

  @Transactional
  @Test
  public void deleteContact() throws Exception {

    // When valid
    mockMvc
        .perform(
            delete(deleteUrl)
                .param("email", "sam@mail.com")
                .with(SecurityMockMvcRequestPostProcessors.user("frodo@mail.com"))
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
        .andExpect(status().isAccepted())
        .andExpect(view().name("success"));

    // When contact doesn't exist
    mockMvc
        .perform(
            delete(deleteUrl)
                .param("email", "sam@mail.com")
                .with(SecurityMockMvcRequestPostProcessors.user("frodo@mail.com"))
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
        .andExpect(status().isNotFound());
  }

  @Transactional
  @Test
  public void createContact() throws Exception {

    // When valid
    String friendMail = "bilbo@mail.com";
    mockMvc
        .perform(
            post(createUrl)
                .param("email", friendMail)
                .with(SecurityMockMvcRequestPostProcessors.user("frodo@mail.com"))
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
        .andExpect(status().isCreated())
        .andExpect(view().name("success"));

    // When contact already exist
    mockMvc
        .perform(
            post(createUrl)
                .param("email", friendMail)
                .with(SecurityMockMvcRequestPostProcessors.user("frodo@mail.com"))
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
        .andExpect(status().isConflict());

    // When contact is not found
    mockMvc
        .perform(
            post(createUrl)
                .param("email", "stupidelf@mail.com")
                .with(SecurityMockMvcRequestPostProcessors.user("frodo@mail.com"))
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
        .andExpect(status().isNotFound());
  }
}
