package com.paymybuddy.webbapp.service;

import com.paymybuddy.webbapp.dao.UserDao;
import com.paymybuddy.webbapp.entity.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log4j2
@Service
public class UserServiceImpl implements UserService {


    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * This method save the given user in DB by calling Dao.
     * this method is used to create a new user
     * or update an existing user
     * @param theUser to save
     */
    @Override
    @Transactional
    public void save(User theUser) {
        userDao.save(theUser);
    }

    @Override
    public void deleteUserById(int theId) {
        userDao.deleteById(theId);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User findById(int theId) {
        return userDao.findById(theId);
    }
}
