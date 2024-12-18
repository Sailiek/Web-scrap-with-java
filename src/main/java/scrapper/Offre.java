package scrapper;

public class Offre 
{
	public Offre(
	    String titre,
	    String uRL,
	    String siteName,
	    String dateDePublication,
	    String datePourPostuler,
	    String adresseDEntreprise,
	    String siteWebDEntreprise,
	    String nomDEntreprise,
	    String descriptionDEntreprise,
	    String descriptionDuPoste,
	    String region,
	    String ville,
	    String secteurActivite,
	    String metier,
	    String typeDuContrat,
	    String niveauDEtudes,
	    String specialiteDiplome,
	    String experience,
	    String profilRecherche,
	    String traitsDePersonnalite,
	    String hardSkills,
	    String softSkills,
	    String competencesRecommandees,
	    String langue,
	    String niveauDeLaLangue,
	    String salaire,
	    String avantagesSociaux,
	    String teletravail
	) {
	    super();
	    this.titre = (titre != null) ? titre : "manquant";
	    this.URL = (uRL != null) ? uRL : "manquant";
	    this.siteName = (siteName != null) ? siteName : "manquant";
	    this.dateDePublication = (dateDePublication != null) ? dateDePublication : "manquant";
	    this.datePourPostuler = (datePourPostuler != null) ? datePourPostuler : "manquant";
	    this.adresseDEntreprise = (adresseDEntreprise != null) ? adresseDEntreprise : "manquant";
	    this.siteWebDEntreprise = (siteWebDEntreprise != null) ? siteWebDEntreprise : "manquant";
	    this.nomDEntreprise = (nomDEntreprise != null) ? nomDEntreprise : "manquant";
	    this.descriptionDEntreprise = (descriptionDEntreprise != null) ? descriptionDEntreprise : "manquant";
	    this.descriptionDuPoste = (descriptionDuPoste != null) ? descriptionDuPoste : "manquant";
	    this.region = (region != null) ? region : "manquant";
	    this.ville = (ville != null) ? ville : "manquant";
	    this.secteurActivite = (secteurActivite != null) ? secteurActivite : "manquant";
	    this.metier = (metier != null) ? metier : "manquant";
	    this.typeDuContrat = (typeDuContrat != null) ? typeDuContrat : "manquant";
	    this.niveauDEtudes = (niveauDEtudes != null) ? niveauDEtudes : "manquant";
	    this.specialiteDiplome = (specialiteDiplome != null) ? specialiteDiplome : "manquant";
	    this.experience = (experience != null) ? experience : "manquant";
	    this.profilRecherche = (profilRecherche != null) ? profilRecherche : "manquant";
	    this.traitsDePersonnalite = (traitsDePersonnalite != null) ? traitsDePersonnalite : "manquant";
	    this.hardSkills = (hardSkills != null) ? hardSkills : "manquant";
	    this.softSkills = (softSkills != null) ? softSkills : "manquant";
	    this.competencesRecommandees = (competencesRecommandees != null) ? competencesRecommandees : "manquant";
	    this.langue = (langue != null) ? langue : "manquant";
	    this.niveauDeLaLangue = (niveauDeLaLangue != null) ? niveauDeLaLangue : "manquant";
	    this.Salaire = (salaire != null) ? salaire : "manquant";
	    this.avantagesSociaux = (avantagesSociaux != null) ? avantagesSociaux : "manquant";
	    this.teletravail = (teletravail != null) ? teletravail : "manquant";
	}
	
	@Override
	public String toString() {
		return "Offre [titre=" + titre + ", URL=" + URL + ", siteName=" + siteName + ", dateDePublication="
				+ dateDePublication + ", datePourPostuler=" + datePourPostuler + ", adresseDEntreprise="
				+ adresseDEntreprise + ", siteWebDEntreprise=" + siteWebDEntreprise + ", nomDEntreprise="
				+ nomDEntreprise + ", descriptionDEntreprise=" + descriptionDEntreprise + ", descriptionDuPoste="
				+ descriptionDuPoste + ", region=" + region + ", ville=" + ville + ", secteurActivite="
				+ secteurActivite + ", metier=" + metier + ", typeDuContrat=" + typeDuContrat + ", niveauDEtudes="
				+ niveauDEtudes + ", specialiteDiplome=" + specialiteDiplome + ", experience=" + experience
				+ ", profilRecherche=" + profilRecherche + ", traitsDePersonnalite=" + traitsDePersonnalite
				+ ", hardSkills=" + hardSkills + ", softSkills=" + softSkills + ", competencesRecommandees="
				+ competencesRecommandees + ", langue=" + langue + ", niveauDeLaLangue=" + niveauDeLaLangue
				+ ", Salaire=" + Salaire + ", avantagesSociaux=" + avantagesSociaux + ", teletravail=" + teletravail
				+ "]";
	}


	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getDateDePublication() {
		return dateDePublication;
	}
	public void setDateDePublication(String dateDePublication) {
		this.dateDePublication = dateDePublication;
	}
	public String getDatePourPostuler() {
		return datePourPostuler;
	}
	public void setDatePourPostuler(String datePourPostuler) {
		this.datePourPostuler = datePourPostuler;
	}
	public String getAdresseDEntreprise() {
		return adresseDEntreprise;
	}
	public void setAdresseDEntreprise(String adresseDEntreprise) {
		this.adresseDEntreprise = adresseDEntreprise;
	}
	public String getSiteWebDEntreprise() {
		return siteWebDEntreprise;
	}
	public void setSiteWebDEntreprise(String siteWebDEntreprise) {
		this.siteWebDEntreprise = siteWebDEntreprise;
	}
	public String getNomDEntreprise() {
		return nomDEntreprise;
	}
	public void setNomDEntreprise(String nomDEntreprise) {
		this.nomDEntreprise = nomDEntreprise;
	}
	public String getDescriptionDEntreprise() {
		return descriptionDEntreprise;
	}
	public void setDescriptionDEntreprise(String descriptionDEntreprise) {
		this.descriptionDEntreprise = descriptionDEntreprise;
	}
	public String getDescriptionDuPoste() {
		return descriptionDuPoste;
	}
	public void setDescriptionDuPoste(String descriptionDuPoste) {
		this.descriptionDuPoste = descriptionDuPoste;
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
	public String getMetier() {
		return metier;
	}
	public void setMetier(String metier) {
		this.metier = metier;
	}
	public String getTypeDuContrat() {
		return typeDuContrat;
	}
	public void setTypeDuContrat(String typeDuContrat) {
		this.typeDuContrat = typeDuContrat;
	}
	public String getNiveauDEtudes() {
		return niveauDEtudes;
	}
	public void setNiveauDEtudes(String niveauDEtudes) {
		this.niveauDEtudes = niveauDEtudes;
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
	public String getTraitsDePersonnalite() {
		return traitsDePersonnalite;
	}
	public void setTraitsDePersonnalite(String traitsDePersonnalite) {
		this.traitsDePersonnalite = traitsDePersonnalite;
	}
	public String getHardSkills() {
		return hardSkills;
	}
	public void setHardSkills(String hardSkills) {
		this.hardSkills = hardSkills;
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
	public String getNiveauDeLaLangue() {
		return niveauDeLaLangue;
	}
	public void setNiveauDeLaLangue(String niveauDeLaLangue) {
		this.niveauDeLaLangue = niveauDeLaLangue;
	}
	public String getSalaire() {
		return Salaire;
	}
	public void setSalaire(String salaire) {
		Salaire = salaire;
	}
	public String getAvantagesSociaux() {
		return avantagesSociaux;
	}
	public void setAvantagesSociaux(String avantagesSociaux) {
		this.avantagesSociaux = avantagesSociaux;
	}
	public String getTeletravail() {
		return teletravail;
	}
	public void setTeletravail(String teletravail) {
		this.teletravail = teletravail;
	}
	
	private String titre;
	private String URL;
	private String siteName;
	private String dateDePublication;
	private String datePourPostuler;
	private String adresseDEntreprise;
	private String siteWebDEntreprise;
	private String nomDEntreprise;
	private String descriptionDEntreprise;
	private String descriptionDuPoste;
	private String region;
	private String ville;
	private String secteurActivite;
	private String metier;
	private String typeDuContrat;
	private String niveauDEtudes;
	private String specialiteDiplome;
	private String experience;
	private String profilRecherche;
	private String traitsDePersonnalite;
	private String hardSkills;
	private String softSkills;
	private String competencesRecommandees;
	private String langue;
	private String niveauDeLaLangue;
	private String Salaire;
	private String avantagesSociaux;
	private String teletravail;
}
