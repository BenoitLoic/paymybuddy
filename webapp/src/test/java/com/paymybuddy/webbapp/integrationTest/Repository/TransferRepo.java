package com.paymybuddy.webbapp.integrationTest.Repository;

import com.paymybuddy.webbapp.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransferRepo extends JpaRepository<Transfer, Integer> {

  List<Transfer> getByCreditorId(int id);

  List<Transfer> getByDebtorId(int id);
  Transfer getTransferByDescription(String description);
}
