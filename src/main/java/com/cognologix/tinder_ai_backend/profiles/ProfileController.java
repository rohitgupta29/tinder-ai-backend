package com.cognologix.tinder_ai_backend.profiles;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {

    @Autowired
    private ProfileRepository profileRepository;

    @GetMapping("/profiles/random")
    public Profile getRandomProfile() {
        return profileRepository.getRandomProfile();

        }
    }
