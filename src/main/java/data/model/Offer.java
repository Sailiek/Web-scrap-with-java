package data.model;

import java.util.Date;

public class Offer {
    private int id;
    private String titre;
    private String url;
    private String siteName;
    private Date datePublication;
    private Date datePostuler;
    private String adresseEntreprise;
    private String siteWebEntreprise;
    private String nomEntreprise;
    private String descriptionEntreprise;
    private String descriptionPoste;
    private String region;
    private String ville;
    private String secteurActivite;
    private String metier;
    private String typeContrat;
    private String niveauEtudes;
    private String specialiteDiplome;
    private String experience;
    private String profilRecherche;
    private String traitsPersonnalite;
    private String competencesRequises;
    private String softSkills;
    private String competencesRecommandees;
    private String langue;
    private String niveauLangue;
    private String salaire;
    private String avantagesSociaux;
    private boolean teletravail;

    // Constructor
    public Offer(int id, String titre, String url, String siteName, Date datePublication, Date datePostuler,
                 String adresseEntreprise, String siteWebEntreprise, String nomEntreprise,
                 String descriptionEntreprise, String descriptionPoste, String region, String ville,
                 String secteurActivite, String metier, String typeContrat, String niveauEtudes,
                 String specialiteDiplome, String experience, String profilRecherche,
                 String traitsPersonnalite, String competencesRequises, String softSkills,
                 String competencesRecommandees, String langue, String niveauLangue,
                 String salaire, String avantagesSociaux, boolean teletravail) {
        this.id = id;
        this.titre = titre;
        this.url = url;
        this.siteName = siteName;
        this.datePublication = datePublication;
        this.datePostuler = datePostuler;
        this.adresseEntreprise = adresseEntreprise;
        this.siteWebEntreprise = siteWebEntreprise;
        this.nomEntreprise = nomEntreprise;
        this.descriptionEntreprise = descriptionEntreprise;
        this.descriptionPoste = descriptionPoste;
        this.region = region;
        this.ville = ville;
        this.secteurActivite = secteurActivite;
        this.metier = metier;
        this.typeContrat = typeContrat;
        this.niveauEtudes = niveauEtudes;
        this.specialiteDiplome = specialiteDiplome;
        this.experience = experience;
        this.profilRecherche = profilRecherche;
        this.traitsPersonnalite = traitsPersonnalite;
        this.competencesRequises = competencesRequises;
        this.softSkills = softSkills;
        this.competencesRecommandees = competencesRecommandees;
        this.langue = langue;
        this.niveauLangue = niveauLangue;
        this.salaire = salaire;
        this.avantagesSociaux = avantagesSociaux;
        this.teletravail = teletravail;
    }

    public Offer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public Date getDatePublication() {
        return datePublication;
    }

    public void setDatePublication(Date datePublication) {
        this.datePublication = datePublication;
    }

    public Date getDatePostuler() {
        return datePostuler;
    }

    public void setDatePostuler(Date datePostuler) {
        this.datePostuler = datePostuler;
    }

    public String getAdresseEntreprise() {
        return adresseEntreprise;
    }

    public void setAdresseEntreprise(String adresseEntreprise) {
        this.adresseEntreprise = adresseEntreprise;
    }

    public String getSiteWebEntreprise() {
        return siteWebEntreprise;
    }

    public void setSiteWebEntreprise(String siteWebEntreprise) {
        this.siteWebEntreprise = siteWebEntreprise;
    }

    public String getNomEntreprise() {
        return nomEntreprise;
    }

    public void setNomEntreprise(String nomEntreprise) {
        this.nomEntreprise = nomEntreprise;
    }

    public String getDescriptionEntreprise() {
        return descriptionEntreprise;
    }

    public void setDescriptionEntreprise(String descriptionEntreprise) {
        this.descriptionEntreprise = descriptionEntreprise;
    }

    public String getDescriptionPoste() {
        return descriptionPoste;
    }

    public void setDescriptionPoste(String descriptionPoste) {
        this.descriptionPoste = descriptionPoste;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getSecteurActivite() {
        return secteurActivite;
    }

    public void setSecteurActivite(String secteurActivite) {
        this.secteurActivite = secteurActivite;
    }

    public String getTypeContrat() {
        return typeContrat;
    }

    public void setTypeContrat(String typeContrat) {
        this.typeContrat = typeContrat;
    }

    public String getMetier() {
        return metier;
    }

    public void setMetier(String metier) {
        this.metier = metier;
    }

    public String getNiveauEtudes() {
        return niveauEtudes;
    }

    public void setNiveauEtudes(String niveauEtudes) {
        this.niveauEtudes = niveauEtudes;
    }

    public String getSpecialiteDiplome() {
        return specialiteDiplome;
    }

    public void setSpecialiteDiplome(String specialiteDiplome) {
        this.specialiteDiplome = specialiteDiplome;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getProfilRecherche() {
        return profilRecherche;
    }

    public void setProfilRecherche(String profilRecherche) {
        this.profilRecherche = profilRecherche;
    }

    public String getTraitsPersonnalite() {
        return traitsPersonnalite;
    }

    public void setTraitsPersonnalite(String traitsPersonnalite) {
        this.traitsPersonnalite = traitsPersonnalite;
    }

    public String getCompetencesRequises() {
        return competencesRequises;
    }

    public void setCompetencesRequises(String competencesRequises) {
        this.competencesRequises = competencesRequises;
    }

    public String getSoftSkills() {
        return softSkills;
    }

    public void setSoftSkills(String softSkills) {
        this.softSkills = softSkills;
    }

    public String getCompetencesRecommandees() {
        return competencesRecommandees;
    }

    public void setCompetencesRecommandees(String competencesRecommandees) {
        this.competencesRecommandees = competencesRecommandees;
    }

    public String getLangue() {
        return langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public String getNiveauLangue() {
        return niveauLangue;
    }

    public void setNiveauLangue(String niveauLangue) {
        this.niveauLangue = niveauLangue;
    }

    public String getSalaire() {
        return salaire;
    }

    public void setSalaire(String salaire) {
        this.salaire = salaire;
    }

    public String getAvantagesSociaux() {
        return avantagesSociaux;
    }

    public void setAvantagesSociaux(String avantagesSociaux) {
        this.avantagesSociaux = avantagesSociaux;
    }

    public boolean isTeletravail() {
        return teletravail;
    }

    public void setTeletravail(boolean teletravail) {
        this.teletravail = teletravail;
    }
}
