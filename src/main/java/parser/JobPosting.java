package parser;

public class JobPosting {
    private String title;
    private String companyIntro;
    private String jobDescription;
    private String requiredSkills;
    private String softSkills;

    // Additional fields for the second job format
    private String jobSection;
    private String profileSection;
    private String criteriaSection;

    // Getter and Setter for Title
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Getter and Setter for Company Intro
    public String getCompanyIntro() {
        return companyIntro;
    }

    public void setCompanyIntro(String companyIntro) {
        this.companyIntro = companyIntro;
    }

    // Getter and Setter for Job Description
    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    // Getter and Setter for Required Skills
    public String getRequiredSkills() {
        return requiredSkills;
    }

    public void setRequiredSkills(String requiredSkills) {
        this.requiredSkills = requiredSkills;
    }

    // Getter and Setter for Soft Skills
    public String getSoftSkills() {
        return softSkills;
    }

    public void setSoftSkills(String softSkills) {
        this.softSkills = softSkills;
    }

    // Getter and Setter for Job Section (if applicable)
    public String getJobSection() {
        return jobSection;
    }

    public void setJobSection(String jobSection) {
        this.jobSection = jobSection;
    }

    // Getter and Setter for Profile Section
    public String getProfileSection() {
        return profileSection;
    }

    public void setProfileSection(String profileSection) {
        this.profileSection = profileSection;
    }

    // Getter and Setter for Criteria Section
    public String getCriteriaSection() {
        return criteriaSection;
    }

    public void setCriteriaSection(String criteriaSection) {
        this.criteriaSection = criteriaSection;
    }

    // Optional: toString() method for easy printing (for debugging)
    @Override
    public String toString() {
        return "JobPosting{" +
                "title='" + title + '\'' +
                ", companyIntro='" + companyIntro + '\'' +
                ", jobDescription='" + jobDescription + '\'' +
                ", requiredSkills='" + requiredSkills + '\'' +
                ", softSkills='" + softSkills + '\'' +
                ", jobSection='" + jobSection + '\'' +
                ", profileSection='" + profileSection + '\'' +
                ", criteriaSection='" + criteriaSection + '\'' +
                '}';
    }
}
