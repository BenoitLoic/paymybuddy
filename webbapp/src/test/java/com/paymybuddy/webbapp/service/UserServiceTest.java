package com.paymybuddy.webbapp.service;


import com.paymybuddy.webbapp.entity.User;
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


        // WHEN
        Mockito.when(userRepositoryMock.existsById(Mockito.anyInt())).thenReturn(true);
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


}