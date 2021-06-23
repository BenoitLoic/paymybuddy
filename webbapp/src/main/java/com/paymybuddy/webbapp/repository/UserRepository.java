package com.paymybuddy.webbapp.repository;

import com.paymybuddy.webbapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {


    boolean existsById(int id);

    Optional<User> findById(int id);

    Optional<User> findByEmail(String email);


}
