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

import java.util.List;

@Repository
@Transactional
public class BusinessDao {
    SessionFactory sessionFactory;

    @Autowired
    public BusinessDao(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    public BusinessDao() {
    }

    @Transactional
    public void addBusiness(String businessName, String address, String summary, Users user) {
        System.out.println("made it to the businessDao, addBusiness method");
        Business newBusiness = new Business();
        newBusiness.setBusinessName(businessName);
        newBusiness.setAddress(address);
        newBusiness.setSummary(summary);
        newBusiness.setUser(user);

        Session session = sessionFactory.getCurrentSession();
        session.save(newBusiness);
    }

    @Transactional
    public List<Business> getBusinessesByUserId(int userId) {
        System.out.println("made it to the businessDao, getBusinessesByUserId method");
        Session session = sessionFactory.getCurrentSession();
        String sql = "Select b From Business b where users = ?";
        Query query = session.createQuery(sql);
        query.setInteger(0, userId);
        List businesses = query.list();
        return businesses;
    }
}
