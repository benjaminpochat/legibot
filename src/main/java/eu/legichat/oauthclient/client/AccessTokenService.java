package eu.legichat.oauthclient.client;

import eu.legichat.oauthclient.model.AccessToken;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class AccessTokenService {

    private static AccessToken ACCESS_TOKEN;

    @RestClient
    private OAuthClient oAuthClient;

    @ConfigProperty(name = "legifrance.oauthclient-api.client-id")
    private String clientId;

    @ConfigProperty(name = "legifrance.oauthclient-api.client-secret")
    private String clientSecret;


    public String getAccessToken() {
        if (ACCESS_TOKEN == null || ACCESS_TOKEN.isExpiredSoon()) {
            ACCESS_TOKEN = oAuthClient.getToken("client_credentials", clientId, clientSecret, "openid");
        }
        return ACCESS_TOKEN.accessToken();
    }
}
