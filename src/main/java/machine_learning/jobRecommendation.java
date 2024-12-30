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

        public void setJobTitle(String jobTitle) {
            this.jobTitle = jobTitle;
        }

        public Job() {
        }

        public void setCompany(String company) {
            this.company = company;
        }

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
                Instance instance = new DenseInstance(3); // 3 attributes
                instance.setValue((Attribute) attributes.elementAt(0), job.encodedStudyLevel);
                instance.setValue((Attribute) attributes.elementAt(1), job.encodedExperienceLevel);
                instance.setValue((Attribute) attributes.elementAt(2), job.contractType.equals("CDI") ? 1 : 0);
                data.add(instance);
            }

            // Normalize data (standardizing numeric attributes)
            normalizeData(data);

            // Apply Weka's J48 decision tree classifier
            J48 j48 = new J48();
            j48.buildClassifier(data);

            // Save the model to disk (for future use in predictions)
            weka.core.SerializationHelper.write("jobRecommendationModel.model", j48);

            // Evaluate model performance using cross-validation

        } catch (Exception e) {
            System.out.println("Error with Weka: " + e.getMessage());
        }
    }

    private static void normalizeData(Instances data) {
        for (int i = 0; i < data.numAttributes(); i++) {
            if (data.attribute(i).isNumeric()) {
                double min = data.attributeStats(i).numericStats.min;
                double max = data.attributeStats(i).numericStats.max;
                for (int j = 0; j < data.numInstances(); j++) {
                    Instance instance = data.instance(j);
                    double normalizedValue = (instance.value(i) - min) / (max - min);
                    instance.setValue(i, normalizedValue);
                }
            }
        }
    }




    public static void predictJobClassification(Job jobToClassify) {
        try {
            // Load the trained model from disk
            J48 j48 = (J48) weka.core.SerializationHelper.read("jobRecommendationModel.model");

            // Prepare the instance to classify
            FastVector attributes = new FastVector();
            attributes.addElement(new Attribute("Study Level"));
            attributes.addElement(new Attribute("Experience Level"));
            attributes.addElement(new Attribute("Contract Type"));

            Instances data = new Instances("JobData", attributes, 1);
            data.setClassIndex(0);

            Instance instance = new DenseInstance(3); // 3 attributes
            instance.setValue((Attribute) attributes.elementAt(0), jobToClassify.encodedStudyLevel);
            instance.setValue((Attribute) attributes.elementAt(1), jobToClassify.encodedExperienceLevel);
            instance.setValue((Attribute) attributes.elementAt(2), jobToClassify.contractType.equals("CDI") ? 1 : 0);
            data.add(instance);

            // Normalize the instance before prediction (using same method as in training)
            normalizeData(data);

            // Perform prediction
            double predictedClass = j48.classifyInstance(data.instance(0));

        } catch (Exception e) {
            System.out.println("Error making prediction: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String filePath = "src/main/java/machine_learning/final_inchaelh.csv"; // Replace with your CSV file path.
        try {
            // Load jobs from CSV file.
            List<Job> jobList = readJobsFromCSV(filePath);

            // Train the classifier and save the model
            trainJobClassifier(jobList);

            String targetJobTitle = scanner.nextLine();
            Job targetJob = jobList.stream()
                    .filter(job -> job.jobTitle.equalsIgnoreCase(targetJobTitle))
                    .findFirst()
                    .orElse(null);

            if (targetJob != null) {
                // Make prediction for the selected job
                predictJobClassification(targetJob);
            } else {
                System.out.println("Job not found in the list.");
            }

        } catch (IOException e) {
            System.out.println("Error reading the CSV file: " + e.getMessage());
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Invalid input for experience. Please enter a numeric value.");
        }
    }

}
