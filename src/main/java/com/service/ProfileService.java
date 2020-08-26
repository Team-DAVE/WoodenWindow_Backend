package com.service;

import com.dao.ProfileDao;
import com.dao.UserDao;
import com.model.Profile;
import com.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {
    ProfileDao profileDao;
    UserDao userDao;

    @Autowired
    public ProfileService(ProfileDao profileDao, UserDao userDao) {
        this.profileDao = profileDao;
        this.userDao = userDao;
    }

    public ProfileService() { };

    public Profile addProfile(String profileName, String resume, Users user) {
        System.out.println("ProfileService addProfile method reached");
        AbstractApplicationContext ac = new ClassPathXmlApplicationContext("application-context.xml");
        ProfileService profileServiceBean = ac.getBean("profileService", ProfileService.class);
        int profileId = profileServiceBean.profileDao.addProfile(profileName, resume, user);
        if (profileId > 0) {
            Profile addedProfile = profileServiceBean.profileDao.getProfileByProfileId(profileId);
            ac.close();
            return addedProfile;
        }
        System.out.println("ProfileService method ended bad profileId");
        ac.close();
        return null;
    }

    public Profile getProfileByProfileId(int profileId) {
        System.out.println("ProfileService getProfileByProfileId method reached");
        AbstractApplicationContext ac = new ClassPathXmlApplicationContext("application-context.xml");
        ProfileService profileServiceBean = ac.getBean("profileService", ProfileService.class);
        Profile profile = profileServiceBean.profileDao.getProfileByProfileId(profileId);
        return  profile;
    }

    public List<Profile> getProfilesByUserId(int userId) {
        System.out.println("ProfileService getProfilesByUserId method reached");
        AbstractApplicationContext ac = new ClassPathXmlApplicationContext("application-context.xml");
        ProfileService profileServiceBean = ac.getBean("profileService", ProfileService.class);
        List<Profile> profiles = profileServiceBean.profileDao.getProfilesByUserId(userId);
        System.out.println(profiles);
        ac.close();
        return profiles;
    }
}
