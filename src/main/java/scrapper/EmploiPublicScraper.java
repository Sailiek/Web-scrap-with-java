package scrapper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmploiPublicScraper implements WebScraper {

    // Méthode pour récupérer les liens des emplois depuis la page principale
    private List<String> getJobLinksFromMainPage() {
        List<String> jobLinks = new ArrayList<>();
        int numberOfPages = 3;  // Nombre de pages à scrapper

        try {
            String baseUrl = "https://www.emploi-public.ma/fr/concoursListe.asp?c=0&e=0&page=";
            for (int page = 1; page <= numberOfPages; page++) {
                String url = baseUrl + page;

                // Charger la page principale
                Document doc = Jsoup.connect(url).get();

                // Sélectionner les lignes du tableau des emplois
                Elements rows = doc.select("table tr");  // Chaque ligne du tableau (<tr>)

                // Parcourir les lignes pour récupérer les liens des colonnes "Grade"
                for (Element row : rows) {
                    Elements columns = row.select("td");
                    if (columns.size() >= 2) { // Vérifie qu'il y a au moins 2 colonnes
                        // Récupérer le lien de la colonne "Grade"
                        Element gradeLink = columns.get(1).selectFirst("a");
                        if (gradeLink != null) {
                            String relativeLink = gradeLink.attr("href");
                            // Compléter le lien avec le domaine principal
                            String fullLink = "https://www.emploi-public.ma/fr" + (relativeLink.startsWith("/") ? relativeLink : "/" + relativeLink);

                            jobLinks.add(fullLink);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jobLinks;
    }

    // Méthode pour récupérer les détails d'un emploi
    private String scrapeJobDetails(String jobUrl) {
        StringBuilder result = new StringBuilder();

        try {
            Document doc = Jsoup.connect(jobUrl).get();

            // Sélectionner toutes les lignes du tableau contenant les informations
            Elements rows = doc.select("table tr");

            // Liste des titres à extraire
            String[] titlesToExtract = {
                    "Grade", "Spécialités", "Type de recrutement", "Nombre de postes",
                    "Date de publication", "Délai de dépôt des candidatures", "Date du concours"
            };

            // Variable pour vérifier si au moins une ligne correspond à un titre
            boolean dataFound = false;

            // Parcourir chaque ligne du tableau
            for (Element row : rows) {
                Elements columns = row.select("th, td");

                // Vérifier qu'il y a bien deux colonnes : une pour le titre et l'autre pour l'information
                if (columns.size() == 2) {
                    String titre = columns.get(0).text();  // Colonne 1 : titre
                    String information = columns.get(1).text();  // Colonne 2 : contenu

                    // Vérifier si le titre fait partie des titres à extraire
                    for (String title : titlesToExtract) {
                        if (titre.contains(title)) {
                            // Afficher seulement si l'information est présente
                            if (!information.isEmpty()) {
                                result.append(titre).append(" : ").append(information).append("\n");
                                dataFound = true;  // Indiquer qu'une donnée a été trouvée
                            }
                        }
                    }
                }
            }

            // Si aucune donnée n'a été trouvée, afficher un message
            if (!dataFound) {
                result.append("Aucune donnée pertinente trouvée sur cette page.\n");
            }

            result.append("---------------------------------\n");

        } catch (IOException e) {
            result.append("Erreur lors de l'accès aux détails : ").append(e.getMessage()).append("\n");
            e.printStackTrace();
        }

        return result.toString();
    }

    // Méthode de l'interface WebScraper pour récupérer toutes les données des emplois
    @Override
    public List<String> getScrapedData() {
        List<String> jobDataList = new ArrayList<>();

        // Récupérer tous les liens d'emplois depuis la page principale
        List<String> jobLinks = getJobLinksFromMainPage();

        // Parcourir chaque lien d'emploi et récupérer ses détails
        for (String jobLink : jobLinks) {
            String jobDetails = scrapeJobDetails(jobLink);
            jobDataList.add(jobDetails);
        }

        return jobDataList;
    }

    // Méthode pour afficher les données récupérées
    @Override
    public void scrap() {
        getScrapedData().forEach(System.out::println);
    }
}
