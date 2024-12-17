package service;

import data.model.Offer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JobServiceTest {

    public static void main(String[] args) {
        testCompleteOfferInsertion();
        testLegacyJobInsertion();
        testJobRetrieval();
        testJobSearch();
    }

    private static void testCompleteOfferInsertion() {
        System.out.println("Testing Complete Offer Insertion...");
        JobInsertionService jobInsertionService = new JobInsertionService();
        
        // Create a complete offer with all fields
        Offer offer = new Offer(
            0,
            "Senior Software Engineer",
            "https://example.com/job123",
            "ExampleSite",
            new Date(),
            new Date(),
            "123 Tech Street",
            "www.techcompany.com",
            "Tech Company Inc",
            "Leading tech company in AI",
            "Develop cutting-edge AI solutions",
            "North",
            "San Francisco",
            "Technology",
            "Software Development",
            "CDI",
            "Master's Degree",
            "Computer Science",
            "5+ years",
            "Expert in Java and AI",
            "Creative, Team Player",
            "Java, Python, AI/ML",
            "Communication, Leadership",
            "Docker, Kubernetes",
            "English, French",
            "Fluent",
            "80K-100K USD",
            "Health Insurance, 401k",
            true
        );

        // Test insertion
        jobInsertionService.insertOffer(offer);
        System.out.println("Complete offer insertion test completed");
    }

    private static void testLegacyJobInsertion() {
        System.out.println("\nTesting Legacy Job Insertion...");
        JobInsertionService jobInsertionService = new JobInsertionService();

        // Create sample job data in legacy format
        List<String> jobData = new ArrayList<>();
        jobData.add("Software Engineer\nCompany A\nBachelor's Degree\n2 years\nNew York\nFull-time\nhttp://example.com/job\nDeveloping enterprise applications");

        // Test insertion
        jobInsertionService.insertJobs(jobData);
        System.out.println("Legacy job insertion test completed");
    }

    private static void testJobRetrieval() {
        System.out.println("\nTesting Job Retrieval...");
        JobRetrievalService jobRetrievalService = new JobRetrievalService();

        // Test getAllJobs
        List<Offer> offers = jobRetrievalService.getAllJobs();
        System.out.println("Retrieved " + offers.size() + " offers");

        // Print detailed information for each offer
        for (Offer offer : offers) {
            printOfferDetails(offer);
        }
    }

    private static void testJobSearch() {
        System.out.println("\nTesting Job Search...");
        JobRetrievalService jobRetrievalService = new JobRetrievalService();

        // Test searching with different keywords
        String[] keywords = {"Software", "Engineer", "Java", "New York"};
        
        for (String keyword : keywords) {
            System.out.println("\nSearching for: " + keyword);
            List<Offer> matchingOffers = jobRetrievalService.searchJobs(keyword);
            System.out.println("Found " + matchingOffers.size() + " matching offers");
            
            for (Offer offer : matchingOffers) {
                printOfferDetails(offer);
            }
        }
    }

    private static void printOfferDetails(Offer offer) {
        System.out.println("\n====== Offer Details ======");
        System.out.println("Title: " + offer.getTitre());
        System.out.println("Company: " + offer.getNomEntreprise());
        System.out.println("Location: " + offer.getVille());
        System.out.println("Contract Type: " + offer.getTypeContrat());
        System.out.println("Education Level: " + offer.getNiveauEtudes());
        System.out.println("Experience Required: " + offer.getExperience());
        System.out.println("Required Skills: " + offer.getCompetencesRequises());
        System.out.println("Soft Skills: " + offer.getSoftSkills());
        System.out.println("Languages: " + offer.getLangue() + " (" + offer.getNiveauLangue() + ")");
        System.out.println("Salary: " + offer.getSalaire());
        System.out.println("Benefits: " + offer.getAvantagesSociaux());
        System.out.println("Remote Work: " + (offer.isTeletravail() ? "Yes" : "No"));
        System.out.println("URL: " + offer.getUrl());
        System.out.println("Description: " + offer.getDescriptionPoste());
        System.out.println("==========================");
    }
}
