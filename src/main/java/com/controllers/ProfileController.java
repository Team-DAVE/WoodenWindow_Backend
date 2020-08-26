package com.controllers;

import com.model.Profile;
import com.model.Users;
import com.service.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path="/profile")
public class ProfileController {
    private ProfileService profileService = new ProfileService();

    @PostMapping
    @ResponseBody
    public ResponseEntity<?> addProfile(@RequestBody Profile newProfile) throws URISyntaxException {
        System.out.println("ProfileController addProfile method reached");
        if (newProfile == null) {
            System.out.println("profile is empty");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else {
            System.out.println("Profile is good");
            String profileName = newProfile.getProfileName();
            String resume = newProfile.getResume();
            Users user = newProfile.getUser();
            Profile addedProfile = profileService.addProfile(profileName, resume, user);
            return new ResponseEntity<>(addedProfile, HttpStatus.CREATED);
        }
    }

    @GetMapping(path="{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Profile> getProfileByProfileId(@PathVariable int id) {
        System.out.println("ProfileController getProfileByProfileId method reached");
        Profile profile = profileService.getProfileByProfileId(id);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    @GetMapping(path="/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<Profile>> getProfilesByUserId(@PathVariable int id) {
        System.out.println("ProfileController getProfilesByUserId method reached");
        List<Profile> profiles = profileService.getProfilesByUserId(id);
        System.out.println(profiles);
        return new ResponseEntity<>(profiles, HttpStatus.OK);
    }
}
