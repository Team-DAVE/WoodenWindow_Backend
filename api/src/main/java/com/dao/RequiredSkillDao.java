// File: RequiredSkillDao.java
// DAO class for interacting with required_skill table
package com.dao;

import com.model.Business;
import com.model.JobPosting;
import com.model.RequiredSkill;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * This is the Required Skill DAO class for interaction with our database with the required_skill table.
 */
@Repository
@Transactional
public class RequiredSkillDao {
    SessionFactory sessionFactory;
    	static Logger log = Logger.getLogger(RequiredSkillDao.class);

    @Autowired
    public RequiredSkillDao(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    public RequiredSkillDao() {
    }

    /**
     * addRequiredSkill method instantiates an object of RequiredSkill and sets the required skill name and job posting
     * which will be saved in the session.
     */
    @Transactional
    public void addRequiredSkill(String requiredSkillName, JobPosting jobPosting) {
        log.info("RequiredSkillDao.addRequiredSkill method initializing");
        RequiredSkill newRequiredSkill = new RequiredSkill();
        newRequiredSkill.setRequiredSkillName(requiredSkillName);
        newRequiredSkill.setJobPosting(jobPosting);

        Session session = sessionFactory.getCurrentSession();
        session.save(newRequiredSkill);
    }

    /**
     * getRequiredSkillsByJobPosting method will create and execute a query for getting the required skill based on
     * the job posting. The query will then be put into a list and be returned.
     */
    @Transactional
    public List<RequiredSkill> getRequiredSkillsByJobPosting(int jobPostingId) {
        log.info("RequiredSkillDao.getRequiredSkilsByJobPosting method initializing");
        Session session = sessionFactory.getCurrentSession();
        String sql = "Select r From RequiredSkill r where jobPosting = ?";
        Query query = session.createQuery(sql);
        query.setInteger(0, jobPostingId);
        List requiredSkills = query.list();
        return requiredSkills;
        log.info("Returning RequiredSkills");
    }
}
