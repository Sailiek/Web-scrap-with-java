package service;

import data.dao.JobDAO;
import data.dao.JobDAOImpl;
import data.model.Offer;

import java.util.Date;
import java.util.List;

public class JobInsertionService {
    private final JobDAO jobDAO;

    public JobInsertionService() {
        this.jobDAO = new JobDAOImpl();
    }

    public void insertOffer(Offer offer) {
        try {
            jobDAO.saveJob(offer);
        } catch (Exception e) {
            System.err.println("Error while inserting offer: " + offer.getTitre());
            e.printStackTrace();
        }
    }

    public void insertOffers(List<Offer> offers) {
        for (Offer offer : offers) {
            insertOffer(offer);
        }
    }

    // Legacy method for backward compatibility
    public void insertJobs(List<String> jobData) {
        for (String job : jobData) {
            try {
                String[] parsedData = parseScrapedData(job);

                if (parsedData != null && parsedData.length >= 8) {
                    // Create Date objects for publication and application dates
                    Date currentDate = new Date();

                    // Create Offer object with all available fields
                    Offer newOffer = new Offer(
                        0, // id will be set by database
                        parsedData[0], // title
                        parsedData[6], // url
                        "Unknown", // siteName
                        currentDate, // datePublication
                        currentDate, // datePostuler
                        parsedData[4], // adresseEntreprise (using location)
                        "", // siteWebEntreprise
                        parsedData[1], // nomEntreprise
                        "", // descriptionEntreprise
                        parsedData[7], // descriptionPoste
                        "", // region
                        parsedData[4], // ville (using location)
                        "", // secteurActivite
                        "", // metier
                        parsedData[5], // typeContrat
                        parsedData[2], // niveauEtudes
                        "", // specialiteDiplome
                        parsedData[3], // experience
                        "", // profilRecherche
                        "", // traitsPersonnalite
                        "", // competencesRequises
                        "", // softSkills
                        "", // competencesRecommandees
                        "", // langue
                        "", // niveauLangue
                        "", // salaire
                        "", // avantagesSociaux
                        false // teletravail
                    );

                    insertOffer(newOffer);
                } else {
                    System.out.println("Skipping invalid job data: " + job);
                }
            } catch (Exception e) {
                System.err.println("Error while inserting job: " + job);
                e.printStackTrace();
            }
        }
    }

    private String[] parseScrapedData(String job) {
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

                return new String[]{title, company, education, experience, localisation, contract, link, jobDetails};
            }
        } catch (Exception e) {
            System.err.println("Error while parsing job data: " + job);
            e.printStackTrace();
        }

        return null;
    }

    private String extractField(String field) {
        return field != null ? field.trim() : "";
    }
}
