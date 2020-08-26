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

import java.util.List;

@Repository
@Transactional
public class RequiredSkillDao {
    SessionFactory sessionFactory;

    @Autowired
    public RequiredSkillDao(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    public RequiredSkillDao() {
    }

    @Transactional
    public void addRequiredSkill(String requiredSkillName, JobPosting jobPosting) {
        System.out.println("made it to the RequiredSkillDao, addRequiredSkill method");
        RequiredSkill newRequiredSkill = new RequiredSkill();
        newRequiredSkill.setRequiredSkillName(requiredSkillName);
        newRequiredSkill.setJobPosting(jobPosting);

        Session session = sessionFactory.getCurrentSession();
        session.save(newRequiredSkill);
    }

    @Transactional
    public List<RequiredSkill> getRequiredSkillsByJobPosting(int jobPostingId) {
        System.out.println("made it to the RequiredSkillDao, getRequiredSkillsByJobPosting method");
        Session session = sessionFactory.getCurrentSession();
        String sql = "Select r From RequiredSkill r where jobPosting = ?";
        Query query = session.createQuery(sql);
        query.setInteger(0, jobPostingId);
        List requiredSkills = query.list();
        return requiredSkills;
    }
}
