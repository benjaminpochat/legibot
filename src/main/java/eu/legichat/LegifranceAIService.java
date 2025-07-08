package eu.legichat;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import jakarta.enterprise.context.ApplicationScoped;

@RegisterAiService
@ApplicationScoped
@SystemMessage("You are a legal assistant")
public interface LegifranceAIService {

    @UserMessage("")
    String answer(String question);

}
