package com.driver;

import com.dao.*;
import com.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Java {
    UserDao userDao;
    ProfileDao profileDao;
    ProfileSkillDao profileSkillDao;
    BusinessDao businessDao;
    JobPostingDao jobPostingDao;
    RequiredSkillDao requiredSkillDao;

    @Autowired
    public Java(UserDao userDao, ProfileDao profileDao, ProfileSkillDao profileSkillDao, BusinessDao businessDao,
                JobPostingDao jobPostingDao, RequiredSkillDao requiredSkillDao) {
        this.userDao = userDao;
        this.profileDao = profileDao;
        this.profileSkillDao = profileSkillDao;
        this.businessDao = businessDao;
        this.jobPostingDao = jobPostingDao;
        this.requiredSkillDao = requiredSkillDao;
    }

//    @Autowired
//    public Java(UserService userService) {
//
//        this.userService = userService;
//    }
    public static void main(String[] args) {
        AbstractApplicationContext ac = new ClassPathXmlApplicationContext("application-context.xml");
        Java javaBean = ac.getBean( "java",Java.class);
        Users userOne = new Users();
        userOne.setEmail("testtwo@host.com");
        userOne.setPassword("password");
        userOne.setFirstName("Test");
        userOne.setLastName("Two");
        javaBean.userDao.addUser(userOne.getEmail(), userOne.getPassword(), userOne.getFirstName(), userOne.getLastName());
        Users userWithAllInfo = javaBean.userDao.findUserByEmail(userOne.getEmail());
        javaBean.profileDao.addProfile("Big Time Timmy Jim", "This is a resume.", userWithAllInfo);
        List<Profile> profiles = javaBean.profileDao.getProfilesByUserId(userWithAllInfo.getUserId());
        Profile firstProfile = profiles.get(0);
        System.out.println(profiles);
        javaBean.profileSkillDao.addProfileSkill("Awkward comments", firstProfile);
        System.out.println(javaBean.profileSkillDao.getProfileSkillsByProfile(firstProfile.getProfileId()));
        javaBean.businessDao.addBusiness("My First Business", "Last Chance Blvd, Heartbreak CA", "WE are your last chance for love", userWithAllInfo);
        System.out.println(javaBean.businessDao.getBusinessesByUserId(userWithAllInfo.getUserId()));
        Business firstBusiness = javaBean.businessDao.getBusinessesByUserId(userWithAllInfo.getUserId()).get(0);
        javaBean.jobPostingDao.addJobPosting("Intern", "You will get coffee", "Based on experience", firstBusiness);
        System.out.println(javaBean.jobPostingDao.getJobPostingsByBusiness(firstBusiness.getBusinessId()));
        JobPosting firstJobPosting = javaBean.jobPostingDao.getJobPostingsByBusiness(firstBusiness.getBusinessId()).get(0);
        javaBean.requiredSkillDao.addRequiredSkill("Java", firstJobPosting);
        System.out.println(javaBean.requiredSkillDao.getRequiredSkillsByJobPosting(firstJobPosting.getJobPostingId()));
        ac.close();
    }
}
