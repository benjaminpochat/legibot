package eu.legichat.chatbot;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ChatbotService {

    @Inject
    Chatbot chatbot;

    public String answer(String question) {
        return chatbot.answer(question, 1);
    }
}
