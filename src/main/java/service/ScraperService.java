package service;

import java.util.ArrayList;
import java.util.List;

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

    public List<String> getAllJobData() {
        List<String> allJobData = new ArrayList<>();
        for (WebScraper scraper : scrapers) {
            allJobData.addAll(scraper.getScrapedData());
        }
        
        return allJobData;
    }
}