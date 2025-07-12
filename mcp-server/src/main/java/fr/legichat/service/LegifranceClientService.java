package fr.legichat.service;


import fr.legichat.legifrance.client.api.ConsultControllerApi;
import fr.legichat.legifrance.client.model.ArticleRequest;
import fr.legichat.legifrance.client.model.GetArticleResponse;
import jakarta.inject.Inject;

public class LegifranceClientResource {

  @Inject
  ConsultControllerApi api;

  public void getArticle() {
    ArticleRequest request = null;
    GetArticleResponse article = api.getArticleUsingPOST(request);
  }
}
