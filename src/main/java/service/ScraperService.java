package service;

import java.util.ArrayList;
import java.util.List;

import scrapper.EmploiScraper;
<<<<<<< HEAD
import scrapper.Mjob_Skuld_Operation;
=======
import scrapper.RekruteScraper;
>>>>>>> deea5605b8a551ac60e8375b007347cbcb92b3e9
import scrapper.WebScraper;
public class ScraperService {
    private final List<WebScraper> scrapers;

    public ScraperService() {
        scrapers = new ArrayList<>();
<<<<<<< HEAD
        //scrapers.add(new RekruteScraper());
=======
        scrapers.add(new RekruteScraper());
>>>>>>> deea5605b8a551ac60e8375b007347cbcb92b3e9
        //scrapers.add(new EmploiScraper());
        //scrapers.add(new MarocemploiScraper());
        //scrapers.add(new EmploiPublicScraper());
        scrapers.add(new Mjob_Skuld_Operation());


    }

    public List<String> getAllJobData() {
        List<String> allJobData = new ArrayList<>();
        for (WebScraper scraper : scrapers) {
            allJobData.addAll(scraper.getScrapedData());
        }
        return allJobData;
    }
}
