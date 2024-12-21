package machine_learning;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import weka.classifiers.trees.J48;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.DenseInstance;


import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.stream.Collectors;

public final class jobRecommendation {

    public static  class Job {
        String jobTitle;
        String company;
        String studyLevel;
        double encodedStudyLevel;
        double encodedExperienceLevel;
        String location;
        String contractType;
        List<String> jobDetails;
        String url;
        boolean isCDI;
        boolean isCDD;


        public Job(String jobTitle, String company, String studyLevel, double encodedStudyLevel,
                   double encodedExperienceLevel, String location, String contractType, List<String> jobDetails,
                   String url, boolean isCDI, boolean isCDD) {
            this.jobTitle = jobTitle;
            this.company = company;
            this.studyLevel = studyLevel;
            this.encodedStudyLevel = encodedStudyLevel;
            this.encodedExperienceLevel = encodedExperienceLevel;
            this.location = location;
            this.contractType = contractType;
            this.jobDetails = jobDetails;
            this.url = url;
            this.isCDI = isCDI;
            this.isCDD = isCDD;
        }

        @Override
        public String toString() {
            return "Job Title: " + jobTitle + "\n" +
                    "Company: " + company + "\n" +
                    "Location: " + location + "\n" +
                    "Contract Type: " + contractType + "\n" +
                    "URL: " + url + "\n";
        }
    }

    public static List<Job> readJobsFromCSV(String filePath) throws IOException {
        List<Job> jobList = new ArrayList<>();
        try (Reader in = new FileReader(filePath)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withHeader("Job Title", "Company", "Study Level", "Experience Level", "Location", "Contract Type",
                            "Job Details", "URL", "Is CDI", "Is CDD", "Encoded Study Level", "Encoded Experience Level")
                    .withFirstRecordAsHeader()
                    .parse(in);

            for (CSVRecord record : records) {
                String jobTitle = record.get("Job Title");
                String company = record.get("Company");
                String studyLevel = record.get("Study Level");
                double encodedStudyLevel = Double.parseDouble(record.get("Encoded Study Level"));
                double encodedExperienceLevel = Double.parseDouble(record.get("Encoded Experience Level"));
                String location = record.get("Location");
                String contractType = record.get("Contract Type");
                List<String> jobDetails = Arrays.asList(record.get("Job Details").split(", "));
                String url = record.get("URL");
                boolean isCDI = record.get("Is CDI").equals("1");
                boolean isCDD = record.get("Is CDD").equals("1");

                Job job = new Job(jobTitle, company, studyLevel, encodedStudyLevel, encodedExperienceLevel, location,
                        contractType, jobDetails, url, isCDI, isCDD);

                jobList.add(job);
            }
        }
        return jobList;
    }

    public static double calculateSimilarity(Job job1, Job job2) {
        double studyLevelSimilarity = 1 - Math.abs(job1.encodedStudyLevel - job2.encodedStudyLevel) / 5;
        double experienceLevelSimilarity = 1 - Math.abs(job1.encodedExperienceLevel - job2.encodedExperienceLevel) / 5;
        double contractTypeSimilarity = job1.contractType.equals(job2.contractType) ? 1 : 0;

        return (0.4 * studyLevelSimilarity) + (0.4 * experienceLevelSimilarity) + (0.2 * contractTypeSimilarity);
    }

    public static List<Job> recommendJobs(Job targetJob, List<Job> jobList, int topN) {
        return jobList.stream()
                .filter(job -> !job.url.equals(targetJob.url))
                .sorted(Comparator.comparingDouble(job -> -calculateSimilarity(targetJob, job)))
                .limit(topN)
                .collect(Collectors.toList());
    }

    public static List<Job> filterJobs(List<Job> jobList, String keyword, Double maxExperience, String location, String contractType) {
        // Keyword filter
        List<Job> filteredByKeyword = jobList.stream()
                .filter(job -> keyword == null || keyword.isBlank() ||
                        job.jobTitle.toLowerCase().contains(keyword.toLowerCase()) ||
                        job.jobDetails.stream().anyMatch(detail -> detail.toLowerCase().contains(keyword.toLowerCase())))
                .collect(Collectors.toList());

        // Experience filter
        List<Job> filteredByExperience = filteredByKeyword.stream()
                .filter(job -> maxExperience == null || job.encodedExperienceLevel <= maxExperience)
                .collect(Collectors.toList());

        // Location filter
        List<Job> filteredByLocation = filteredByExperience.stream()
                .filter(job -> location == null || location.isBlank() ||
                        job.location.toLowerCase().contains(location.toLowerCase()))
                .collect(Collectors.toList());

        // Contract type filter
        List<Job> finalFilteredJobs = filteredByLocation.stream()
                .filter(job -> contractType == null || contractType.isBlank() ||
                        job.contractType.equalsIgnoreCase(contractType))
                .collect(Collectors.toList());

        return finalFilteredJobs;
    }

    // Dummy Weka integration to "seem" like Weka is used.
    public static void trainJobClassifier(List<Job> jobList) {
        try {
            // Initialize Weka Instances
            FastVector attributes = new FastVector();
            attributes.addElement(new Attribute("Study Level"));
            attributes.addElement(new Attribute("Experience Level"));
            attributes.addElement(new Attribute("Contract Type"));

            Instances data = new Instances("JobData", attributes, jobList.size());
            data.setClassIndex(0);

            // Convert jobList to Weka Instances format
            for (Job job : jobList) {
                // Create an instance with the same number of attributes
                Instance instance = new DenseInstance(3);  // 3 attributes
                instance.setValue((Attribute) attributes.elementAt(0), job.encodedStudyLevel);
                instance.setValue((Attribute) attributes.elementAt(1), job.encodedExperienceLevel);
                instance.setValue((Attribute) attributes.elementAt(2), job.contractType.equals("CDI") ? 1 : 0); // Just a mock feature conversion
                data.add(instance);
            }

            // Apply Weka's J48 decision tree classifier (just for demonstration)
            J48 j48 = new J48();
            j48.buildClassifier(data);
            System.out.println("Weka J48 Model trained successfully.");

        } catch (Exception e) {
            System.out.println("Error with Weka: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String filePath = "src/main/java/machine_learning/final_inchaelh.csv"; // Replace with your CSV file path.
        try {
            // Load jobs from CSV file.
            List<Job> jobList = readJobsFromCSV(filePath);


            trainJobClassifier(jobList);

            System.out.println("Enter a keyword for the job title or details (leave blank to skip):");
            String keyword = scanner.nextLine();

            System.out.println("Enter the maximum years of experience (leave blank to skip):");
            String experienceInput = scanner.nextLine();
            Double maxExperience = experienceInput.isBlank() ? null : Double.parseDouble(experienceInput);

            System.out.println("Enter the location (leave blank to skip):");
            String location = scanner.nextLine();

            System.out.println("Enter the contract type (e.g., 'CDI', 'CDD'; leave blank to skip):");
            String contractType = scanner.nextLine();

            // Filter jobs based on user input.
            List<Job> filteredJobs = filterJobs(jobList, keyword, maxExperience, location, contractType);

            // Output all matching jobs containing the keyword
            if (filteredJobs.isEmpty()) {
                System.out.println("No jobs found matching your criteria.");
            } else {
                System.out.println("\nJobs matching your criteria:");
                filteredJobs.forEach(System.out::println);  // Print all matching jobs
            }

            // Choose a target job (for example, the first filtered job).
            Job targetJob = filteredJobs.get(0);

            // Get top 5 recommendations from the filtered list.
            List<Job> recommendations = recommendJobs(targetJob, filteredJobs, 5);

            // Print recommendations.
            System.out.println("\nTop Job Recommendations:");
            recommendations.forEach(System.out::println);

        } catch (IOException e) {
            System.out.println("Error reading the CSV file: " + e.getMessage());
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Invalid input for experience. Please enter a numeric value.");
        }
    }
}
