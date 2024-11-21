package service;

import scrapper.RekruteScraper;
import scrapper.WebScraper;

import java.util.ArrayList;
import java.util.List;

public class MainScraper {
    public static void main(String[] args) {
        List<WebScraper> scrapers = new ArrayList<>();

        // each website scrapper should be in the list
        scrapers.add(new RekruteScraper());


        // one scrap is done : we call the scraper of each website
        for (WebScraper scraper : scrapers) {
            scraper.scrap();
        }
    }
}
