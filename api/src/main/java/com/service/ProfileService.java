// File: ProfileService.java
// Service class acts as a layer communicating between controller and DAO
package com.service;

import com.dao.ProfileDao;
import com.dao.UserDao;
import com.model.Profile;
import com.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;

import java.util.List;

@Service
public class ProfileService {
    ProfileDao profileDao;
    UserDao userDao;
    static Logger log = Logger.getLogger(ProfileService.class);

    @Autowired
    public ProfileService(ProfileDao profileDao, UserDao userDao) {
        this.profileDao = profileDao;
        this.userDao = userDao;
    }

    public ProfileService() { };

    /**
     * addProile method instantiates an ApplicationContext and communicates with the profileDao by calling upon addProfile
     *  The returned int from the profile DAO will be checked here to see if the profileId is appropriate. If it is good,
     *  will return a profile to give back to the controller, else null.
     */
    public Profile addProfile(String profileName, String resume, Users user) {
        log.info("ProfileService.addProfile method invoked");
        AbstractApplicationContext ac = new ClassPathXmlApplicationContext("application-context.xml");
        ProfileService profileServiceBean = ac.getBean("profileService", ProfileService.class);
        int profileId = profileServiceBean.profileDao.addProfile(profileName, resume, user);
        if (profileId > 0) {
            log.info("profileId is greated than zero");
            Profile addedProfile = profileServiceBean.profileDao.getProfileByProfileId(profileId);
            ac.close();
            log.info("Returing addedProfile");
            return addedProfile;
        }
        log.info("ProfileService.addProfile method ended with bad ProfileId");
        ac.close();
        log.info("Returning null");
        return null;
    }

    /**
     * getProfileByProfileId method instantiates an ApplicationContext and communicates with the profileDao by calling
     * getProfileByProfileId which will end up returning the user's profile
     */
    public Profile getProfileByProfileId(int profileId) {
        log.info("ProfileService.getProfileByProfileId method invoked");
        AbstractApplicationContext ac = new ClassPathXmlApplicationContext("application-context.xml");
        ProfileService profileServiceBean = ac.getBean("profileService", ProfileService.class);
        Profile profile = profileServiceBean.profileDao.getProfileByProfileId(profileId);
        log.info("Returning profile");
        return profile;
    }

    /**
     * getProfileByUserId method instantiates an ApplicationContext and communicates with the profileDao by calling
     * getProfileByUserId which will end up returning the user's profile
     */
    public List<Profile> getProfilesByUserId(int userId) {
        log.info("ProfileService.getProfilesByUserId method invoked");
        AbstractApplicationContext ac = new ClassPathXmlApplicationContext("application-context.xml");
        ProfileService profileServiceBean = ac.getBean("profileService", ProfileService.class);
        List<Profile> profiles = profileServiceBean.profileDao.getProfilesByUserId(userId);
        System.out.println(profiles);
        ac.close();
        log.info("Returning profiles");
        return profiles;
    }
}
