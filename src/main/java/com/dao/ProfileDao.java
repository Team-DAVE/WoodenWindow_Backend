package com.dao;

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
public class ProfileDao {
    SessionFactory sessionFactory;

    @Autowired
    public ProfileDao(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    public ProfileDao() {
    }

    @Transactional
    public int addProfile(String profileName, String resume, Users user) {
        System.out.println("made it to the profileDao, addProfile method");
        Profile newProfile = new Profile();
        newProfile.setProfileName(profileName);
        newProfile.setResume(resume);
        newProfile.setUser(user);
        System.out.println(newProfile);

        Session session = sessionFactory.getCurrentSession();
        int newProfileId = (int) session.save(newProfile);
        System.out.println(newProfileId);
        return newProfileId;
    }

    @Transactional
    public Profile getProfilebyProfileId(int profiledId) {
        System.out.println("made it to the profileDao, getProfilebyProfileId method");
        Session session = sessionFactory.getCurrentSession();
        String sql = "Select p From Profile p where profileId = ?";
        Query query = session.createQuery(sql);
        query.setInteger(0, profiledId);
        Profile profile = (Profile) query.uniqueResult();
        return profile;
    }

    @Transactional
    public List<Profile> getProfilesByUserId(int userId) {
        System.out.println("made it to the profileDao, getProfilesByUserId method");
        Session session = sessionFactory.getCurrentSession();
        String sql = "Select p From Profile p where users = ?";
        Query query = session.createQuery(sql);
        query.setInteger(0, userId);
        List profiles = query.list();
        return profiles;
    }
}
