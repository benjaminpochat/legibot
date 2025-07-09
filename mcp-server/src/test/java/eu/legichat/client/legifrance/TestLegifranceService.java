package eu.legichat.client.legifrance;

import eu.legichat.legifrance.client.LegifranceService;
import io.quarkus.logging.Log;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class TestLegifranceService {

    @Inject
    LegifranceService legifranceService;

    @Test
    public void getArticle_should_return_the_article_text() {
        // when
//        String articleText = legifranceService.getArticle("JORFARTI000043673261");
        String articleText = legifranceService.getArticle("L.162-22-6");

        // then
        Assertions.assertEquals(
                "Pour les établissements de santé mentionnés aux a, b et c de l'article L. 162-22-6 du code de la sécurité sociale , la transmission des données d'activité mentionnées à l'article 6 du présent arrêté, la valorisation des données et la détermination des montants fixés en application de l'article 3 du présent arrêté s'effectuent dans les conditions définies respectivement aux articles 2, 3 et 5 de l'arrêté du 23 janvier 2008 du même code modifié susvisé. Pour les établissements de santé mentionnés au d de l'article L. 162-22-6 du code de la sécurité sociale , le versement du forfait mentionné à l'article 3 du présent arrêté s'effectue dans les conditions définies aux articles R. 174-17 et suivants de ce code.",
                articleText);
    }
}
