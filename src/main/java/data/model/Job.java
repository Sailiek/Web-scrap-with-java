package data.model;

public class Job {
    private int id;
    private String title;
    private String company;
    private String description;
    private String educationLevel;
    private String experience;
    private String contractType;
    private String jobLink;

    public Job(String title, String company, String description, String educationLevel, String experience, String contractType, String jobLink) {
        this.title = title;
        this.company = company;
        this.description = description;
        this.educationLevel = educationLevel;
        this.experience = experience;
        this.contractType = contractType;
        this.jobLink = jobLink;
    }

    public Job(int id, String title, String company, String description, String educationLevel, String experience, String contractType, String jobLink) {
        this(title, company, description, educationLevel, experience, contractType, jobLink);
        this.id = id;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getJobLink() {
        return jobLink;
    }

    public void setJobLink(String jobLink) {
        this.jobLink = jobLink;
    }
}
