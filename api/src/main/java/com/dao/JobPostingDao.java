// File: JobPostingDao.java
// DAO class for interacting with job_posting table in the database
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
import org.apache.log4j.Logger;

import java.util.List;

/**
 * This is the Job Posting DAO class for interaction with our database with the job_posting table.
 */
@Repository
@Transactional
public class JobPostingDao {
    SessionFactory sessionFactory;
    static Logger log = Logger.getLogger(JobPostingDao.class);

    @Autowired
    public JobPostingDao(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    public JobPostingDao() {
    }

    /**
     * addJobPosting method instantiates an object of JobPosting and sets the position, job description, salary,
     * and business which will be saved in the session.
     */
    @Transactional
    public void addJobPosting(String position, String jobSummary, String salary, Business business) {
        log.info("JobPostingDao.addJobPosting method initializing");
        JobPosting jobPosting = new JobPosting();
        jobPosting.setPosition(position);
        jobPosting.setPosition(jobSummary);
        jobPosting.setSalary(salary);
        jobPosting.setBusiness(business);

        Session session = sessionFactory.getCurrentSession();
        session.save(jobPosting);
    }

    /**
     * getJobPostingsByBusiness method will create and execute a query for getting the job posting based on
     * the business name. The query will then be put into a list and be returned.
     */
    @Transactional
    public List<JobPosting> getJobPostingsByBusiness(int businessId) {
        log.info("JobPostingDao.getJobPostingsByBusiness method initializing");
        Session session = sessionFactory.getCurrentSession();
        String sql = "Select j From JobPosting j where business = ?";
        Query query = session.createQuery(sql);
        query.setInteger(0, businessId);
        List jobPostings = query.list();
        return jobPostings;
        log.info("Returning jobPostings");
    }
}
