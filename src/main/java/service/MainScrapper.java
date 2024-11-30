package service;

import java.util.ArrayList;
import java.util.List;

import scrapper.*;


public class MainScrapper {
    public static void main(String[] args) {
        List<WebScraper> scrapers = new ArrayList<>();

        long startTime = System.nanoTime(); // Start time

       scrapers.add(new RekruteScraper());
//        scrapers.add(new EmploiScraper());
//        scrapers.add(new MarocemploiScraper());
//
//        scrapers.add(new EmploiPublicScraper());
//        scrapers.add(new Mjob_Skuld_Operation());





        // one scrap is done : we call the scraper of each website
        for (WebScraper scraper : scrapers) {
            scraper.scrap();
        }

        long endTime = System.nanoTime(); // End time

        long durationInMilliseconds = (endTime - startTime) / 1_000_000_000;
        System.out.println("Execution time: " + durationInMilliseconds + " s");
    }
}
