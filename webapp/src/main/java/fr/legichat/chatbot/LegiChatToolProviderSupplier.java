package eu.legichat.chatbot;

import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.mcp.client.DefaultMcpClient;
import dev.langchain4j.mcp.client.McpClient;
import dev.langchain4j.mcp.client.transport.McpTransport;
import dev.langchain4j.mcp.client.transport.stdio.StdioMcpTransport;
import dev.langchain4j.service.tool.ToolProvider;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.function.Supplier;

@ApplicationScoped
public class LegiChatToolProviderSupplier implements Supplier<ToolProvider> {

    @Override
    public ToolProvider get() {
        McpTransport mcpTransport = new StdioMcpTransport.Builder()
                .command(
                        List.of("java", "-jar", "/home/benjamin/Documents/workspace/legichat/mcp-server/target/quarkus-app/quarkus-run.jar"))
                .logEvents(true)
                .build();

        McpClient mcpClient = new DefaultMcpClient.Builder()
                .key("MCPLegifranceClient")
                .transport(mcpTransport)
                .build();

        McpToolProvider toolProvider = McpToolProvider.builder()
                .mcpClients(mcpClient)
                .build();

        return toolProvider;
    }
}
