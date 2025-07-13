package fr.legichat.chatbot;

import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.function.Supplier;

@ApplicationScoped
public class LegiChatMemoryProviderSupplier implements Supplier<ChatMemoryProvider> {

    @Override
    public ChatMemoryProvider get() {
        return memoryId -> MessageWindowChatMemory.withMaxMessages(10);
    }
}
