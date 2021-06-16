package com.paymybuddy.webbapp.service;

import com.paymybuddy.webbapp.dao.UserDao;
import com.paymybuddy.webbapp.entity.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
@Disabled
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserDao userDaoMock;
    @InjectMocks
    UserServiceImpl userServiceTested;


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
Mockito.doNothing().when(userDaoMock).save(Mockito.any());
//        THEN
        Mockito.verify(userDaoMock, Mockito.times(1)).save(user);

    }


//    @Test
//    void createUserValidWhenUserAlreadyExist_ShouldThrowDataAlreadyExistException() {
//
//        //        GIVEN
//        User user = new User();
//        user.setFirstName(firstName);
//        user.setLastName(lastName);
//        user.setEmail(email);
//        user.setPassword(password);
//
////        WHEN
//        Mockito.doNothing().when(userDaoMock.save(););
//
////        THEN
//        assertThrows(DataAlreadyExistException.class, () -> userServiceTested.save(user));
//
//    }


}