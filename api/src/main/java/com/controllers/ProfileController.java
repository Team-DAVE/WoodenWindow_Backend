// File: ProfileController.java
// Controller class to communicate between frontend and backend
package com.controllers;

import com.model.Profile;
import com.model.Users;
import com.service.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.apache.log4j.Logger;

import java.net.URISyntaxException;
import java.util.List;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path="/profile")
public class ProfileController {
    private ProfileService profileService = new ProfileService();
    static Logger log = Logger.getLogger(ProfileController.class);

    /**
     * addProfile method will retrieve the information from the request and check whether is it null or not. If not null,
     * The information will be set into newProfile and run through the service and dao to be returned back to the controller
     * for it to go to the front end side.
     */
    @PostMapping
    @ResponseBody
    public ResponseEntity<?> addProfile(@RequestBody Profile newProfile) throws URISyntaxException {
        log.info("ProfileController, addProfile method successfully reached");
        if (newProfile == null) {
            log.info("newProfile is null");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else {
            log.info("newProfile is not null");
            String profileName = newProfile.getProfileName();
            String resume = newProfile.getResume();
            Users user = newProfile.getUser();
            Profile addedProfile = profileService.addProfile(profileName, resume, user);
            return new ResponseEntity<>(addedProfile, HttpStatus.CREATED);
            log.info("ResponseEntity-AddedProfile successfully returned");
        }
    }

    /**
     * getProfileByProfileId method will retrieve the information from the database to be displayed on the view of the front-end
     */
    @GetMapping(path="{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Profile> getProfileByProfileId(@PathVariable int id) {
        log.info("getProfileByProfileId method has successfully been reached");
        Profile profile = profileService.getProfileByProfileId(id);
        return new ResponseEntity<>(profile, HttpStatus.OK);
        log.info("ResponseEntity-Profile successfully returned");
    }

    /**
     * getProfilesByUserId method will retrieve the information from the database to be displayed on the view of the front-end
     */
    @GetMapping(path="/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<Profile>> getProfilesByUserId(@PathVariable int id) {
        log.info("ProfileController getProfilesByUserId method successfully reached");
        List<Profile> profiles = profileService.getProfilesByUserId(id);
        System.out.println(profiles);
        return new ResponseEntity<>(profiles, HttpStatus.OK);
        log.info("ResponseEntity-Profiles successfully returned");
    }
}
