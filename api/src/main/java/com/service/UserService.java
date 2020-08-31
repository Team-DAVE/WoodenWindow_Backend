// File: UserService.java
// Service class acts as a layer communicating between controller and DAO
package com.service;

import com.dao.UserDao;
import com.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;

import java.util.List;

@Service
public class UserService {
    UserDao userDao;
    static Logger log = Logger.getLogger(UserService.class);

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserService() { };

    /**
     * addUser method instantiates an ApplicationContext and communicates with the UserDao by calling upon addUser
     * which will return a boolean to give back to the controller to determine whether it will add user or be stopped.
     */
    public boolean addUser(String email, String password, String firstName, String lastName) {
        log.info("UserService.addUser method invoked");
        AbstractApplicationContext ac = new ClassPathXmlApplicationContext("application-context.xml");
        UserService userServiceBean = ac.getBean("userService", UserService.class);
        if (userServiceBean.userDao.addUser(email, password, firstName, lastName)) {
            log.info("UserService.addUser after success Dao call in service");
            ac.close();
            return true;
        }
        else {
            ac.close();
            return false;
        }
    }

    /**
     * checkUser method instantiates an ApplicationContext and communicates with the UserDao by calling findUserByEmail
     * which will return a user back and check whether the given email and password is correct for credentials. If it is
     * correct, then the user information will be returned to the controller. If not, it will return null.
     */
    public Users checkUser(String email, String password) {
        log.info("UserService.checkUser method invoked");
        AbstractApplicationContext ac = new ClassPathXmlApplicationContext("application-context.xml");
        UserService userServiceBean = ac.getBean("userService", UserService.class);
        Users user = userServiceBean.userDao.findUserByEmail(email);
        System.out.println(user);
        System.out.println(password);
        if (user.getPassword().equals(password)) {
            log.info("UserService.checkUser correct password provided");
            user.getUserId();
            user.getEmail();
            user.getFirstName();
            user.getLastName();
            ac.close();
            return user;
            log.info("Returning user");
        }
        else {
            log.info("Incorrect password provided");
            ac.close();
        }
        return null;
        log.info("Returning null");
    }

}
