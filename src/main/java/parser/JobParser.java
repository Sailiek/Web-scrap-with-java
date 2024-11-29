package parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JobParser extends  JobPosting {

    public static List<JobPosting> parseJobs(String rawData) {
        List<JobPosting> jobList = new ArrayList<>();

        // Split entries by delimiter (e.g., "----")
        String[] entries = rawData.split("----+");

        for (String entry : entries) {
            JobPosting job = new JobPosting();

            // Extract fields using regex patterns
            job.setTitle(extractField(entry, "Titre Annonce:"));
            job.setCompanyIntro(extractField(entry, "Intro Societe:"));
            job.setJobDescription(extractField(entry, "Description Offre:"));
            job.setRequiredSkills(extractField(entry, "Skills requis:"));
            job.setSoftSkills(extractField(entry, "Soft Skills:"));

            // Check for additional fields (if in a different format)
            job.setJobSection(extractField(entry, "Section: Poste propose :"));
            job.setProfileSection(extractField(entry, "Section: Profil recherche pour le poste :"));
            job.setCriteriaSection(extractField(entry, "Section: Criteres de l'annonce pour le poste :"));

            // Add to the job list
            jobList.add(job);
        }

        return jobList;
    }

    private static String extractField(String text, String fieldName) {
        // Regex to extract content after the field name
        String regex = fieldName + "\\s*(.+?)(?=\\n\\w|$)";
        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(text);
        return matcher.find() ? matcher.group(1).trim() : null;
    }
}
