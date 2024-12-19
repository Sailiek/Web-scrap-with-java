package service;

import data.dao.JobDAO;
import data.dao.JobDAOImpl;
import data.model.Offer;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class JobInsertionService {
    private final JobDAO jobDAO;

    public JobInsertionService() {
        this.jobDAO = getJobDAO();
    }

    // Protected method to allow mocking in tests
    protected JobDAO getJobDAO() {
        return new JobDAOImpl();
    }

    public void insertOffer(Offer offer) {
        if (offer == null) {
            throw new IllegalArgumentException("Offer cannot be null");
        }
        
        if (offer.getTitre() == null || offer.getTitre().trim().isEmpty()) {
            throw new IllegalArgumentException("Offer title cannot be empty");
        }

        try {
            jobDAO.saveJob(offer);
        } catch (Exception e) {
            String errorMessage = "Error while inserting offer: " + offer.getTitre();
            System.err.println(errorMessage);
            e.printStackTrace();
            throw new RuntimeException(errorMessage, e);
        }
    }

    public void insertOffers(List<Offer> offers) {
        if (offers == null || offers.isEmpty()) {
            throw new IllegalArgumentException("Offers list cannot be null or empty");
        }

        for (Offer offer : offers) {
            insertOffer(offer);
        }
    }

    public void emptyDatabase() {
        try {
            jobDAO.emptyDatabase();
        } catch (Exception e) {
            String errorMessage = "Error while emptying database";
            System.err.println(errorMessage);
            e.printStackTrace();
            throw new RuntimeException(errorMessage, e);
        }
    }

    // Legacy method for backward compatibility
    public void insertJobs(List<String> jobData) {
        if (jobData == null || jobData.isEmpty()) {
            throw new IllegalArgumentException("Job data list cannot be null or empty");
        }

        for (String job : jobData) {
            try {
                String[] parsedData = parseScrapedData(job);

                if (parsedData != null && parsedData.length >= 8) {
                    // Get current date for publication date
                    Date currentDate = new Date();
                    
                    // Calculate application deadline (30 days from now)
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(currentDate);
                    calendar.add(Calendar.DATE, 30);
                    Date applicationDeadline = calendar.getTime();

                    // Create Offer object with all available fields
                    Offer newOffer = new Offer(
                        0, // id will be set by database
                        parsedData[0], // title
                        parsedData[6], // url
                        "Legacy Import", // siteName
                        currentDate, // datePublication
                        applicationDeadline, // datePostuler
                        parsedData[4], // adresseEntreprise (using location)
                        parsedData[6], // siteWebEntreprise (using URL as fallback)
                        parsedData[1], // nomEntreprise
                        "N/A", // descriptionEntreprise
                        parsedData[7], // descriptionPoste
                        "N/A", // region
                        parsedData[4], // ville (using location)
                        "N/A", // secteurActivite
                        "N/A", // metier
                        parsedData[5], // typeContrat
                        parsedData[2], // niveauEtudes
                        "N/A", // specialiteDiplome
                        parsedData[3], // experience
                        parsedData[7], // profilRecherche (using description)
                        "N/A", // traitsPersonnalite
                        "N/A", // competencesRequises
                        "N/A", // softSkills
                        "N/A", // competencesRecommandees
                        "N/A", // langue
                        "N/A", // niveauLangue
                        "N/A", // salaire
                        "N/A", // avantagesSociaux
                        false // teletravail
                    );

                    insertOffer(newOffer);
                } else {
                    String errorMessage = "Invalid job data format: " + job;
                    System.err.println(errorMessage);
                    throw new IllegalArgumentException(errorMessage);
                }
            } catch (Exception e) {
                String errorMessage = "Error while inserting job: " + job;
                System.err.println(errorMessage);
                e.printStackTrace();
                throw new RuntimeException(errorMessage, e);
            }
        }
    }

    private String[] parseScrapedData(String job) {
        if (job == null || job.trim().isEmpty()) {
            throw new IllegalArgumentException("Job data cannot be null or empty");
        }

        try {
            String[] lines = job.split("\n");

            if (lines.length >= 8) {
                String title = extractField(lines[0]);
                String company = extractField(lines[1]);
                String education = extractField(lines[2]);
                String experience = extractField(lines[3]);
                String localisation = extractField(lines[4]);
                String contract = extractField(lines[5]);
                String link = extractField(lines[6]);
                String jobDetails = extractField(lines[7]);

                // Validate essential fields
                if (title.isEmpty() || company.isEmpty()) {
                    throw new IllegalArgumentException("Title and company are required fields");
                }

                return new String[]{title, company, education, experience, localisation, contract, link, jobDetails};
            }
        } catch (Exception e) {
            String errorMessage = "Error while parsing job data: " + job;
            System.err.println(errorMessage);
            e.printStackTrace();
            throw new RuntimeException(errorMessage, e);
        }

        return null;
    }

    private String extractField(String field) {
        return field != null && !field.trim().isEmpty() ? field.trim() : "N/A";
    }
}
