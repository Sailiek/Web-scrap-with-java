package service;

import data.dao.JobDAO;
import data.dao.JobDAOImpl;
import data.model.Job;

import java.util.List;

public class JobInsertionService {

    public void insertJobs(List<String> jobData) {
        JobDAOImpl jobDAOImpl = new JobDAOImpl(); // Instantiate DAO

        for (String job : jobData) {
            try {
                String[] parsedData = parseScrapedData(job);

                if (parsedData != null && parsedData.length >=8) {
                    String title = parsedData[0];
                    String company = parsedData[1];
                    String education = parsedData[2];
                    String experience = parsedData[3];
                    String localisation = parsedData[4];
                    String contract = parsedData[5];
                    String link = parsedData[6];
                    String jobDetails = parsedData[7];

                    // Create Job object
                    Job newJob = new Job(title, company, "Description Placeholder", education, experience,localisation ,contract, link, jobDetails);

                    // Save job to the database
                    jobDAOImpl.saveJob(newJob);
                } else {
                    System.out.println("Skipping invalid job data: " + job);
                }
            } catch (Exception e) {
                System.err.println("Error while inserting job: " + job);
                e.printStackTrace();
            }
        }
    }


    private String[] parseScrapedData(String job) {
        try {
            String[] lines = job.split("\n");

            // Check if we have at least 7 fields
            if (lines.length >= 8) {
                String title = extractField(lines[0]);
                String company = extractField(lines[1]);
                String education = extractField(lines[2]);
                String experience = extractField(lines[3]);
                String localisation = extractField(lines[4]);
                String contract = extractField(lines[5]);
                String link = extractField(lines[6]);
                String jobDetails = extractField(lines[7]);

                // Return the parsed data in an array
                return new String[]{title, company, education, experience,localisation ,contract, link, jobDetails};
            }
        } catch (Exception e) {
            System.err.println("Error while parsing job data: " + job);
            e.printStackTrace();
        }

        return null; // Return null if the data is invalid
    }


    private String extractField(String field) {
        // Remove unwanted whitespace or other characters
        return field.replaceAll("[^\\w\\s]", "").trim();
    }
}
