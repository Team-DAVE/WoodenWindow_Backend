// File: UserService.java
// Service class acts as a layer communicating between controller and DAO
package com.service;

import com.dao.UserDao;
import com.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    UserDao userDao;

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
        System.out.println("service method beggining");
        AbstractApplicationContext ac = new ClassPathXmlApplicationContext("application-context.xml");
        UserService userServiceBean = ac.getBean("userService", UserService.class);
        if (userServiceBean.userDao.addUser(email, password, firstName, lastName)) {
            System.out.println("after success dao call in service");
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
        System.out.println("checkUser method beginning");
        AbstractApplicationContext ac = new ClassPathXmlApplicationContext("application-context.xml");
        UserService userServiceBean = ac.getBean("userService", UserService.class);
        Users user = userServiceBean.userDao.findUserByEmail(email);
        System.out.println(user);
        System.out.println(password);
        if (user.getPassword().equals(password)) {
            System.out.println("password is the same");
            user.getUserId();
            user.getEmail();
            user.getFirstName();
            user.getLastName();
            ac.close();
            return user;
        }
        else {
            System.out.println("Password is not the same");
            ac.close();
        }
        return null;
    }

}
