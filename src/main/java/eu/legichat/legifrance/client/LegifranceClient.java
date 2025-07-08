package eu.legichat.legifrance.client;

import eu.legichat.legifrance.model.ArticleRequest;
import eu.legichat.legifrance.model.GetArticleResponse;
import io.quarkus.logging.Log;
import io.quarkus.rest.client.reactive.ClientExceptionMapper;
import io.quarkus.rest.client.reactive.NotBody;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey="legifrance-api")
public interface LegifranceClient {

    @POST
    @Path("/consult/getArticle")
    @ClientHeaderParam(name = "Authorization", value = "Bearer {accessToken}")
    GetArticleResponse getArticle(ArticleRequest articleRequest, @NotBody String accessToken);

    @ClientExceptionMapper
    static RuntimeException toException(Response response) {
        Log.error("Legifrance API error response : " + response.toString());
        return new RuntimeException("Legifrance API throw error code " + response.getStatus());
    }
}
