package com.dao;

import com.model.Business;
import com.model.JobPosting;
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
public class JobPostingDao {
    SessionFactory sessionFactory;

    @Autowired
    public JobPostingDao(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    public JobPostingDao() {
    }

    @Transactional
    public void addJobPosting(String position, String jobSummary, String salary, Business business) {
        System.out.println("made it to the JobPostingDao, addJobPosting method");
        JobPosting jobPosting = new JobPosting();
        jobPosting.setPosition(position);
        jobPosting.setPosition(jobSummary);
        jobPosting.setSalary(salary);
        jobPosting.setBusiness(business);

        Session session = sessionFactory.getCurrentSession();
        session.save(jobPosting);
    }

    @Transactional
    public List<JobPosting> getJobPostingsByBusiness(int businessId) {
        System.out.println("made it to the JobPostingDao, getJobPostingsByBusiness method");
        Session session = sessionFactory.getCurrentSession();
        String sql = "Select j From JobPosting j where business = ?";
        Query query = session.createQuery(sql);
        query.setInteger(0, businessId);
        List jobPostings = query.list();
        return jobPostings;
    }
}
