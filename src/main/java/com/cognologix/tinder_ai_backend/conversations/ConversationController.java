package com.cognologix.tinder_ai_backend.conversations;


import com.cognologix.tinder_ai_backend.profiles.ProfileRepository;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class ConversationController {

    private final ConversationRepository conversationRepository;
    private final ProfileRepository profileRepository;

    public ConversationController(ConversationRepository conversationRepository,ProfileRepository profileRepository) {
        this.conversationRepository = conversationRepository;
        this.profileRepository = profileRepository;
    }

    @PostMapping("/conversations")
    public Conversation createNewConversation(@RequestBody CreateConversationRequest request) {

        // check a valid profile
        profileRepository.findById(request.profileId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Conversation conversation = new Conversation(UUID.randomUUID().toString(),request.profileId(), new ArrayList<>());

        conversationRepository.save(conversation);
        return conversation;
    }

    @GetMapping("/conversations/{conversationId}")
    public Conversation getConversation(
            @PathVariable String conversationId) {
        return conversationRepository.findById(conversationId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Unable to find conversation with the ID " + conversationId
                ));
    }

    @PostMapping("/conversations/{conversationId}")
    public Conversation addMessageToConversation(
            @PathVariable String conversationId,
            ChatMessage chatMessage) {

        // Verifying that the conversation exists
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Unable to find conversation with the ID " + conversationId
                ));
        // verifying that the author of that message exists
        profileRepository.findById(conversationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                        "Unable to find conversation with the ID" + chatMessage.authorId()
                ));

        // TODO: Need to validate that the author of a message happes to be only the profile associated with the user.

        // create a new message with time
        ChatMessage messageWithTime = new ChatMessage(
                chatMessage.messageText(),
                chatMessage.authorId(),
                LocalDateTime.now());

        // add a check message to conversation
        conversation.messages().add(chatMessage);
        conversationRepository.save(conversation);
        return conversation;


    }


    public record CreateConversationRequest(
            String profileId
    ){}

}


// revise 1:55:00 to practice API requests