package service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import data.model.Offer;
import scrapper.EmploiScraper;
import scrapper.Mjob_Skuld_Operation;
import scrapper.RekruteScraper;
import scrapper.WebScraper;
 

public class ScraperService {

    public final List<WebScraper> scrapers;

    public ScraperService() {
        scrapers = new ArrayList<>();
        scrapers.add(new RekruteScraper());
        //scrapers.add(new EmploiScraper());
        //scrapers.add(new Mjob_Skuld_Operation());
    }

    public List<Offer> getAllJobData() {
        List<Offer> allJobData = new ArrayList<>();
        for (WebScraper scraper : scrapers) {
            try {
                List<String> scrapedData = scraper.getScrapedData();
                List<Offer> offers = convertToOffers(scrapedData, scraper.getClass().getSimpleName());
                allJobData.addAll(offers);
            } catch (Exception e) {
                System.err.println("Error scraping data from " + scraper.getClass().getSimpleName() + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
        return allJobData;
    }

    private List<Offer> convertToOffers(List<String> scrapedData, String scraperName) {
        List<Offer> offers = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        // Get current date for publication date
        Date currentDate = new Date();
        
        // Calculate application deadline (30 days from now)
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DATE, 30);
        Date applicationDeadline = calendar.getTime();

        for (String data : scrapedData) {
            try {
                String[] lines = data.split("\n");
                String title = "N/A";
                String url = "N/A";
                String company = "N/A";
                String location = "";
                String contract = "N/A";
                String experience = "N/A";
                String education = "N/A";
                String sector = "N/A";
                String function = "N/A";
                String description = "N/A";
                String companyDesc = "N/A";
                boolean remote = false;
                String region = "N/A";
                String ville = "N/A";
                
                for (String line : lines) {
                    String[] parts = line.split(":", 2);
                    if (parts.length < 2) continue;
                    
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    
                    if (value.isEmpty() || value.equalsIgnoreCase("null")) {
                        continue;
                    }
                    
                    switch (key) {
                        case "Title":
                            title = value;
                            break;
                        case "Company":
                            company = value;
                            break;
                        case "Location":
                            location = value;
                            if (location.contains("(")) {
                                ville = location.substring(0, location.indexOf("(")).trim();
                                region = location.substring(location.indexOf("(") + 1, location.indexOf(")")).trim();
                            } else {
                                ville = location;
                                region = "N/A";
                            }
                            break;
                        case "Contract":
                            contract = value;
                            break;
                        case "Experience":
                            experience = value;
                            break;
                        case "Education":
                            education = value;
                            break;
                        case "Sector":
                            sector = value;
                            break;
                        case "Function":
                            function = value;
                            break;
                        case "URL":
                            url = value;
                            break;
                        case "Remote Work":
                            remote = value.equalsIgnoreCase("Yes");
                            break;
                        case "Description":
                            description = value;
                            break;
                        case "Company Description":
                            companyDesc = value;
                            break;
                    }
                }

                // Only create offer if we have at least the essential fields
                if (!title.equals("N/A") && !company.equals("N/A")) {
                    Offer offer = new Offer(
                        0, // id - will be set by database
                        title,
                        url,
                        scraperName,
                        currentDate,
                        applicationDeadline,
                        ville + ", " + region, // adresseEntreprise
                        url, // siteWebEntreprise (using job URL as fallback)
                        company,
                        companyDesc,
                        description,
                        region,
                        ville,
                        sector,
                        function,
                        contract,
                        education,
                        "N/A", // specialiteDiplome
                        experience,
                        description, // profilRecherche (using description as fallback)
                        "N/A", // traitsPersonnalite
                        "N/A", // competencesRequises
                        "N/A", // softSkills
                        "N/A", // competencesRecommandees
                        "N/A", // langue
                        "N/A", // niveauLangue
                        "N/A", // salaire
                        "N/A", // avantagesSociaux
                        remote
                    );
                    offers.add(offer);
                }
            } catch (Exception e) {
                System.err.println("Error converting scraped data to offer: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return offers;
    }
}
