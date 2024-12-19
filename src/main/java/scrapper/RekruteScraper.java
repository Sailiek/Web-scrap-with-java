package scrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import data.model.Offer;

public class RekruteScraper implements WebScraper {
    private static final int MAX_TRIES = 3;
    private static final int SCRAPER_SLEEP_TIME = 2000; // Base sleep time in milliseconds
    private static final int MAX_PAGES = 30;

    private List<Offer> scrapeRekrute() {
        List<Offer> offers = new ArrayList<>();
        List<String> exceptions = new ArrayList<>();

        for (int page = 1; page <= MAX_PAGES; page++) {
            String url = "https://www.rekrute.com/fr/offres.html?s=3&p=" + page + "&o=1";
            Document doc = null;
            int retryCount = 0;

            // Retry logic for connection failures
            while (retryCount < MAX_TRIES) {
                try {
                    doc = Jsoup.connect(url).get();
                    break;
                } catch (Exception e) {
                    System.out.println("Retrying connection to Rekrute page " + page);
                    retryCount++;
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        return offers;
                    }
                }
            }

            if (doc == null) {
                continue;
            }

            Element postData = doc.getElementById("post-data");
            if (postData == null) continue;

            Elements allPosts = postData.getElementsByClass("post-id");
            int postNum = 0;

            for (Element post : allPosts) {
                try {
                    postNum++;
                    Offer offer = extractOfferFromPost(post);
                    if (offer != null) {
                        offers.add(offer);
                    }
                } catch (Exception e) {
                    String error = "Exception on post " + postNum + " page " + page + ": " + e.getMessage();
                    exceptions.add(error);
                    System.err.println(error);
                }
            }

            // Sleep between pages with random delay
            try {
                Random random = new Random();
                int randomDelay = random.nextInt(1000) + SCRAPER_SLEEP_TIME;
                Thread.sleep(randomDelay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        // Print exception report
        if (!exceptions.isEmpty()) {
            System.err.println("\n*** EXCEPTION REPORT ***");
            exceptions.forEach(System.err::println);
            System.err.println("*********************");
        }

        return offers;
    }

    private Offer extractOfferFromPost(Element post) throws Exception {
        // Company info
        Element companyDiv = post.getElementsByClass("col-sm-2 col-xs-12").first();
        String nomEntreprise = companyDiv.getElementsByTag("img").attr("alt").trim();
        String siteWebEntreprise = "https://www.rekrute.com" + companyDiv.getElementsByTag("a").first().attr("href");

        // Job info
        Element bodyDiv = post.getElementsByClass("col-sm-10 col-xs-12").first();
        Element titleElement = bodyDiv.getElementsByClass("titreJob").first();
        String rawTitle = titleElement.text().trim();
        
        // Parse title and location
        String[] titleParts = rawTitle.split("\\|");
        String titre = titleParts[0].trim();
        String[] locationParts = titleParts[1].trim().split("\\(");
        String ville = locationParts[0].trim();
        String region = locationParts[1].replace(")", "").trim();

        // Get URL
        String url = "https://www.rekrute.com" + titleElement.attr("href");

        // Company and job descriptions
        String descriptionEntreprise = bodyDiv.getElementsByClass("info").get(0).text().trim();
        String descriptionPoste = bodyDiv.getElementsByClass("info").get(1).text().trim();

        // Dates
        Element datesElement = bodyDiv.getElementsByClass("date").first();
        String datePublicationStr = datesElement.getElementsByTag("span").get(0).text().trim();
        String datePostulerStr = datesElement.getElementsByTag("span").get(1).text().trim();
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date datePublication = null;
        Date datePostuler = null;
        try {
            datePublication = dateFormat.parse(datePublicationStr);
            datePostuler = dateFormat.parse(datePostulerStr);
        } catch (Exception e) {
            System.err.println("Error parsing dates: " + e.getMessage());
        }

        // Job details
        Element infoUl = bodyDiv.getElementsByClass("info").get(2).getElementsByTag("ul").first();
        
        String secteurActivite = extractTextFromElements(infoUl.getElementsByTag("li").get(0).getElementsByTag("a"));
        String metier = extractTextFromElements(infoUl.getElementsByTag("li").get(1).getElementsByTag("a"));
        String experience = extractTextFromElements(infoUl.getElementsByTag("li").get(2).getElementsByTag("a"));
        String niveauEtudes = extractTextFromElements(infoUl.getElementsByTag("li").get(3).getElementsByTag("a"));
        String typeContrat = infoUl.getElementsByTag("li").get(4).getElementsByTag("a").first().text().trim();
        
        // Telework status
        String teletravailStr = infoUl.getElementsByTag("li").get(4).text().split(":")[2].trim();
        boolean teletravail = teletravailStr.equalsIgnoreCase("Oui");

        return new Offer(
            0, // ID will be set by database
            titre,
            url,
            "ReKrute",
            datePublication,
            datePostuler,
            null, // adresseEntreprise
            siteWebEntreprise,
            nomEntreprise,
            descriptionEntreprise,
            descriptionPoste,
            region,
            ville,
            secteurActivite,
            metier,
            typeContrat,
            niveauEtudes,
            null, // specialiteDiplome
            experience,
            null, // profilRecherche
            null, // traitsPersonnalite
            null, // competencesRequises
            null, // softSkills
            null, // competencesRecommandees
            null, // langue
            null, // niveauLangue
            null, // salaire
            null, // avantagesSociaux
            teletravail
        );
    }

    private String extractTextFromElements(Elements elements) {
        StringBuilder text = new StringBuilder();
        for (Element element : elements) {
            if (text.length() > 0) text.append(" ");
            text.append(element.text().trim());
        }
        return text.toString();
    }

    @Override
    public List<String> getScrapedData() {
        List<String> result = new ArrayList<>();
        List<Offer> offers = scrapeRekrute();
        
        for (Offer offer : offers) {
            StringBuilder offerString = new StringBuilder();
            offerString.append("Title: ").append(offer.getTitre()).append("\n");
            offerString.append("Company: ").append(offer.getNomEntreprise()).append("\n");
            offerString.append("Location: ").append(offer.getVille()).append(" (").append(offer.getRegion()).append(")\n");
            offerString.append("Contract: ").append(offer.getTypeContrat()).append("\n");
            offerString.append("Experience: ").append(offer.getExperience()).append("\n");
            offerString.append("Education: ").append(offer.getNiveauEtudes()).append("\n");
            offerString.append("Sector: ").append(offer.getSecteurActivite()).append("\n");
            offerString.append("Function: ").append(offer.getMetier()).append("\n");
            offerString.append("URL: ").append(offer.getUrl()).append("\n");
            offerString.append("Remote Work: ").append(offer.isTeletravail() ? "Yes" : "No").append("\n");
            offerString.append("Description: ").append(offer.getDescriptionPoste()).append("\n");
            offerString.append("Company Description: ").append(offer.getDescriptionEntreprise()).append("\n");
            
            result.add(offerString.toString());
        }
        
        return result;
    }

    @Override
    public void scrap() {
        System.out.println("Starting Rekrute scraping...");
        List<String> scrapedData = getScrapedData();
        System.out.println("Scraped " + scrapedData.size() + " job offers from Rekrute");
        scrapedData.forEach(System.out::println);
    }
}
