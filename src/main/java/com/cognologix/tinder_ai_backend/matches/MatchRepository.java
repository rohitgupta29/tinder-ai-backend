package com.cognologix.tinder_ai_backend.matches;

import com.cognologix.tinder_ai_backend.conversations.Conversation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MatchRepository extends MongoRepository<Match, String> {

}
