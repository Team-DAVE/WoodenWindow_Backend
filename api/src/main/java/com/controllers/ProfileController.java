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
        log.info("ProfileController.addProfile method invoked");
        if (newProfile == null) {
            log.info("newProfile is null");
            log.info("Returning ResponseEntity(Bad_REQUEST)");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else {
            log.info("newProfile is not null");
            String profileName = newProfile.getProfileName();
            String resume = newProfile.getResume();
            Users user = newProfile.getUser();
            Profile addedProfile = profileService.addProfile(profileName, resume, user);
            log.info("Returning ResponseEntity(CREATED)");
            return new ResponseEntity<>(addedProfile, HttpStatus.CREATED);
        }
    }

    /**
     * getProfileByProfileId method will retrieve the information from the database to be displayed on the view of the front-end
     */
    @GetMapping(path="{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Profile> getProfileByProfileId(@PathVariable int id) {
        log.info("ProfileController.getProfileByProfileId method invoked");
        Profile profile = profileService.getProfileByProfileId(id);
        log.info("Returning ResponseEntity(OK)");
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    /**
     * getProfilesByUserId method will retrieve the information from the database to be displayed on the view of the front-end
     */
    @GetMapping(path="/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<Profile>> getProfilesByUserId(@PathVariable int id) {
        log.info("ProfileController.getProfilesByUserId method invoked");
        List<Profile> profiles = profileService.getProfilesByUserId(id);
        System.out.println(profiles);
        log.info("Returning ResponseEntity(OK)");
        return new ResponseEntity<>(profiles, HttpStatus.OK);
    }
}
