package com.paymybuddy.webbapp.integrationTest.Repository;

import com.paymybuddy.webbapp.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepo extends JpaRepository<Transfer, Integer> {

  Transfer getTransferByDescription(String description);
}
