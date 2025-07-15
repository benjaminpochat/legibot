package fr.legichat.mcpserver;

import fr.legichat.service.LegifranceClientService;
import io.quarkiverse.mcp.server.Tool;
import io.quarkiverse.mcp.server.ToolArg;
import jakarta.inject.Inject;

import java.util.List;


public class LegifranceMCPServer {

    @Inject
    LegifranceClientService legifranceService;

    @Tool(description = "Récupère le contenu d'un article de la loi française à partir de son identifiant")
    String getArticle(
            @ToolArg(description = "identifiant d'un article de loi")
            String id) {
        return legifranceService.getArticle(id);
    }

    @Tool(description = """
            Recherche les textes de lois, d'arrêtés, de décrets, et d'ordonnance en vigueur en France à la date du jour.
            La recherche retourne les textes contenant tous les mots ou expressions passés en argument.
        """)
    List<String> searchInLODA(
        @ToolArg(description = "un liste de mots ou d'expressions recherchées")
        String... keywords) {
        return legifranceService.searchInLODA(keywords);
    }
}
