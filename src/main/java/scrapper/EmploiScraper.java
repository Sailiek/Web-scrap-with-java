package scrapper;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import data.model.Offer;

public class EmploiScraper implements WebScraper {
    private static final int MAX_TRIES = 3;
    private static final int SCRAPER_SLEEP_TIME = 2000; // Base sleep time in milliseconds
    private static final int NUMBER_OF_PAGES_TO_SCRAPE = 2;
    private static final String BASE_URL = "https://www.emploi.ma/recherche-jobs-maroc";

    private List<String> getJobLinksFromMainPage() {
        List<String> jobLinks = new ArrayList<>();
        List<String> exceptions = new ArrayList<>();
        
        for (int pageNumber = 0; pageNumber < NUMBER_OF_PAGES_TO_SCRAPE; pageNumber++) {
            String url = pageNumber == 0 ? BASE_URL : BASE_URL + "?page=" + pageNumber;
            Document doc = null;
            int retryCount = 0;

            // Retry logic for connection failures
            while (retryCount < MAX_TRIES) {
                try {
                    doc = Jsoup.connect(url).get();
                    break;
                } catch (Exception e) {
                    System.out.println("Retrying connection to Emploi.ma page " + pageNumber);
                    retryCount++;
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        return jobLinks;
                    }
                }
            }

            if (doc == null) {
                continue;
            }

            Elements jobCards = doc.select("div.page-search-jobs-content > div.card.card-job[data-href]");
            for (Element card : jobCards) {
                String jobUrl = card.attr("data-href");
                jobLinks.add(jobUrl);
            }

            // Sleep between pages with random delay
            try {
                Random random = new Random();
                int randomDelay = random.nextInt(1000) + SCRAPER_SLEEP_TIME;
                Thread.sleep(randomDelay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        return jobLinks;
    }

    private Offer scrapeJobDetails(String jobUrl) {
        try {
            // Fetch the detail page
            Document detailPage = Jsoup.connect(jobUrl).get();
    
            // Job Title and Location
            Element titleElement = detailPage.selectFirst("div.container > h1");
            String rawTitle = titleElement != null ? titleElement.text().trim() : null;
            
            String titre = null;
            String ville = null;
            String region = null;
            
            if (rawTitle != null && rawTitle.contains("|")) {
                String[] titleParts = rawTitle.split("\\|");
                titre = titleParts[0].trim();
                
                if (titleParts.length > 1) {
                    String[] locationParts = titleParts[1].trim().split("\\(");
                    ville = locationParts[0].trim();
                    region = locationParts.length > 1 ? 
                        locationParts[1].replace(")", "").trim() : 
                        null;
                }
            }

            // Fallback if title parsing fails
            if (titre == null) {
                titre = rawTitle;
            }
            
            // Dates
            Date datePublication = null;
            Date datePostuler = null;
            Element date_pub = detailPage.selectFirst("div.page-application-details > p");
            if (date_pub != null) {
                String job_d_pub = date_pub.text();
                String datePart = job_d_pub.replace("Publiée le ", "").trim();
        
                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                LocalDate date = LocalDate.parse(datePart, inputFormatter);
                datePublication = java.sql.Date.valueOf(date);
                datePostuler = new Date(); // Current date for application deadline
            }

            // Company Details
            Element companyElement = detailPage.selectFirst("div.card-block-company > ul > li > h3 > a");
            String nomEntreprise = companyElement != null ? companyElement.text().trim() : null;
            String siteWebEntreprise = companyElement != null ? companyElement.attr("href") : null;
            
            // Company Description
            Element descriptionEntrepriseElement = detailPage.selectFirst("div.truncated > p");
            String descriptionEntreprise = descriptionEntrepriseElement != null ? 
                descriptionEntrepriseElement.text().trim() : null;

            // Job Description
            Element descriptionPosteElement = detailPage.selectFirst("div.job-description > ul");
            String descriptionPoste = descriptionPosteElement != null ? descriptionPosteElement.text().trim() : null;

            // Contract and Other Details
            Element typeContratElement = detailPage.selectFirst("li.withicon.file-signature");
            String typeContrat = typeContratElement != null ? typeContratElement.text().trim() : null;

            // Teletravail detection
            Element teleTravailElement = detailPage.selectFirst("li:contains(Travail à distance) > span");
            boolean teletravail = teleTravailElement != null && 
                teleTravailElement.text().trim().equalsIgnoreCase("Oui");

            // Study Level
            Element niveauEtudesElement = detailPage.selectFirst("li.withicon.graduation-cap");
            String niveauEtudes = niveauEtudesElement != null ? niveauEtudesElement.text().trim() : null;

            // Experience Level
            Element experienceElement = detailPage.selectFirst("li.withicon.chart");
            String experience = experienceElement != null ? experienceElement.text().trim() : null;

            // Sector and Job Function
            Element secteurActiviteElement = detailPage.selectFirst("li:contains(Secteur d´activité) > span");
            String secteurActivite = secteurActiviteElement != null ? secteurActiviteElement.text().trim() : null;

            Element metierElement = detailPage.selectFirst("li:contains(Métier) > span");
            String metier = metierElement != null ? metierElement.text().trim() : null;

            // Searched Profile
            Elements profilRechercheElements = detailPage.select("div.job-qualifications > ul > li:has(strong)");
            String profilRecherche = profilRechercheElements.stream()
                .map(Element::text)
                .reduce((a, b) -> a + ", " + b)
                .orElse(null);

            // Hard Skills
            Elements hardSkillsElements = detailPage.select("div.job-description > ul > li");
            String competencesRequises = hardSkillsElements.stream()
                .map(Element::text)
                .reduce((a, b) -> a + ", " + b)
                .orElse(null);

            // Soft Skills
            String softSkills = null;
            for (Element element : detailPage.select("li")) {
                if (element.text().contains("Management d'équipe")) {
                    softSkills = element.selectFirst("span") != null && 
                        element.selectFirst("span").text().equals("Oui") ? 
                        "Team management" : null;
                    break;
                }
            }

            // Languages
            Element langueElement = detailPage.selectFirst("li:contains(Langues exigées) > span");
            String langue = langueElement != null ? langueElement.text().trim() : null;

            // Salary
            Element salaireElement = detailPage.selectFirst("li:contains(Salaire proposé) > span");
            String salaire = salaireElement != null ? salaireElement.text().trim() : null;

            return new Offer(
                0, // ID will be set by database
                titre,
                jobUrl,
                "Emploi.ma",
                datePublication,
                datePostuler,
                null, // adresseEntreprise
                siteWebEntreprise,
                nomEntreprise,
                descriptionEntreprise,
                descriptionPoste,
                region,
                ville,
                secteurActivite,
                metier,
                typeContrat,
                niveauEtudes,
                null, // specialiteDiplome
                experience,
                profilRecherche,
                null, // traitsPersonnalite
                competencesRequises,
                softSkills,
                null, // competencesRecommandees
                langue,
                null, // niveauLangue
                salaire,
                null, // avantagesSociaux
                teletravail
            );

        } catch (IOException e) {
            System.err.println("Error scraping job details from: " + jobUrl);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<String> getScrapedData() {
        List<String> result = new ArrayList<>();
        List<Offer> offers = getOffers();
        
        for (Offer offer : offers) {
            StringBuilder offerString = new StringBuilder();
            offerString.append("Title: ").append(offer.getTitre()).append("\n");
            offerString.append("Company: ").append(offer.getNomEntreprise()).append("\n");
            offerString.append("Location: ").append(offer.getVille()).append(" (").append(offer.getRegion()).append(")\n");
            offerString.append("Contract: ").append(offer.getTypeContrat()).append("\n");
            offerString.append("Experience: ").append(offer.getExperience()).append("\n");
            offerString.append("Education: ").append(offer.getNiveauEtudes()).append("\n");
            offerString.append("Sector: ").append(offer.getSecteurActivite()).append("\n");
            offerString.append("Function: ").append(offer.getMetier()).append("\n");
            offerString.append("URL: ").append(offer.getUrl()).append("\n");
            offerString.append("Remote Work: ").append(offer.isTeletravail() ? "Yes" : "No").append("\n");
            offerString.append("Description: ").append(offer.getDescriptionPoste()).append("\n");
            offerString.append("Company Description: ").append(offer.getDescriptionEntreprise()).append("\n");
            
            result.add(offerString.toString());
        }
        
        return result;
    }

    public List<Offer> getOffers() {
        List<Offer> offers = new ArrayList<>();
        List<String> exceptions = new ArrayList<>();

        // Step 1: Get all job links from the main page
        List<String> jobLinks = getJobLinksFromMainPage();

        // Step 2: Visit each job link and scrape its details
        int jobNum = 0;
        for (String jobLink : jobLinks) {
            try {
                jobNum++;
                Offer offer = scrapeJobDetails(jobLink);
                if (offer != null) {
                    offers.add(offer);
                }
            } catch (Exception e) {
                String error = "Exception on job " + jobNum + ": " + e.getMessage();
                exceptions.add(error);
                System.err.println(error);
            }
        }

        // Print exception report
        if (!exceptions.isEmpty()) {
            System.err.println("\n*** EXCEPTION REPORT ***");
            exceptions.forEach(System.err::println);
            System.err.println("*********************");
        }

        return offers;
    }
    
    @Override
    public void scrap() {
        System.out.println("Starting Emploi.ma scraping...");
        List<String> scrapedData = getScrapedData();
        System.out.println("Scraped " + scrapedData.size() + " job offers from Emploi.ma");
        scrapedData.forEach(System.out::println);
    }
}