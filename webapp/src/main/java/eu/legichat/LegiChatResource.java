package eu.legichat;

import eu.legichat.chatbot.ChatbotService;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/chatbot")
public class LegiChatResource {

    @Inject
    ChatbotService legifranceAIService;

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String submitQuestion(String question) {
        return legifranceAIService.answer(question);
    }

}
