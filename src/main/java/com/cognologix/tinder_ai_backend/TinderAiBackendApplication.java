package com.cognologix.tinder_ai_backend;

import com.cognologix.tinder_ai_backend.profiles.Gender;
import com.cognologix.tinder_ai_backend.profiles.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.cognologix.tinder_ai_backend.profiles.Profile;

@SpringBootApplication
public class TinderAiBackendApplication {

	@Autowired
	private ProfileRepository profileRepository;

	public static void main(String[] args) {

		SpringApplication.run(TinderAiBackendApplication.class, args);
	}

	public void run(String... args) {
		Profile profile = new Profile(
				"1",
				"Rohit",
				"Gupta",
				34,
				"Indian",
				Gender.MALE,
				"Software Programmer",
				 "foo.img",
				"INTP"
		);
		profileRepository.save(profile);
		System.out.println("My app is running");
		profileRepository.findAll().forEach(System.out::println);
//
	}
}
