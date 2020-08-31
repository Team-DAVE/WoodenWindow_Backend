// File: UserDao.java
// DAO class for user table
package com.dao;

import com.model.Users;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * This is the User DAO class for interaction with our database.
 */
@Repository
@Transactional
public class UserDao {
    SessionFactory sessionFactory;
    	static Logger log = Logger.getLogger(UserDao.class);

    @Autowired
    public UserDao(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    public UserDao() {
    }

    /**
     * addUser method will take in the parameters from the user's reactive form and add it to the database
     * on the user table. A query will be called and check if the email does not exist in the database. If not,
     * it will add a user to the database.
     */
    @Transactional
    public boolean addUser(String email, String password, String firstName, String lastName) {
        log.info("UserDao.addUser method initializing");
        Users newUser = new Users();
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);

        Session session = sessionFactory.getCurrentSession();
        String sql = "Select u From Users u where email = ?";
        Query query = session.createQuery(sql);
        query.setParameter(0, newUser.getEmail());
        if (query.uniqueResult() == null) {
            log.info("Initializing unique query in UserDao");
            session.save(newUser);
            log.info("User saved in Dao");
            return true;
        }
        return false;
        log.info("UserDao.addUser uniqueResult returned false");
    }

    /**
     * findUserByEmail method will take the user's email input as a parameter. It will create a query to select a user
     * based on the email that is passed as a parameter.
     */
    @Transactional
    public Users findUserByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        String sql = "Select u From Users u where email = ?";
        Query query = session.createQuery(sql);
        query.setParameter(0, email);
        return (Users) query.uniqueResult();
    }

    @Transactional
    public Users findUserById(int id) {
        log.info("UserDao.findUserById method initializing");
        Session session = sessionFactory.getCurrentSession();
        String sql = "Select u From Users u where userId = ?";
        Query query = session.createQuery(sql);
        query.setParameter(0, id);
        return (Users) query.uniqueResult();
        log.info("Returning Users");
    }

    /**
     * findAll method will create and run a query to get all users from the table Users.
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly=true)
    public List<Users> findAll() {
        log.info("UserDao.findAll method initializing");
        Session session;
        session = sessionFactory.getCurrentSession();
        System.out.println(session);
        String sql = "Select u From Users u";
        Query query = session.createQuery(sql);
        List<Users> users = query.list();
        return users;
        log.info("Returning users");
    }
}
