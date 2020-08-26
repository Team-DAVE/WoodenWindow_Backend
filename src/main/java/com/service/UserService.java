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
//        Users userWithoutPassword;
        }
        else {
            System.out.println("Password is not the same");
            ac.close();
        }
        return null;
    }

    public List<Users> getUsers() {
        System.out.println("service method get users invoked");
        AbstractApplicationContext ac = new ClassPathXmlApplicationContext("WEB-INF/application-context.xml");
        UserService userServiceBean = ac.getBean( "userService", UserService.class);
        List<Users> users = userServiceBean.userDao.findAll();
        System.out.println(users);
        ac.close();
        return users;
    }
}
