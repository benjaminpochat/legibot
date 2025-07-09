package eu.legichat.mcpserver;

import eu.legichat.legifrance.client.LegifranceService;
import io.quarkiverse.mcp.server.Tool;
import io.quarkiverse.mcp.server.ToolArg;
import jakarta.inject.Inject;


public class LegifranceMCPServer {

    @Inject
    LegifranceService legifranceService;

    @Tool(description = "Récupère le contenu d'un article de la loi française à partir de son identifiant")
    String getArticle(
            @ToolArg(description = "identifiant d'un article de loi")
            String id) {
        return legifranceService.getArticle(id);
    }
}
