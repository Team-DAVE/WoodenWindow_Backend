// File: BusinessDao.java
// DAO class for interacting with business table in the database
package com.dao;

import com.model.Business;
import com.model.Profile;
import com.model.Users;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * This is the Business DAO class for interaction with our database with the business table.
 */
@Repository
@Transactional
public class BusinessDao {
    SessionFactory sessionFactory;
    static Logger log = Logger.getLogger(BusinessDao.class);

    @Autowired
    public BusinessDao(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    public BusinessDao() {
    }

    /**
     * addBusiness method instantiates an object of Business and sets the name, address, summary, and user
     * which will be saved in the session.
     */
    @Transactional
    public void addBusiness(String businessName, String address, String summary, Users user) {
        log.info("BusinessDao.addBusiness method invoked");
        Business newBusiness = new Business();
        newBusiness.setBusinessName(businessName);
        newBusiness.setAddress(address);
        newBusiness.setSummary(summary);
        newBusiness.setUser(user);

        Session session = sessionFactory.getCurrentSession();
        session.save(newBusiness);
    }

    /**
     * getBusinessesByUserId method will create and execute a query for getting the business based on the user.
     * The query will then be put into a list and be returned.
     */
    @Transactional
    public List<Business> getBusinessesByUserId(int userId) {
        log.info("BusinessDao.getBusinessByUserId method invoked");
        Session session = sessionFactory.getCurrentSession();
        String sql = "Select b From Business b where users = ?";
        Query query = session.createQuery(sql);
        query.setInteger(0, userId);
        List businesses = query.list();
        log.info("Returning businesses");
        return businesses;
    }
}
