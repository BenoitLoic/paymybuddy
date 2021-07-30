package com.paymybuddy.webapp.integrationTest.Repository;

import com.paymybuddy.webapp.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepo extends JpaRepository<Transfer, Integer> {

  Transfer getTransferByDescription(String description);
}
