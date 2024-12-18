package scrapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import data.model.Offer;

public class Mjob_Skuld_Operation implements WebScraper {
    private static final int MAX_TRIES = 3;
    private static final int SCRAPER_SLEEP_TIME = 2000;
    
    private List<String> getthaturl(String url) {
        List<String> urllist = new ArrayList<>();
        int retryCount = 0;
        
        while (retryCount < MAX_TRIES) {
            try {
                Document doc = Jsoup.connect(url).get();
                Elements links = doc.select("div.offer-box > div.right-area > div.date-buttons > form > div.form-group > a.btn.featured-btn");

                for (Element link : links) {
                    String href = link.attr("href");
                    if (!href.isEmpty()) {
                        urllist.add(href);
                    }
                }
                break;
            } catch (Exception e) {
                System.out.println("Retrying connection to M-job page");
                retryCount++;
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    return urllist;
                }
            }
        }
        return urllist;
    }

    private String nextoneplease(String url, int previous_page) {
        return url + "?page=" + (previous_page + 1);
    }

    private Offer extractOfferFromUrl(String url) {
        try {
            Document doc = Jsoup.connect(url).get();

            Element headerElement = doc.select("div.container > div.details-content > div.header-details > div.header-info").first();
            Element contentElement = doc.select("div.container > div.details-content > div.the-content").first();

            if (headerElement == null || contentElement == null) {
                return null;
            }

            // Extract basic information
            String titre = extractElementText(headerElement, "h1.offer-title");
            String nomEntreprise = extractElementText(headerElement, "ul.list-details > li:nth-child(1) > h3");
            String location = extractElementText(headerElement, "div.location > span");
            String typeContrat = extractElementText(headerElement, "ul.list-details > li:nth-child(2) > h3");

            // Split location into ville and region if possible
            String ville = location;
            String region = "";
            if (location.contains(",")) {
                String[] locationParts = location.split(",");
                ville = locationParts[0].trim();
                region = locationParts[1].trim();
            }

            // Extract experience and education using regex
            String contentText = contentElement.text();
            String experience = extractWithRegex(contentText, "(?<=\\bNiveau d'expériences requis :)(.*?)(?=\\bNiveau d'études exigé :)");
            String niveauEtudes = extractWithRegex(contentText, "(?<=Niveau d'études exigé :)(.*?)(?=\\bLangue\\(s\\) exigée\\(s\\) :)");

            // Create new Offer object
            return new Offer(
                0, // ID will be set by database
                titre,
                url,
                "M-job",
                new Date(), // Current date as publication date
                null, // datePostuler
                null, // adresseEntreprise
                null, // siteWebEntreprise
                nomEntreprise,
                null, // descriptionEntreprise
                contentText, // Using full content as description
                region,
                ville,
                null, // secteurActivite
                null, // metier
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
                false // teletravail - default to false as not specified
            );

        } catch (Exception e) {
            System.err.println("Error extracting offer from URL " + url + ": " + e.getMessage());
            return null;
        }
    }

    private String extractElementText(Element parent, String selector) {
        Element element = parent.select(selector).first();
        return element != null ? element.text().trim() : "";
    }

    private String extractWithRegex(String text, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        return matcher.find() ? matcher.group(1).trim() : "";
    }

    public List<Offer> scrapeOffers() {
        List<Offer> offers = new ArrayList<>();
        String baseUrl = "https://www.m-job.ma/recherche";
        int numberOfPages = 1;  // Can be modified to scrape more pages

        for (int i = 0; i < numberOfPages; i++) {
            List<String> urlList = getthaturl(baseUrl);
            
            for (String url : urlList) {
                Offer offer = extractOfferFromUrl(url);
                if (offer != null) {
                    offers.add(offer);
                }
            }

            // Add random delay between pages
            try {
                Random random = new Random();
                int randomDelay = random.nextInt(1000) + SCRAPER_SLEEP_TIME;
                Thread.sleep(randomDelay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }

            baseUrl = nextoneplease(baseUrl, i);
        }

        return offers;
    }

    @Override
    public List<String> getScrapedData() {
        List<String> result = new ArrayList<>();
        List<Offer> offers = scrapeOffers();
        
        for (Offer offer : offers) {
            StringBuilder offerString = new StringBuilder();
            offerString.append("Title: ").append(offer.getTitre()).append("\n");
            offerString.append("Company: ").append(offer.getNomEntreprise()).append("\n");
            offerString.append("Location: ").append(offer.getVille());
            if (!offer.getRegion().isEmpty()) {
                offerString.append(" (").append(offer.getRegion()).append(")");
            }
            offerString.append("\n");
            offerString.append("Contract: ").append(offer.getTypeContrat()).append("\n");
            offerString.append("Experience: ").append(offer.getExperience()).append("\n");
            offerString.append("Education: ").append(offer.getNiveauEtudes()).append("\n");
            offerString.append("URL: ").append(offer.getUrl()).append("\n");
            offerString.append("Description: ").append(offer.getDescriptionPoste()).append("\n");
            
            result.add(offerString.toString());
        }
        
        return result;
    }

    @Override
    public void scrap() {
        System.out.println("Starting M-job scraping...");
        List<String> scrapedData = getScrapedData();
        System.out.println("Scraped " + scrapedData.size() + " job offers from M-job");
        scrapedData.forEach(System.out::println);
    }
}