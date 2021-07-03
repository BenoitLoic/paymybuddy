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
//    @Override
//    @Transactional
//    public GetTransferDto saveNewTransfer(User creditor, User debtor, Transfer transfer) {
//
//        // create session factory
//        SessionFactory factory = new Configuration()
//                .configure()
//                .addAnnotatedClass(User.class)
//                .addAnnotatedClass(Transfer.class)
//                .buildSessionFactory();
//        Session session = factory.getCurrentSession();
//
//        // start transaction
//        session.beginTransaction();
//        System.out.println("debtor: "+debtor);
//        System.out.println("creditor: " + creditor);
//        System.out.println(transfer);
//        // save creditor and debtor
//        session.update(debtor);
//        session.getTransaction().commit();
//        session.beginTransaction();
//        session.update(creditor);
//
//        // commit transaction
//        session.getTransaction().commit();
//
//        //GetTransferDto(String contactName, String description, int amount)
//        return new GetTransferDto(creditor.getFirstName(), transfer.getDescription(), transfer.getAmount());
//    }
    @Override
    @Transactional
    public GetTransferDto saveNewTransfer(User creditor, User debtor, Transfer transfer) {
        try {

            entityManager = entityManagerF.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();

            User userCreditor = entityManager.find(User.class, creditor.getId());
            userCreditor = creditor;

            User userDebtor = entityManager.find(User.class, debtor.getId());
            userCreditor = debtor;

            entityManager.persist(transfer);

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
