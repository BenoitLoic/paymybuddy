package com.paymybuddy.webbapp.dao;

import com.paymybuddy.webbapp.dto.GetTransferDto;
import com.paymybuddy.webbapp.entity.Transfer;
import com.paymybuddy.webbapp.entity.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
@Log4j2
@Repository
public class TransferDaoImpl implements TransferDao {

    @Autowired
    EntityManagerFactory entityManagerF;

    private EntityManager entityManager;
    private EntityTransaction tx;

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
        try {

            entityManager = entityManagerF.createEntityManager();
            tx = entityManager.getTransaction();

            tx.begin();

            User userCreditor = entityManager.find(User.class, creditor.getId());
            userCreditor.setBalance(creditor.getBalance());

            User userDebtor = entityManager.find(User.class, debtor.getId());
            userDebtor.setBalance(debtor.getBalance());

            entityManager.persist(transfer);


            //TODO: trouver un moyen de vérifier que les requêtes sont faites en BATCH.

            tx.commit();

            return new GetTransferDto(creditor.getFirstName(), transfer.getDescription(), transfer.getAmount());

        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
}
