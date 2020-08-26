package com.dao;

import com.model.Users;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@Transactional
public class UserDao {
    SessionFactory sessionFactory;

    @Autowired
    public UserDao(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    public UserDao() {
    }

    @Transactional
    public boolean addUser(String email, String password, String firstName, String lastName) {
        System.out.println("made it to dao");
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
            System.out.println("unique query in Dao");
            session.save(newUser);
            System.out.println("user saved in Dao");
            return true;
        }
        return false;
    }

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
        Session session = sessionFactory.getCurrentSession();
        String sql = "Select u From Users u where userId = ?";
        Query query = session.createQuery(sql);
        query.setParameter(0, id);
        return (Users) query.uniqueResult();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly=true)
    public List<Users> findAll() {
        System.out.println("dao method findall invoked");
        Session session;
        session = sessionFactory.getCurrentSession();
        System.out.println(session);
        String sql = "Select u From Users u";
        Query query = session.createQuery(sql);
        List<Users> users = query.list();
        return users;
    }
}
