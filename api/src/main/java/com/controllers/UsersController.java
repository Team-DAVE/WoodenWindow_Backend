// File: UsersController.java
// Controller class to communicate between frontend and backend
package com.controllers;

import com.model.Users;
import com.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.apache.log4j.Logger;

import java.util.List;
import java.net.URISyntaxException;

@Controller
@CrossOrigin(origins ="*", allowedHeaders = "*")
@RequestMapping(path="/user")
public class UsersController {
    private UserService userService = new UserService();
    static Logger log = Logger.getLogger(UsersController.class);

    /**
     * addUser method will retrieve the information from the request and check whether is it null or not. If not null,
     * The information will be set into newUser and run through the service and dao to be returned back to the controller
     * to determine whether the user has been added or not.
     */
    @PostMapping
    @ResponseBody
    public boolean addUser(@RequestBody Users newUser) throws URISyntaxException {
        log.info("UsersController.addUser method invoked");
        if (newUser.getEmail() == null) {
            log.info("addUser, getEmail from newUser is null");
            log.info("Returning false");
            return false;
        } else {
            log.info("addUser, getEmail from newUser is not null");
            String email = newUser.getEmail();
            String password = newUser.getPassword();
            String firstName = newUser.getFirstName();
            String lastName = newUser.getLastName();
            log.info("Returning userService.addUser");
            return userService.addUser(email, password, firstName, lastName);
        }
    }

    /**
     * login method will retrieve the information from the request and check whether is it null or not. If not null,
     * The information will be set into currentUser and run through the service and dao to be returned back to the controller
     * to determine whether the user has been logged in or not.
     */
    @PostMapping(path="/login")
    @ResponseBody
    public ResponseEntity<?> login(@RequestBody Users currentUser) throws URISyntaxException {
        log.info("UsersController.login method invoked");
        if (currentUser != null) {
            log.info("currentUser is not an Empty Statement");
            String email = currentUser.getEmail();
            String password = currentUser.getPassword();
            Users returnUser = userService.checkUser(email, password);
            log.info("Returning ResponseEntity(OK)");
            return new ResponseEntity<>(returnUser, HttpStatus.OK);
        } else {
            log.info("currentUser is an Empty Statement");
            log.info("Returning null");
            return null;
        }
    }
}
