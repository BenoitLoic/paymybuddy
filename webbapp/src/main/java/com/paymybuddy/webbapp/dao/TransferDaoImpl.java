package com.paymybuddy.webbapp.dao;

import com.paymybuddy.webbapp.dto.GetTransferDto;
import com.paymybuddy.webbapp.entity.Transfer;
import com.paymybuddy.webbapp.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;


@Repository
public class TransferDaoImpl implements TransferDao {

    final
    EntityManagerFactory entityManagerF;

    private EntityManager entityManager;
    private EntityTransaction tx;

    @Autowired
    public TransferDaoImpl(EntityManagerFactory entityManagerF) {
        this.entityManagerF = entityManagerF;
    }

    /**
     * This method will save a new transfer in transaction table and update both user.
     *
     * @param creditor the user who get the money
     * @param debtor   the user who give the money
     * @param transfer the transfer entity
     * @return GetTransferDto(creditorFirstName, description, amount)
     */
    @Override
    @Transactional
    public GetTransferDto saveNewTransfer(User creditor, User debtor, Transfer transfer) {

        User userCreditor = entityManager.find(User.class, creditor.getId());
        userCreditor.setBalance(creditor.getBalance());

        User userDebtor = entityManager.find(User.class, debtor.getId());
        userDebtor.setBalance(debtor.getBalance());

        entityManager.persist(transfer);


        //TODO: trouver un moyen de vérifier que les requêtes sont faites en BATCH.



        return new GetTransferDto(creditor.getFirstName(), transfer.getDescription(), transfer.getAmount());


    }
}

