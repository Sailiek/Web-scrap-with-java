package service;

import data.dao.JobDAO;
import data.dao.JobDAOImpl;
import data.model.Offer;

import java.util.ArrayList;
import java.util.List;

public class JobRetrievalService {
    private final JobDAO jobDAO;

    public JobRetrievalService() {
        this.jobDAO = new JobDAOImpl();
    }

    public List<Offer> getAllJobs() {
        try {
            List<Offer> offers = jobDAO.getAllJobs();
            if (offers == null) {
                System.err.println("Warning: No jobs found in database");
                return new ArrayList<>();
            }
            return offers;
        } catch (Exception e) {
            System.err.println("Error retrieving jobs from database: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Offer> searchJobs(String keyword) {
        List<Offer> allJobs = getAllJobs();
        List<Offer> matchingJobs = new ArrayList<>();

        for (Offer offer : allJobs) {
            if (matchesKeyword(offer, keyword)) {
                matchingJobs.add(offer);
            }
        }

        return matchingJobs;
    }

    private boolean matchesKeyword(Offer offer, String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return false;
        }
        
        keyword = keyword.toLowerCase();
        
        return (offer.getTitre() != null && offer.getTitre().toLowerCase().contains(keyword)) ||
               (offer.getDescriptionPoste() != null && offer.getDescriptionPoste().toLowerCase().contains(keyword)) ||
               (offer.getNomEntreprise() != null && offer.getNomEntreprise().toLowerCase().contains(keyword)) ||
               (offer.getVille() != null && offer.getVille().toLowerCase().contains(keyword)) ||
               (offer.getMetier() != null && offer.getMetier().toLowerCase().contains(keyword)) ||
               (offer.getSecteurActivite() != null && offer.getSecteurActivite().toLowerCase().contains(keyword)) ||
               (offer.getCompetencesRequises() != null && offer.getCompetencesRequises().toLowerCase().contains(keyword));
    }
}
