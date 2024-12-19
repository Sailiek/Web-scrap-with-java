package data.dao;

import data.model.Offer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import data.util.DatabaseConnectionManager;

public class JobDAOImpl implements JobDAO {

    private static final int TYPE_CONTRAT_MAX_LENGTH = 50;

    @Override
    public void saveJob(Offer offer) {
        String query = "INSERT INTO jobs (titre, url, site_name, date_publication, date_postuler, " +
                "adresse_entreprise, site_web_entreprise, nom_entreprise, description_entreprise, " +
                "description_poste, region, ville, secteur_activite, metier, type_contrat, " +
                "niveau_etudes, specialite_diplome, experience, profil_recherche, traits_personnalite, " +
                "competences_requises, soft_skills, competences_recommandees, langue, niveau_langue, " +
                "salaire, avantages_sociaux, teletravail) VALUES " +
                "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            
            // Debug information
            System.out.println("Attempting to save job offer with title: " + offer.getTitre());
            
            // Set values with null checks
            stmt.setString(1, validateString(offer.getTitre(), "titre"));
            stmt.setString(2, offer.getUrl());
            stmt.setString(3, offer.getSiteName());
            stmt.setDate(4, offer.getDatePublication() != null ? new java.sql.Date(offer.getDatePublication().getTime()) : null);
            stmt.setDate(5, offer.getDatePostuler() != null ? new java.sql.Date(offer.getDatePostuler().getTime()) : null);
            stmt.setString(6, offer.getAdresseEntreprise());
            stmt.setString(7, offer.getSiteWebEntreprise());
            stmt.setString(8, offer.getNomEntreprise());
            stmt.setString(9, offer.getDescriptionEntreprise());
            stmt.setString(10, offer.getDescriptionPoste());
            stmt.setString(11, offer.getRegion());
            stmt.setString(12, offer.getVille());
            stmt.setString(13, offer.getSecteurActivite());
            stmt.setString(14, offer.getMetier());
            stmt.setString(15, truncateString(offer.getTypeContrat(), TYPE_CONTRAT_MAX_LENGTH));
            stmt.setString(16, offer.getNiveauEtudes());
            stmt.setString(17, offer.getSpecialiteDiplome());
            stmt.setString(18, offer.getExperience());
            stmt.setString(19, offer.getProfilRecherche());
            stmt.setString(20, offer.getTraitsPersonnalite());
            stmt.setString(21, offer.getCompetencesRequises());
            stmt.setString(22, offer.getSoftSkills());
            stmt.setString(23, offer.getCompetencesRecommandees());
            stmt.setString(24, offer.getLangue());
            stmt.setString(25, offer.getNiveauLangue());
            stmt.setString(26, offer.getSalaire());
            stmt.setString(27, offer.getAvantagesSociaux());
            stmt.setBoolean(28, offer.isTeletravail());
            
            // Execute the insert
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Successfully saved offer: " + offer.getTitre() + " (Rows affected: " + rowsAffected + ")");
            
        } catch (SQLException e) {
            String errorMessage = String.format("Error saving offer '%s': %s (SQL State: %s, Error Code: %d)", 
                offer.getTitre(), e.getMessage(), e.getSQLState(), e.getErrorCode());
            System.err.println(errorMessage);
            e.printStackTrace();
            throw new RuntimeException(errorMessage, e);
        }
    }

    private String validateString(String value, String fieldName) throws SQLException {
        if (value == null || value.trim().isEmpty()) {
            throw new SQLException("Field '" + fieldName + "' cannot be null or empty");
        }
        return value;
    }

    private String truncateString(String value, int maxLength) {
        if (value == null) {
            return null;
        }
        return value.length() > maxLength ? value.substring(0, maxLength) : value;
    }

    @Override
    public List<Offer> getAllJobs() {
        List<Offer> offers = new ArrayList<>();
        String query = "SELECT * FROM jobs";

        try (Connection connection = DatabaseConnectionManager.getConnection();
             Statement stmt = connection.createStatement(); 
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                try {
                    Offer offer = new Offer(
                        rs.getInt("id"),
                        rs.getString("titre"),
                        rs.getString("url"),
                        rs.getString("site_name"),
                        rs.getDate("date_publication"),
                        rs.getDate("date_postuler"),
                        rs.getString("adresse_entreprise"),
                        rs.getString("site_web_entreprise"),
                        rs.getString("nom_entreprise"),
                        rs.getString("description_entreprise"),
                        rs.getString("description_poste"),
                        rs.getString("region"),
                        rs.getString("ville"),
                        rs.getString("secteur_activite"),
                        rs.getString("metier"),
                        rs.getString("type_contrat"),
                        rs.getString("niveau_etudes"),
                        rs.getString("specialite_diplome"),
                        rs.getString("experience"),
                        rs.getString("profil_recherche"),
                        rs.getString("traits_personnalite"),
                        rs.getString("competences_requises"),
                        rs.getString("soft_skills"),
                        rs.getString("competences_recommandees"),
                        rs.getString("langue"),
                        rs.getString("niveau_langue"),
                        rs.getString("salaire"),
                        rs.getString("avantages_sociaux"),
                        rs.getBoolean("teletravail")
                    );
                    offers.add(offer);
                } catch (SQLException e) {
                    System.err.println("Error mapping result set to offer: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving offers: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to retrieve job offers", e);
        }
        return offers;
    }

    @Override
    public void emptyDatabase() {
        String query = "TRUNCATE TABLE jobs";
        try (Connection connection = DatabaseConnectionManager.getConnection();
             Statement stmt = connection.createStatement()) {
            
            System.out.println("Attempting to empty the jobs table...");
            stmt.executeUpdate(query);
            System.out.println("Successfully emptied the jobs table");
            
        } catch (SQLException e) {
            String errorMessage = "Error emptying database: " + e.getMessage();
            System.err.println(errorMessage);
            e.printStackTrace();
            throw new RuntimeException(errorMessage, e);
        }
    }
}
