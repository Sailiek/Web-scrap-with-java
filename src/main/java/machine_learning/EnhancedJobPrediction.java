package machine_learning;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;

import weka.classifiers.Classifier;
import weka.classifiers.trees.RandomForest;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

import data.model.Job;

public class EnhancedJobPrediction {

    public static double encodeStudyLevel(String studyLevel) {
        switch (studyLevel) {
            case "Bac":
                return 0;
            case "Bac +2":
                return 1;
            case "Bac +3":
                return 2;
            case "Bac +4":
                return 3;
            case "Bac +5":
                return 4;
            case "Master":
                return 5;
            case "Ecole d'ingénieur":
                return 6;
            default:
                return -1; // Unknown level
        }
    }

    public static double encodeExperienceLevel(String experienceLevel) {
        Pattern rangePattern = Pattern.compile("De (\\d+) à (\\d+) ans");
        Matcher rangeMatcher = rangePattern.matcher(experienceLevel);
        if (rangeMatcher.find()) {
            int start = Integer.parseInt(rangeMatcher.group(1));
            int end = Integer.parseInt(rangeMatcher.group(2));
            return (start + end) / 2.0;
        }

        Pattern lessThanPattern = Pattern.compile("Moins de (\\d+) an");
        Matcher lessThanMatcher = lessThanPattern.matcher(experienceLevel);
        if (lessThanMatcher.find()) {
            return Integer.parseInt(lessThanMatcher.group(1)) / 2.0;
        }

        Pattern moreThanPattern = Pattern.compile("Plus de (\\d+) ans");
        Matcher moreThanMatcher = moreThanPattern.matcher(experienceLevel);
        if (moreThanMatcher.find()) {
            return Integer.parseInt(moreThanMatcher.group(1)) + 1.0;
        }

        return 0; // Unknown experience level
    }

    public static List<Job> readJobsFromCSV(String filePath) throws IOException {
        List<Job> jobList = new ArrayList<>();

        try (Reader in = new FileReader(filePath)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader("Job Title", "Company", "Study Level",
                            "Experience Level", "Location", "Contract Type", "Job Details", "URL")
                    .withFirstRecordAsHeader().parse(in);

            for (CSVRecord record : records) {
                String jobTitle = record.get("Job Title");
                String company = record.get("Company");
                String studyLevel = record.get("Study Level");
                String experienceLevel = record.get("Experience Level");
                String location = record.get("Location");
                String contractType = record.get("Contract Type");
                String jobDetails = record.get("Job Details");
                String url = record.get("URL");

                Job job = new Job(jobTitle, company, "", studyLevel, experienceLevel, location, contractType, url, jobDetails);
                jobList.add(job);
            }
        }

        return jobList;
    }

    public static Instances createEnhancedWekaInstances(List<Job> jobs) {
        ArrayList<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute("encodedStudyLevel"));
        attributes.add(new Attribute("encodedExperienceLevel"));

        // Collect unique values for categorical attributes
        ArrayList<String> locations = new ArrayList<>();
        ArrayList<String> contractTypes = new ArrayList<>();
        for (Job job : jobs) {
            if (!locations.contains(job.getLocalisation())) {
                locations.add(job.getLocalisation());
            }
            if (!contractTypes.contains(job.getContractType())) {
                contractTypes.add(job.getContractType());
            }
        }

        attributes.add(new Attribute("location", locations));
        attributes.add(new Attribute("contractType", contractTypes));

        Instances dataset = new Instances("JobData", attributes, jobs.size());
        dataset.setClassIndex(0); // Example: encodedStudyLevel as the class

        for (Job job : jobs) {
            double[] instanceValues = new double[attributes.size()];
            instanceValues[0] = encodeStudyLevel(job.getEducationLevel());
            instanceValues[1] = encodeExperienceLevel(job.getExperience());
            instanceValues[2] = locations.indexOf(job.getLocalisation());
            instanceValues[3] = contractTypes.indexOf(job.getContractType());
            dataset.add(new DenseInstance(1.0, instanceValues));
        }

        return dataset;
    }

    public static Classifier trainModel(Instances data) throws Exception {
        data.setClassIndex(0);
        Classifier model = new RandomForest();
        model.buildClassifier(data);
        return model;
    }

    public static String findClosestJobTitle(String inputTitle, List<Job> jobList) {
        String closestTitle = "";
        int minDistance = Integer.MAX_VALUE;

        for (Job job : jobList) {
            int distance = StringUtils.getLevenshteinDistance(inputTitle.toLowerCase(), job.getTitle().toLowerCase());
            if (distance < minDistance) {
                minDistance = distance;
                closestTitle = job.getTitle();
            }
        }

        return closestTitle;
    }

    public static Job predictJobDetails(String jobTitle) throws Exception {
        List<Job> jobs = readJobsFromCSV("src/main/java/machine_learning/final_inchaelh.csv");
        String closestJobTitle = findClosestJobTitle(jobTitle, jobs);

        return jobs.stream()
                .filter(job -> job.getTitle().equals(closestJobTitle))
                .findFirst()
                .orElse(null);
    }

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter job title (e.g., 'Product Manager', 'Technician'): ");
            String jobTitle = scanner.nextLine();
            
            Job predictedJob = predictJobDetails(jobTitle);
            if (predictedJob != null) {
                System.out.println("Closest job title: " + predictedJob.getTitle());
                System.out.println("Predicted Study Level: " + predictedJob.getEducationLevel());
                System.out.println("Predicted Experience Level: " + predictedJob.getExperience());
            } else {
                System.out.println("No matching job found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
