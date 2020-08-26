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

import java.util.List;

@Repository
@Transactional
public class ProfileSkillDao {
    SessionFactory sessionFactory;

    @Autowired
    public ProfileSkillDao(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    public ProfileSkillDao() {
    }

    @Transactional
    public void addProfileSkill(String profileSkillName, Profile profile) {
        System.out.println("made it to the profileSkillDao, addProfileSkill method");
        ProfileSkill newProfileSkill = new ProfileSkill();
        newProfileSkill.setProfileSkillName(profileSkillName);
        newProfileSkill.setProfile(profile);
        System.out.println(newProfileSkill);

        Session session = sessionFactory.getCurrentSession();
        session.save(newProfileSkill);
    }

    @Transactional
    public List<ProfileSkill> getProfileSkillsByProfile(int profileId) {
        System.out.println("made it to the profileSkillDao, getProfileSkillsByProfile method");
        Session session = sessionFactory.getCurrentSession();
        String sql = "Select p From ProfileSkill p where profile = ?";
        Query query = session.createQuery(sql);
        query.setInteger(0, profileId);
        List profileSkills = query.list();
        return profileSkills;
    }
}
