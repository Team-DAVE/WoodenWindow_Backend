// File: ProfileSkillDao.java
// DAO class for interacting with profile_skill table in the database
package com.dao;

import com.model.Profile;
import com.model.ProfileSkill;
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
 * This is the Profile Skill DAO class for interaction with our database with the profile_skill table.
 */
@Repository
@Transactional
public class ProfileSkillDao {
    SessionFactory sessionFactory;
    	static Logger log = Logger.getLogger(BudgetServlet.class);

    @Autowired
    public ProfileSkillDao(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    public ProfileSkillDao() {
    }

    /**
     * addProfileSkill method instantiates an object of ProfileSkill and sets the profile skill name and profile
     * which will be saved in the session.
     */
    @Transactional
    public void addProfileSkill(String profileSkillName, Profile profile) {
        log.info("ProfileSkillDao.addProfileSkill method initializing");
        ProfileSkill newProfileSkill = new ProfileSkill();
        newProfileSkill.setProfileSkillName(profileSkillName);
        newProfileSkill.setProfile(profile);
        System.out.println(newProfileSkill);

        Session session = sessionFactory.getCurrentSession();
        session.save(newProfileSkill);
    }

    /**
     * getProfileSkillsByProfile method will create and execute a query for getting the profile skill based on
     * the profile. The query will then be put into a list and be returned.
     */
    @Transactional
    public List<ProfileSkill> getProfileSkillsByProfile(int profileId) {
        log.info("ProfileSkillDao.getProfileByProfile method initializing");
        Session session = sessionFactory.getCurrentSession();
        String sql = "Select p From ProfileSkill p where profile = ?";
        Query query = session.createQuery(sql);
        query.setInteger(0, profileId);
        List profileSkills = query.list();
        return profileSkills;
        log.info("Returning ProfileSkills");
    }
}
