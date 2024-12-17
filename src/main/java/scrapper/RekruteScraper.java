package scrapper;


import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class RekruteScraper implements WebScraper {


    private String scrapeJobDetails(String jobUrl) {
        StringBuilder jobInfo = new StringBuilder();

        try {

            Document jobDoc = Jsoup.connect(jobUrl).get();

            // Scraping details from the individual job offer page
            Element titleElement = jobDoc.select("h1").first();
            jobInfo.append("Job Title: ").append((titleElement != null) ? titleElement.text() : "No title found").append("\n");

            Element targetDiv = jobDoc.select("div.contentbloc").first();
            Element introToSocieteElement = targetDiv.select("div#recruiterDescription p").first();
            jobInfo.append("Company: ").append((introToSocieteElement != null) ? introToSocieteElement.text() : "No intro societe found").append("\n");


//            Element descriptionPosteElement = targetDiv.select("div.col-md-12.blc ").eq(4).first();
//            jobInfo.append("Description Offre: ").append((descriptionPosteElement != null) ? descriptionPosteElement.text() : "No description found").append("\n");

//            Element SkillsPosteElement = targetDiv.select("div.col-md-12.blc").eq(5).first();
//            jobInfo.append("Skills requis: ").append((SkillsPosteElement != null) ? SkillsPosteElement.text() : "No description found").append("\n");

            Element exp_niveauEtudeDiv = jobDoc.select("ul.featureInfo").first();
            Element nivEtdude = exp_niveauEtudeDiv.select("li").eq(2).first();
            jobInfo.append("Study Level: ").append((nivEtdude !=null) ? nivEtdude.text():"not found").append("\n");

            Element experience = exp_niveauEtudeDiv.select("li").eq(0).first();
            jobInfo.append("Experience Level: ").append((experience !=null) ? experience.text():"not found").append("\n");

            Element localisation = jobDoc.select("div.col-md-12.blc > span").eq(0).first();
            jobInfo.append("Localisation: ")
                    .append((localisation != null) ? localisation.text() : "not found")
                    .append("\n");
            List<String> jobDetails = new ArrayList<>();


            Element typeContrat = jobDoc.select("ul.featureInfo > li > span.tagContrat").eq(0).first();
            jobInfo.append("Contract Type: ").append((typeContrat != null) ? typeContrat.text() : "not found").append("\n");

            jobInfo.append("URL :").append(jobUrl).append("\n");

            Element fonctions = jobDoc.select("h2.h2italic").first();
            if (fonctions != null) {
                String mainText = fonctions.ownText(); // e.g., "Multimédia / Internet - Secteur Informatique"
                mainText = mainText.replace("(métiers de la)", "").replace("(métiers de)", "").trim();
                // Replace "/" with "," and remove "Secteur"
                mainText = mainText.replace("/", ",").replace("Secteur", "").trim();

                // Split the text by delimiters like "-" and ","
                String[] parts = mainText.split("[-,]");

                // Clean up each part and store in a list
                List<String> result = new ArrayList<>();
                for (String part : parts) {
                    String cleanedPart = part.trim(); // Remove extra spaces
                    if (!cleanedPart.isEmpty()) { // Ignore empty strings
                        result.add(cleanedPart);
                    }
                }

                // Print or return the final list
                jobInfo.append("Job Details  :").append(result).append("\n");
            } else {
                System.out.println("<h2> element not found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Error scraping job details from: " + jobUrl;
        }

        return jobInfo.toString();
    }

    // Method to get all job links from the main page
    private List<String> getJobLinksFromMainPage() {
        List<String> jobLinks = new ArrayList<>();
        int numPageToScrap = 1;

        try {
            for (int pageNumber = 1; pageNumber <= numPageToScrap; pageNumber++) {
                String url = "https://www.rekrute.com/offres.html?s=1&p=" + pageNumber + "&o=1";
                Document doc = Jsoup.connect(url).get();

                // Adjusting selector to find the job links in <h2><a> tag
                Element ulElement = doc.select("div.page > div#fortopscroll > div.main.alt-main > div.section > div.container > div.row > div.col-md-9 > div.content-column > div.slide-block > ul").first();

                if (ulElement != null) {
                    for (Element li : ulElement.select("li")) {
                        // Look for the <h2><a> tag inside each <li> to extract the job link
                        Element titleElement = li.select("h2 > a").first(); // <h2><a> tag for job link

                        if (titleElement != null) {
                            String jobUrl = titleElement.attr("href"); // Extract the href attribute (job link)

                            // Ensure the URL is absolute
                            if (!jobUrl.startsWith("http")) {
                                jobUrl = "https://www.rekrute.com" + jobUrl;  // Make the URL absolute if it's relative
                            }

                            jobLinks.add(jobUrl); // Add the job URL to the list
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jobLinks;
    }


    // Method to scrape the details from all job links
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