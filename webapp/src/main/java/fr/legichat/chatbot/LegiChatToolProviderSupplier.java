package fr.legichat.chatbot;

import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.mcp.client.DefaultMcpClient;
import dev.langchain4j.mcp.client.McpClient;
import dev.langchain4j.mcp.client.logging.McpLogMessageHandler;
import dev.langchain4j.mcp.client.transport.McpTransport;
import dev.langchain4j.mcp.client.transport.http.HttpMcpTransport;
import dev.langchain4j.mcp.client.transport.stdio.StdioMcpTransport;
import dev.langchain4j.service.tool.ToolProvider;
import io.quarkiverse.langchain4j.mcp.runtime.QuarkusDefaultMcpLogHandler;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.function.Supplier;

@ApplicationScoped
public class LegiChatToolProviderSupplier implements Supplier<ToolProvider> {

    @Override
    public ToolProvider get() {

        Log.debug("LegiFrance Tool initialization is starting.");

        McpLogMessageHandler logMessageHandler = new QuarkusDefaultMcpLogHandler("legifrance-client");

        McpTransport mcpSseTransport= new HttpMcpTransport.Builder()
            .sseUrl("http://localhost:8081/mcp/sse")
            .logRequests(true)
            .logResponses(true)
            .build();

        McpClient mcpClient = new DefaultMcpClient.Builder()
                .key("MCPLegifranceClient")
                .logHandler(logMessageHandler)
                .transport(mcpSseTransport)
                .build();

        McpToolProvider toolProvider = McpToolProvider.builder()
                .mcpClients(mcpClient)
                .build();

        Log.debug("LegiFrance Tool initialization is finished.");

        return toolProvider;
    }
}
