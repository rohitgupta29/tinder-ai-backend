package com.cognologix.tinder_ai_backend.matches;


import com.cognologix.tinder_ai_backend.conversations.Conversation;
import com.cognologix.tinder_ai_backend.conversations.ConversationController;
import com.cognologix.tinder_ai_backend.conversations.ConversationRepository;
import com.cognologix.tinder_ai_backend.profiles.Profile;
import com.cognologix.tinder_ai_backend.profiles.ProfileRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class MatchController {

    private final ConversationRepository conversationRepository;
    private final ProfileRepository profileRepository;
    private final MatchRepository matchRepository;

    public MatchController(ConversationRepository conversationRepository, ProfileRepository profileRepository, com.cognologix.tinder_ai_backend.matches.MatchRepository matchRepository, MatchRepository matchRepository1) {
        this.conversationRepository = conversationRepository;
        this.profileRepository = profileRepository;
        this.matchRepository = matchRepository1;
    }

    public record createMatchRequest (String profileId) {}

    @PostMapping("/matches")
    public Match createNewMatch(@RequestBody CreateMatchRequest request) {

        // check a valid profile
        Profile profile = profileRepository.findById(request.profileId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find a profile with ID " + request.profileId()));

        Conversation conversation = new Conversation(
                UUID.randomUUID().toString(),
                profile.id(),
                new ArrayList<>());

        // TODO: Make sure there are no existing conversations with this profile already.
        conversationRepository.save(conversation);
        Match match = new Match(
                UUID.randomUUID().toString(),
                profile,
                conversation.id());

        matchRepository.save(match);
        return match;
    }

    @GetMapping("/matches")
    public List<Match> getAllMatches() {
        return matchRepository.findAll();
    }

    public record CreateMatchRequest(
            String profileId
    ){}



}
