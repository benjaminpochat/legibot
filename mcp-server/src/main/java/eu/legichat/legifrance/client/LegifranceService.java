package eu.legichat.legifrance.client;

import eu.legichat.legifrance.model.ArticleRequest;
import eu.legichat.legifrance.model.GetArticleResponse;
import eu.legichat.oauthclient.client.AccessTokenService;
import eu.legichat.oauthclient.client.OAuthClient;
import eu.legichat.oauthclient.model.AccessToken;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class LegifranceService {

    @Inject
    AccessTokenService accessTokenService;

    @RestClient
    LegifranceClient legifranceClient;


    public String getArticle(String id) {
        ArticleRequest request = new ArticleRequest(id);
        String accessToken = accessTokenService.getAccessToken();
        GetArticleResponse response = legifranceClient.getArticle(request, accessToken);
        return response.article().texte();
    }
}
