package scrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class EmploiScraper implements WebScraper{


    private List<String> getJobLinksFromMainPage() {
        List<String> jobLinks = new ArrayList<>();  
        
        int NUMBER_OF_PAGES_TO_SCRAPE = 2;
        String BASE_URL ="https://www.emploi.ma/recherche-jobs-maroc";
        for (int pageNumber = 0; pageNumber < NUMBER_OF_PAGES_TO_SCRAPE; pageNumber++) {
            String url = pageNumber == 0? BASE_URL : BASE_URL + "?page=" + pageNumber;
            try {
                Document doc = Jsoup.connect(url).get();
                Elements jobCards = doc.select("div.page-search-jobs-content > div.card.card-job[data-href]");
                for (Element card : jobCards) {
                    String jobUrl = card.attr("data-href");
                    jobLinks.add(jobUrl);
                }
                Thread.sleep(1000); // wait for 1 second
            } catch (IOException | InterruptedException e) {
                System.err.println("Error fetching job links from page " + pageNumber + ": " + e.getMessage());
            }
        }
        return jobLinks;
    }

    private String scrapeJobDetails(String jobUrl) {
        StringBuilder result = new StringBuilder();
    
        try {
            // Fetch the detail page
            Document detailPage = Jsoup.connect(jobUrl).get();
    
            // Extract job title
            Element titleElement = detailPage.selectFirst(" div.container > h1");
            String jobTitle = titleElement != null ? titleElement.text() : "No Job Title";
            result.append("Job Title: ").append(jobTitle).append("\n");
            
            //company
            Element a_company = detailPage.selectFirst("div.card-block-company > ul > li > h3 > a");
            String company = a_company != null ? a_company.text() : "No Company Name";
            result.append("Company: ").append(company).append("\n");
            
           // Location
            Element localisation = detailPage.selectFirst("li.withicon.location-dot");
            result.append("Localisation: ").append(localisation != null ? localisation.text().trim() : "No Location Found").append("\n");

            // Contract Type
            Element type_contrat = detailPage.selectFirst("li.withicon.file-signature");
            result.append("Contract Type: ").append(type_contrat != null ? type_contrat.text().trim() : "No Contract Type Found").append("\n");

            // Study Level
            Element niveau_etude = detailPage.selectFirst("li.withicon.graduation-cap");
            result.append("Study Level: ").append(niveau_etude != null ? niveau_etude.text().trim() : "No Study Level Found").append("\n");

            // Experience Level
            Element niveau_exp = detailPage.selectFirst("li.withicon.chart");
            result.append("Experience Level: ").append(niveau_exp != null ? niveau_exp.text().trim() : "No Experience Level Found").append("\n");


                        // URL
            result.append("URL : ").append(jobUrl).append("\n \n");

            result.append("Job Description : \n" );            
            // Extract sections within card-block-content
            Elements sections = detailPage.select("div.card-block-content > section");
    
            for (Element section : sections) {
                // Extract the header (h3)
                Element header = section.selectFirst("h3");
                String sectionTitle = header != null ? header.text() : "No Section Title";
                result.append("Section: ").append(sectionTitle).append("\n");
    
                // Extract the content (can be <p>, <ul>, etc.)
                Element content = section.selectFirst("div");
                if (content != null) {
                    if (content.selectFirst("ul") != null) {
                        // Extract all list items if <ul> exists
                        Elements listItems = content.select("ul > li");
                        for (Element listItem : listItems) {
                            result.append(" - ").append(listItem.text()).append("\n");
                        }
                    } else if (content.selectFirst("p") != null) {
                        // Extract paragraph content if <p> exists
                        result.append(" - ").append(content.selectFirst("p").text()).append("\n");
                    } else {
                        result.append(" - No content available in this section.\n");
                    }
                }
            }
    
            result.append("-----------------------------\n");
    
        } catch (IOException e) {
            result.append("Error scraping job details from: ").append(jobUrl).append("\n");
            e.printStackTrace();
        } catch (NullPointerException e) {
            result.append("Error: Missing data on page: ").append(jobUrl).append("\n");
        }
    
        return result.toString();
    }
    @Override
    public List<String> getScrapedData() {
        List<String> jobDataList = new ArrayList<>();

        // Step 1: Get all job links from the main page
        List<String> jobLinks = getJobLinksFromMainPage();

        // Step 2: Visit each job link and scrape its details
        for (String jobLink : jobLinks) {
            String jobDetails = scrapeJobDetails(jobLink);
            jobDataList.add(jobDetails);
        }

        return jobDataList;
    }
    
    @Override
    public void scrap() {
        // Print out the scraped job data to console
       getScrapedData().forEach(System.out::println);
    }
}

