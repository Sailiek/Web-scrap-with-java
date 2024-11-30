package service;

import scrapper.*;

import java.util.ArrayList;
import java.util.List;

public class ScraperService {
    private final List<WebScraper> scrapers;

    public ScraperService() {
        scrapers = new ArrayList<>();
        //scrapers.add(new RekruteScraper());
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
