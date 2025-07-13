package fr.legichat.service;


import fr.legichat.legifrance.client.api.ConsultControllerApi;
import fr.legichat.legifrance.client.model.ArticleRequest;
import fr.legichat.legifrance.client.model.GetArticleResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class LegifranceClientService {

  @RestClient
  @Inject
  ConsultControllerApi api;

  public String getArticle(String id) {
    ArticleRequest request = new ArticleRequest();
    request.id(id);
    GetArticleResponse response = api.getArticleUsingPOST(request);
    return response.getArticle().getTexte();
  }
}
