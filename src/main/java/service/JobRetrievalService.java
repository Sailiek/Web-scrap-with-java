package service;

import data.dao.JobDAO;
import data.dao.JobDAOImpl;
import data.model.Offer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class JobRetrievalService {
    private final JobDAO jobDAO;

    public JobRetrievalService() {
        this.jobDAO = new JobDAOImpl();
    }

    public List<Offer> getAllJobs() {
        try {
            List<Offer> offers = jobDAO.getAllJobs();
            if (offers == null) {
                throw new RuntimeException("Database returned null instead of an empty list");
            }
            return offers;
        } catch (Exception e) {
            String errorMessage = "Error retrieving jobs from database: " + e.getMessage();
            System.err.println(errorMessage);
            e.printStackTrace();
            throw new RuntimeException(errorMessage, e);
        }
    }

    public List<Offer> searchJobs(String keyword) {
        if (keyword == null) {
            throw new IllegalArgumentException("Search keyword cannot be null");
        }

        keyword = keyword.trim().toLowerCase();
        if (keyword.isEmpty()) {
            throw new IllegalArgumentException("Search keyword cannot be empty");
        }

        try {
            List<Offer> allJobs = getAllJobs();
            List<Offer> matchingJobs = new ArrayList<>();

            for (Offer offer : allJobs) {
                if (matchesKeyword(offer, keyword)) {
                    matchingJobs.add(offer);
                }
            }

            return matchingJobs;
        } catch (Exception e) {
            String errorMessage = "Error searching jobs with keyword '" + keyword + "': " + e.getMessage();
            System.err.println(errorMessage);
            e.printStackTrace();
            throw new RuntimeException(errorMessage, e);
        }
    }

    private boolean matchesKeyword(Offer offer, String keyword) {
        if (offer == null || keyword == null || keyword.isEmpty()) {
            return false;
        }

        // Helper function to safely check if a string contains the keyword
        Function<String, Boolean> containsKeyword = text -> 
            text != null && !text.equals("N/A") && text.toLowerCase().contains(keyword);

        return containsKeyword.apply(offer.getTitre()) ||
               containsKeyword.apply(offer.getDescriptionPoste()) ||
               containsKeyword.apply(offer.getNomEntreprise()) ||
               containsKeyword.apply(offer.getVille()) ||
               containsKeyword.apply(offer.getRegion()) ||
               containsKeyword.apply(offer.getMetier()) ||
               containsKeyword.apply(offer.getSecteurActivite()) ||
               containsKeyword.apply(offer.getTypeContrat()) ||
               containsKeyword.apply(offer.getNiveauEtudes()) ||
               containsKeyword.apply(offer.getExperience()) ||
               containsKeyword.apply(offer.getCompetencesRequises()) ||
               containsKeyword.apply(offer.getSoftSkills()) ||
               containsKeyword.apply(offer.getCompetencesRecommandees()) ||
               containsKeyword.apply(offer.getLangue()) ||
               containsKeyword.apply(offer.getDescriptionEntreprise());
    }
}
