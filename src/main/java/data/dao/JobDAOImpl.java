package data.dao;

import data.model.Offer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import data.util.DatabaseConnectionManager;

public class JobDAOImpl implements JobDAO {

    private Connection connection;

    public JobDAOImpl() {
        this.connection = DatabaseConnectionManager.getConnection();
    }

    @Override
    public void saveJob(Offer offer) {
        String query = "INSERT INTO jobs (titre, url, site_name, date_publication, date_postuler, " +
                "adresse_entreprise, site_web_entreprise, nom_entreprise, description_entreprise, " +
                "description_poste, region, ville, secteur_activite, metier, type_contrat, " +
                "niveau_etudes, specialite_diplome, experience, profil_recherche, traits_personnalite, " +
                "competences_requises, soft_skills, competences_recommandees, langue, niveau_langue, " +
                "salaire, avantages_sociaux, teletravail) VALUES " +
                "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, offer.getTitre());
            stmt.setString(2, offer.getUrl());
            stmt.setString(3, offer.getSiteName());
            stmt.setDate(4, new java.sql.Date(offer.getDatePublication().getTime()));
            stmt.setDate(5, new java.sql.Date(offer.getDatePostuler().getTime()));
            stmt.setString(6, offer.getAdresseEntreprise());
            stmt.setString(7, offer.getSiteWebEntreprise());
            stmt.setString(8, offer.getNomEntreprise());
            stmt.setString(9, offer.getDescriptionEntreprise());
            stmt.setString(10, offer.getDescriptionPoste());
            stmt.setString(11, offer.getRegion());
            stmt.setString(12, offer.getVille());
            stmt.setString(13, offer.getSecteurActivite());
            stmt.setString(14, offer.getMetier());
            stmt.setString(15, offer.getTypeContrat());
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
            
            stmt.executeUpdate();
            System.out.println("Successfully saved offer: " + offer.getTitre());
        } catch (SQLException e) {
            System.err.println("Error saving offer: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<Offer> getAllJobs() {
        List<Offer> offers = new ArrayList<>();
        String query = "SELECT * FROM jobs";

        try (Statement stmt = connection.createStatement(); 
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
        }
        return offers;
    }
}
