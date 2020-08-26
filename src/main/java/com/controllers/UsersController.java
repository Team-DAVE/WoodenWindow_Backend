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

import java.util.List;
import java.net.URISyntaxException;

@Controller
@CrossOrigin(origins ="*", allowedHeaders = "*")
@RequestMapping(path="/user")
public class UsersController {
    private UserService userService = new UserService();

    /**
     * addUser method will retrieve the information from the request and check whether is it null or not. If not null,
     * The information will be set into newUser and run through the service and dao to be returned back to the controller
     * to determine whether the user has been added or not.
     */
    @PostMapping
    @ResponseBody
    public boolean addUser(@RequestBody Users newUser) throws URISyntaxException {
        System.out.println("addUser controller called");
        if (newUser.getEmail() == null) {
            System.out.println("user is empty");
            return false;
        } else {
            System.out.println("after else statement");
            String email = newUser.getEmail();
            String password = newUser.getPassword();
            String firstName = newUser.getFirstName();
            String lastName = newUser.getLastName();
            return userService.addUser(email, password, firstName, lastName);
            //  return new ResponseEntity<>("success", HttpStatus.CREATED);
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
        System.out.println("Login controller called");
        if (currentUser != null) {
            System.out.println("User is not empty statement");
            String email = currentUser.getEmail();
            String password = currentUser.getPassword();
            Users returnUser = userService.checkUser(email, password);
            return new ResponseEntity<>(returnUser, HttpStatus.OK);
        } else {

            System.out.println("user is empty");
            return null;
        }
    }
}
