package eu.legichat.oauthclient.client;

import eu.legichat.oauthclient.model.AccessToken;
import io.quarkus.logging.Log;
import io.quarkus.rest.client.reactive.ClientExceptionMapper;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey="oauthclient-api")
public interface OAuthClient {

    @POST
    @Path("/oauth/token")
    @ClientHeaderParam(name="Content-Type", value= MediaType.APPLICATION_FORM_URLENCODED)
    AccessToken getToken(@FormParam("grant_type") String grantType,
                         @FormParam("client_id") String clientId,
                         @FormParam("client_secret") String clientSecret,
                         @FormParam("scope") String scope);

    @ClientExceptionMapper
    static RuntimeException toException(Response response) {
        Log.error("OAuth API error response : " + response.toString());
        return new RuntimeException("OAuth API throw error code " + response.getStatus());
    }

}
