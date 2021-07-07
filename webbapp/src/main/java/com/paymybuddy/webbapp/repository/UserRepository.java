package com.paymybuddy.webbapp.repository;

import com.paymybuddy.webbapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Jpa repository for entity : User.
 * Contains some custom method to check/find user.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {


    /**
     * This method check if the user exist in data base, based on its id.
     *
     * @param id the id of the user to check.
     * @return true if success.
     */
    boolean existsById(int id);


    /**
     * This method find an user in data base, based on its id.
     *
     * @param id the id of the user.
     * @return optional of the user
     */
    Optional<User> findById(int id);


    /**
     * This method find an user in data base, based on its email.
     *
     * @param email the email of the user.
     * @return optional of the user
     */
    Optional<User> findByEmail(String email);


}
