package com.paymybuddy.webbapp.dao;

import com.paymybuddy.webbapp.entity.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    // define field for entitymanager
    private EntityManager entityManager;

    // set up constructor injection
    @Autowired
    public UserDaoImpl(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }


    @Override
    public List<User> findAll() {

        // get the current hibernate session
        Session currentSession = entityManager.unwrap(Session.class);

        // create a query
        Query<User> theQuery = currentSession.createQuery("from User", User.class);
        // execute query and get result list
        List<User> users = theQuery.getResultList();

        // return the result

        return users;
    }

    @Override
    public User findById(int theID) {

        // get the current hibernate session
        Session session = entityManager.unwrap(Session.class);

        // get the user
        User user = session.get(User.class, theID);
        // return the user

        return user;
    }

    /**
     * This method save the given user in Db if its id == 0, or update the user already in db if id!=0
     *
     * @param theUser to save or update
     */
    @Override
    public void save(User theUser) {


    }

    @Override
    public void deleteById(int theId) {

        // get the current session
        Session currentSession = entityManager.unwrap(Session.class);

        // delete object with primary key
        Query theQuery = currentSession.createQuery("delete from User where id=:userId");
        theQuery.setParameter("userId", theId);
        theQuery.executeUpdate();


    }
}
