package service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import data.model.Offer;
import scrapper.EmploiScraper;
import scrapper.RekruteScraper;
import scrapper.WebScraper;

public class ScraperService {

    private final List<WebScraper> scrapers;

    public ScraperService() {
        scrapers = new ArrayList<>();
        scrapers.add(new RekruteScraper());
        //scrapers.add(new EmploiScraper());
    }

    public List<Offer> getAllJobData() {
        List<Offer> allJobData = new ArrayList<>();
        for (WebScraper scraper : scrapers) {
            List<String> scrapedData = scraper.getScrapedData();
            List<Offer> offers = convertToOffers(scrapedData, scraper.getClass().getSimpleName());
            allJobData.addAll(offers);
        }
        return allJobData;
    }

    private List<Offer> convertToOffers(List<String> scrapedData, String scraperName) {
        List<Offer> offers = new ArrayList<>();
        for (String data : scrapedData) {
            String[] parts = data.split("\n");
            if (parts.length >= 8) { // Ensure we have minimum required data
                Offer offer = new Offer(
                    0, // id - will be set by database
                    parts[0], // title
                    parts[6], // url
                    scraperName, // siteName
                    new Date(), // datePublication
                    new Date(), // datePostuler
                    "", // adresseEntreprise
                    "", // siteWebEntreprise
                    parts[1], // nomEntreprise
                    "", // descriptionEntreprise
                    parts[7], // descriptionPoste
                    "", // region
                    parts[4], // ville
                    "", // secteurActivite
                    "", // metier
                    parts[5], // typeContrat
                    parts[2], // niveauEtudes
                    "", // specialiteDiplome
                    parts[3], // experience
                    "", // profilRecherche
                    "", // traitsPersonnalite
                    "", // competencesRequises
                    "", // softSkills
                    "", // competencesRecommandees
                    "", // langue
                    "", // niveauLangue
                    "", // salaire
                    "", // avantagesSociaux
                    false // teletravail
                );
                offers.add(offer);
            }
        }
        return offers;
    }
}
