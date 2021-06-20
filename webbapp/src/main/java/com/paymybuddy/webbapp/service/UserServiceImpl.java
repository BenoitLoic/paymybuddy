package com.paymybuddy.webbapp.service;

import com.paymybuddy.webbapp.dao.UserDao;
import com.paymybuddy.webbapp.entity.User;
import com.paymybuddy.webbapp.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserDao userDao) {
        this.userRepository = userRepository;
        this.userDao = userDao;
    }

    /**
     * This method save the given user in DB by calling Dao.
     * this method is used to create a new user
     * or update an existing user
     *
     * @param theUser to save
     */
    @Override
    public void save(User theUser) {
        userRepository.save(theUser);
    }

    @Override
    public void deleteUserById(int theId) {
        userDao.deleteById(theId);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(int theId) {
        return userDao.findById(theId);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
