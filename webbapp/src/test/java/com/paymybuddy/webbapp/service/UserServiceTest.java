package com.paymybuddy.webbapp.service;


import com.paymybuddy.webbapp.dto.ContactDto;
import com.paymybuddy.webbapp.entity.User;
import com.paymybuddy.webbapp.exception.DataAlreadyExistException;
import com.paymybuddy.webbapp.exception.DataNotFindException;
import com.paymybuddy.webbapp.model.UserModel;
import com.paymybuddy.webbapp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepositoryMock;
    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @InjectMocks
    UserServiceImpl userService;


    private int id = 1;
    private String email = "testmail";
    private String lastName = "Doe";
    private String firstName = "John";
    private String password = "testpassword";
    private int balance = 0;
    private String phone;
    private String addressPrefix;
    private String addressNumber;
    private String addressStreet;
    private String zip;
    private String city;


    /**
     *
     */
    @Test
    void saveUserValid() {

//        GIVEN
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
//        WHEN
        userService.save(user);
//        THEN

        Mockito.verify(userRepositoryMock, Mockito.times(1)).save(user);
    }


    @Test
    public void updateUserValid() {

        // GIVEN
        UserModel userToUpdate = new UserModel();
        userToUpdate.setFirstName(firstName);
        userToUpdate.setLastName(lastName);
        userToUpdate.setId(id);
        userToUpdate.setEmail(email);
        User user = new User();

        // WHEN
        Mockito.when(userRepositoryMock.existsById(Mockito.anyInt())).thenReturn(true);
        Mockito.when(userRepositoryMock.getById(Mockito.anyInt())).thenReturn(user);
        userService.update(userToUpdate);
        // THEN

        Mockito.verify(userRepositoryMock, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(userRepositoryMock, Mockito.times(1)).existsById(Mockito.anyInt());


    }

    @Test
    public void updateUserWhenUserDontExist_ShouldThrowDataNotFindException() {

        // GIVEN
        UserModel userToUpdate = new UserModel();
        userToUpdate.setFirstName(firstName);
        userToUpdate.setLastName(lastName);
        userToUpdate.setId(id);


        // WHEN
        Mockito.when(userRepositoryMock.existsById(Mockito.anyInt())).thenReturn(false);
        // THEN
        Assertions.assertThrows(DataNotFindException.class, () -> userService.update(userToUpdate));


    }

    @Test
    public void deleteUserValid() {

        // GIVEN
        int validUserId = 598743;

        // WHEN
        Mockito.when(userRepositoryMock.existsById(Mockito.anyInt())).thenReturn(true);
        userService.deleteUserById(validUserId);
        // THEN

        Mockito.verify(userRepositoryMock, Mockito.times(1)).deleteById(validUserId);
        Mockito.verify(userRepositoryMock, Mockito.times(1)).existsById(validUserId);


    }

    @Test
    public void deleteUserValidWhenUserDontExist_ShouldThrowDataNotFindException() {

        // GIVEN
        int validUserId = 598743;

        // WHEN
        Mockito.when(userRepositoryMock.existsById(Mockito.anyInt())).thenReturn(false);
        // THEN
        Assertions.assertThrows(DataNotFindException.class, () -> userService.deleteUserById(validUserId));


    }

    @Test
    public void addNewContactValid() {

        // GIVEN
        User user1 = new User(email, lastName, firstName, password);
        user1.setId(1);
        Optional<User> userToAdd = Optional.of(user1);
        User user2 = new User(email + 1, lastName, firstName, password);
        user2.setId(2);
        Optional<User> userAccount = Optional.of(user2);

        // WHEN
        Mockito.when(userRepositoryMock.findByEmail(Mockito.anyString())).thenReturn(userToAdd, userAccount);

        String returnString = userService.addNewContact(email, email + 1);
        // THEN
        org.assertj.core.api.Assertions.assertThat(returnString).isEqualTo(firstName + " " + lastName);
    }

    @Test
    public void addNewContactWhenContactEmailDontExist_ShouldThrowDataNotFindException() {

        // GIVEN
        Optional<User> userToAdd = Optional.empty();
        User user2 = new User(email + 1, lastName, firstName, password);
        user2.setId(2);
        Optional<User> userAccount = Optional.of(user2);

        // WHEN
        Mockito.when(userRepositoryMock.findByEmail(Mockito.anyString())).thenReturn(userToAdd, userAccount);

        // THEN
        Assertions.assertThrows(DataNotFindException.class, () -> userService.addNewContact(email, email + 1));
    }

    @Test
    public void addNewContactWhenContactAlreadyExist_ShouldThrowDataAlreadyExist() {

        // GIVEN
        User user1 = new User(email, lastName, firstName, password);
        user1.setId(1);
        Optional<User> userToAdd = Optional.of(user1);
        User user2 = new User(email + 1, lastName, firstName, password);
        user2.setId(2);
        user2.getContactList().add(user1);
        Optional<User> userAccount = Optional.of(user2);

        // WHEN
        Mockito.when(userRepositoryMock.findByEmail(Mockito.anyString())).thenReturn(userToAdd, userAccount);


        // THEN
        Assertions.assertThrows(DataAlreadyExistException.class, () -> userService.addNewContact(email, email + 1));
    }

    @Test
    public void deleteContactValid() {

        // GIVEN
        User user = new User("userEmail", "userLastName", "userFirstName", "test");
        User contact = new User(email, lastName, firstName, password);
        contact.setId(id);
        user.getContactList().add(contact);
        ContactDto expectedDto = new ContactDto(firstName, lastName, email);
        // WHEN
        Mockito.when(userRepositoryMock.findByEmail("userEmail")).thenReturn(Optional.of(user));


        ContactDto actualDto = userService.deleteContact(email, "userEmail");
        // THEN
        org.assertj.core.api.Assertions.assertThat(actualDto).isEqualTo(expectedDto);

    }


    @Test
    public void deleteContactWhenContactDontExist_ShouldThrowDataNotFindException() {

        // GIVEN
        User user = new User(email, "userLastName", "userFirstName", "test");
        User contact = new User("email", lastName, firstName, password);


        // WHEN
        Mockito.when(userRepositoryMock.findByEmail(email)).thenReturn(Optional.of(user));


        // THEN
        Assertions.assertThrows(DataNotFindException.class, () -> userService.deleteContact("email", email));

    }

    @Test
    public void getContactValid_ShouldReturnAListOfTwoDto() {

        // GIVEN
        User user = new User(email, lastName, firstName, password);
        User userContact1 = new User(email + 1, lastName + 1, firstName + 1, password + 1);
        User userContact2 = new User(email + 2, lastName + 2, firstName + 2, password + 2);
        user.getContactList().add(userContact1);
        user.getContactList().add(userContact2);

        ContactDto contactDto1 = new ContactDto(firstName + 1, lastName + 1, email + 1);
        ContactDto contactDto2 = new ContactDto(firstName + 2, lastName + 2, email + 2);
        // WHEN
        Mockito.when(userRepositoryMock.findByEmail(email)).thenReturn(Optional.of(user));
        Collection<ContactDto> expected = Arrays.asList(contactDto1, contactDto2);
        Collection<ContactDto> actual = userService.getAllContact(email);

        // THEN
        org.assertj.core.api.Assertions.assertThat(actual.containsAll(expected)).isTrue();


    }

    @Test
    public void getContactValidWhenNoContactFound_ShouldReturnAnEmptyList() {

        // GIVEN
        User user = new User(email, lastName, firstName, password);

        // WHEN
        Mockito.when(userRepositoryMock.findByEmail(email)).thenReturn(Optional.of(user));
        Collection<ContactDto> expected = new ArrayList<>();
        Collection<ContactDto> actual = userService.getAllContact(email);

        // THEN
        org.assertj.core.api.Assertions.assertThat(actual).isEqualTo(expected);


    }

}