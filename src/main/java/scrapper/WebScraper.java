package scrapper;

import java.util.List;

public interface WebScraper {
    void scrap();
    public List<String> getScrapedData();
}