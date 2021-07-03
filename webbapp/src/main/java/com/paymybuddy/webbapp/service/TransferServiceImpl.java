package com.paymybuddy.webbapp.service;

import com.paymybuddy.webbapp.entity.User;
import com.paymybuddy.webbapp.exception.DataNotFindException;
import com.paymybuddy.webbapp.exception.IllegalArgumentException;
import com.paymybuddy.webbapp.exception.UnicornException;
import com.paymybuddy.webbapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class TransferServiceImpl implements TransferService {

    @Autowired
    UserRepository userRepository;

    /**
     * This method will add some cash to User balance.
     *
     * @param amount the integer to add
     * @return true if success
     */
    @Override
    public boolean addCash(int amount, String email) {

        // Check amount > 0
        if (amount < 1) {
            throw new IllegalArgumentException("KO - Amount must be > 0.");
        }
        // Get User
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new DataNotFindException("KO - Can't find user: " + email);
        }
        // Check User Balance
        User user = userOptional.get();
        long checkResult = (long) amount + (long) user.getBalance();
        // Add Amount and Check if initialBalance + amount < Integer.MAX
        if (checkResult <= Integer.MAX_VALUE) {
            user.setBalance(amount + user.getBalance());
            userRepository.save(user);
            // save
            return true;
        } else {
            throw new UnicornException("Error - Balance over max value.");
        }


    }

    /**
     * This method will remove some cash to User balance.
     *
     * @param amount the integer to subtract
     * @return true if success
     */
    @Override
    public boolean removeCash(int amount, String email) {
        // Check amount > 0
        if (amount < 1) {
            throw new IllegalArgumentException("KO - Amount must be > 0.");
        }
        // Get User
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new DataNotFindException("KO - Can't find user: " + email);
        }
        // Check User Balance
        User user = userOptional.get();
        long checkResult = (long) user.getBalance() - (long) amount;
        // Add Amount and Check if initialBalance + amount < Integer.MAX
        if (checkResult >= 0) {
            user.setBalance(user.getBalance() - amount);
            userRepository.save(user);
            // save
            return true;
        } else {
            throw new UnicornException("Error - Balance over max value.");
        }

    }
}
