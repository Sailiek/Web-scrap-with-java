package scrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MarocemploiScraper implements WebScraper{


private List<String> getJobLinksFromMainPage() {
        List<String> jobLinks = new ArrayList<>();
        int numPageToScrap = 2;

        try {
            String url;
            String baseurl ="https://marocemploi.net/offre/?ajax_filter=true&job_page=";
            for (int pageNumber = 1; pageNumber <= numPageToScrap; pageNumber++) {
                 url = baseurl + pageNumber;

                
                    Document doc = Jsoup.connect(url).get();
    
        // Step 1: Fetch the jobs page and extract URLs from the data-href attribute
        Document jobsPage = Jsoup.connect(url).get();
        Elements jobCards = jobsPage.select("div.jobsearch-list-option > h2 > a ");
    

        // Store job detail page URLs
        for (Element card : jobCards) {
            String jobUrl = card.attr("href");
            jobLinks.add(jobUrl);
        }

    }
} catch (IOException e) {
     e.printStackTrace();
}
     return jobLinks; 
    }

    private String scrapeJobDetails(String jobUrl) {
        StringBuilder result = new StringBuilder();
    
        try {
            // Fetch the detail page
            Document detailPage = Jsoup.connect(jobUrl).get();
    
            // Extract job title (adjust this if necessary, assuming h1 or other header for the title)
            Element titleElement = detailPage.selectFirst("figure.jobsearch-jobdetail-list h1");
            String jobTitle = titleElement != null ? titleElement.text() : "No Job Title";
            result.append("Job Title: ").append(jobTitle).append("\n");
            //extract company name
            Element a_company = detailPage.selectFirst("figure.jobsearch-jobdetail-list > figcaption > span > a");
            String company = a_company.text();
            result.append("Company: ").append(company).append("\n");

            result.append("Study Level: ").append("None");

            result.append("Experience Level: ").append("None");

            //Localisation :
            Element loc = detailPage.selectFirst(" ul.jobsearch-jobdetail-options > li> a.jobsearch-jobdetail-view");
            String Localisation = loc.attr("href");           
            result.append("Localisation: ").append(Localisation).append("\n");

            //type de contrtat
            Element type_contrtat = detailPage.selectFirst("figure.jobsearch-jobdetail-list > figcaption > span > small > a");
            String contrat = type_contrtat.text();
             result.append("Contract Type: ").append(contrat).append("\n");

            //JOb url
            result.append("URL : ").append(jobUrl).append("\n \n");

             
            // Extract job description details (new structure)
            Elements descriptionElements = detailPage.select("div.jobsearch-jobdetail-content > div.jobsearch-description");
    
            for (Element descElement : descriptionElements) {
                // Extract each content block (could be <p>, <ul>, or <h3>)
                for (Element content : descElement.children()) {
                    if (content.tagName().equals("h3")) {
                        // Extract the section title if it's an h3
                        result.append("Section Title: ").append(content.text()).append("\n");
                    } else if (content.tagName().equals("p")) {
                        // Extract the paragraph content
                        result.append(" - ").append(content.text()).append("\n");
                    } else if (content.tagName().equals("ul")) {
                        // Extract list items if it's an unordered list
                        Elements listItems = content.select("li");
                        for (Element listItem : listItems) {
                            result.append(" - ").append(listItem.text()).append("\n");
                        }
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