package eu.legichat;

import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/chatbot")
public class LegiChatResource {

    @Inject
    LegifranceAIService europeanParliamentAIService;

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String submitQuestion(String question) {
        return europeanParliamentAIService.writeAPoem(question, 10);
    }

}
