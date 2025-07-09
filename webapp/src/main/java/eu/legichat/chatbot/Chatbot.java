package eu.legichat.chatbot;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService(
        toolProviderSupplier = LegiChatToolProviderSupplier.class,
        chatMemoryProviderSupplier = LegiChatMemoryProviderSupplier.class
)
@SystemMessage("Je suis un assistant juridique")
public interface Chatbot {

    String answer(@UserMessage String question, @MemoryId int memoryId);

}
