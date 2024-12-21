package machine_learning;

import data.model.Offer;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Arrays;

public class JobRecommendationAdapter {
    
    public static Offer convertToOffer(jobRecommendation.Job job) {
        return new Offer(
            0, // id - using default since it's a new conversion
            job.jobTitle,
            job.url,
            "", // siteName
            new Date(), // datePublication - current date as default
            null, // datePostuler
            "", // adresseEntreprise
            "", // siteWebEntreprise
            job.company,
            "", // descriptionEntreprise
            String.join(", ", job.jobDetails), // descriptionPoste
            "", // region
            job.location, // ville
            "", // secteurActivite
            job.jobTitle, // metier - using jobTitle as it's the closest match
            job.contractType,
            job.studyLevel,
            "", // specialiteDiplome
            String.valueOf(job.encodedExperienceLevel) + " years", // experience
            "", // profilRecherche
            "", // traitsPersonnalite
            String.join(", ", job.jobDetails), // competencesRequises - using jobDetails as it might contain skills
            "", // softSkills
            "", // competencesRecommandees
            "", // langue
            "", // niveauLangue
            "", // salaire
            "", // avantagesSociaux
            false // teletravail
        );
    }

    public static List<Offer> convertToOffers(List<jobRecommendation.Job> jobs) {
        List<Offer> offers = new ArrayList<>();
        for (jobRecommendation.Job job : jobs) {
            offers.add(convertToOffer(job));
        }
        return offers;
    }
}
