package com.cognologix.tinder_ai_backend;


import com.cognologix.tinder_ai_backend.conversations.ChatMessage;
import com.cognologix.tinder_ai_backend.conversations.Conversation;
import com.cognologix.tinder_ai_backend.conversations.ConversationRepository;
import com.cognologix.tinder_ai_backend.profiles.Gender;
import com.cognologix.tinder_ai_backend.profiles.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.cognologix.tinder_ai_backend.profiles.Profile;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class TinderAiBackendApplication implements CommandLineRunner {

	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
	private ConversationRepository conversationRepository;

	public static void main(String[] args) {

		SpringApplication.run(TinderAiBackendApplication.class, args);
	}

	public void run(String... args) {

		profileRepository.deleteAll();
		conversationRepository.deleteAll();
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
		profile = new Profile("2",
				"Mohit",
				"Gupta",
				32,
				"Indian",
				Gender.MALE,
				"Software Programmer",
				"foo.img",
				"INTP");
		profileRepository.save(profile);
		profileRepository.findAll().forEach(System.out::println);

		Conversation conversation = new Conversation("1",profile.id(), List.of(
				new ChatMessage("hello",profile.id(), LocalDateTime.now())));

		conversationRepository.save(conversation);
		conversationRepository.findAll().forEach(System.out::println);
	}
}
