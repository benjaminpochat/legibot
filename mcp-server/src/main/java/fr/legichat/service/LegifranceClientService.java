package fr.legichat.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.legichat.legifrance.client.api.ConsultControllerApi;
import fr.legichat.legifrance.client.api.SearchControllerApi;
import fr.legichat.legifrance.client.model.*;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class LegifranceClientService {

  @RestClient
  @Inject
  ConsultControllerApi consultControllerApi;

  @RestClient
  @Inject
  SearchControllerApi searchControllerApi;

  @Inject
  ObjectMapper objectMapper;

  public String getArticle(String id) {
    ArticleRequest request = new ArticleRequest();
    request.id(id);
    GetArticleResponse response = consultControllerApi.getArticleUsingPOST(request);
    return response.getArticle().getTexte();
  }

  public String getLEGIText(String cid) {
    Log.debug("Start getLEGIText for cid %s".formatted(cid));
    LegiConsultRequest legiConsultRequest = new LegiConsultRequest();
    legiConsultRequest.setTextId(cid);
    legiConsultRequest.setDate(OffsetDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
    try {
      ConsultTextResponse result = consultControllerApi.displayLegiPartUsingPOST(legiConsultRequest);
      String title = result.getTitle();
      String content = result.getArticles().stream()
          .filter(article -> article.getEtat().equals("VIGUEUR"))
          .map(article -> "\n\nArticle " + article.getNum() + "\n\n" + article.getContent())
          .collect(Collectors.joining());
      content += result.getSections().stream()
          .filter(section -> section.getEtat().equals("VIGUEUR"))
          .flatMap(section -> section.getArticles().stream())
          .filter(article -> article.getEtat().equals("VIGUEUR"))
          .map(article -> "\n\nArticle " + article.getNum() + "\n\n" + article.getContent())
          .collect(Collectors.joining());
      String collectedLEGIText = title + "\n\n" + content;
      Log.debug("Stop getLEGIText for cid %s".formatted(cid));
      Log.debug("Text collected for cid %s : %s".formatted(cid, collectedLEGIText));
      return collectedLEGIText;
    } catch (Exception e) {
      Log.error("Error at consultControllerApi.displayLegiPartUsingPOST", e);
      throw e;
    }
  }

  /**
   * LODA pour Lois, Ordonnances, Décrêts, Arrêtés
   * @param keyWords
   * @return
   */
  public List<String> searchInLODA(String... keyWords) {
    Log.debug("Start searchInLODA for keywords %s".formatted(Arrays.stream(keyWords).collect(Collectors.joining(", ", "\"", "\""))));

    SearchRequestDTO request = new SearchRequestDTO();
    request.setFond(SearchRequestDTO.FondEnum.LODA_DATE);
    request.setRecherche(getRechercheSpecifiqueDTO(keyWords));

    SearchResponseDTO response = searchControllerApi.searchUsingPOST(request);
    List<SearchResult> results = response.getResults();
    var texts = results.stream()
        .map(result -> {
          try {
            Thread.sleep(500);
          } catch (InterruptedException ex) {
            return null;
          }
          return result.getTitles().get(0);
        })
        .map(SearchTitle::getTitle)
        .toList();
    Log.debug("Stop searchInLODA for keywords %s".formatted(Arrays.stream(keyWords).collect(Collectors.joining(", ", "\"", "\""))));
    return texts;
  }

  private static RechercheSpecifiqueDTO getRechercheSpecifiqueDTO(String[] keyWords) {
    RechercheSpecifiqueDTO rechercheSpecifiqueDTO = new RechercheSpecifiqueDTO();
    addKeyWordsToRechercheSpecifiqueDTO(keyWords, rechercheSpecifiqueDTO);
    addCurrentDateFilterToRechercheSpecifiqueDTO(rechercheSpecifiqueDTO);
    addStatusToRechercheSpecifiqueDTO(rechercheSpecifiqueDTO);
    return rechercheSpecifiqueDTO;
  }

  private static void addStatusToRechercheSpecifiqueDTO(RechercheSpecifiqueDTO rechercheSpecifiqueDTO) {
    FiltreDTO filtreStatutTexteEnVigueur = new FiltreDTO();
    filtreStatutTexteEnVigueur.setFacette("TEXT_LEGAL_STATUS");
    filtreStatutTexteEnVigueur.addValeursItem("VIGUEUR");
    rechercheSpecifiqueDTO.addFiltresItem(filtreStatutTexteEnVigueur);
  }

  private static void addCurrentDateFilterToRechercheSpecifiqueDTO(RechercheSpecifiqueDTO rechercheSpecifiqueDTO) {
    FiltreDTO filtreDateDujour = new FiltreDTO();
    filtreDateDujour.setFacette("DATE_VERSION");
    filtreDateDujour.setSingleDate(OffsetDateTime.now());
    rechercheSpecifiqueDTO.addFiltresItem(filtreDateDujour);
  }

  private static void addKeyWordsToRechercheSpecifiqueDTO(String[] keyWords, RechercheSpecifiqueDTO rechercheSpecifiqueDTO) {
    ChampDTO champDTO = new ChampDTO();
    champDTO.setOperateur(ChampDTO.OperateurEnum.ET);
    champDTO.setTypeChamp(ChampDTO.TypeChampEnum.ALL);
    Arrays.stream(keyWords).map(keyWord -> {
      CritereDTO critereDTO = new CritereDTO();
      critereDTO.setValeur(keyWord);
      critereDTO.setOperateur(CritereDTO.OperateurEnum.ET);
      critereDTO.setTypeRecherche(CritereDTO.TypeRechercheEnum.TOUS_LES_MOTS_DANS_UN_CHAMP);
      critereDTO.setProximite(2);
      return critereDTO;
    }).forEach(champDTO::addCriteresItem);
    rechercheSpecifiqueDTO.addChampsItem(champDTO);
  }

}
