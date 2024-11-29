package parser;
// package ML_test;

// import org.deeplearning4j.clustering.algorithm.KMeansClustering;
// import org.deeplearning4j.clustering.cluster.ClusterSet;
// import org.deeplearning4j.clustering.cluster.Point;
// import org.nd4j.linalg.factory.Nd4j;
// import org.nd4j.linalg.api.ndarray.INDArray;

// import java.util.*;
// import java.util.stream.Collectors;

// public class JobTextClustering {

//     public static void main(String[] args) {
//         // Raw job text data
//         String[] rawJobData = {
//             "Job Title: Professeur Permanent en Communication - Casablanca\n" +
//             "Section: Poste propose : Professeur Permanent en Communication - Casablanca\n" +
//             "- Enseignement (cours et des Travaux Dirigés) et autres activités pédagogiques\n" +
//             "- Encadrement pédagogique et suivi de projets étudiants\n" +
//             "- Recherche scientifique dans le domaine de spécialisation.\n" +
//             "Section: Profil recherche pour le poste : Professeur Permanent en Communication - Casablanca\n" +
//             "- Titulaire obligatoirement d'un doctorat en communication, marketing, journalisme, linguistique\n" +
//             "- Une expérience professionnelle est obligatoire\n" +
//             "- Une experience dans l'enseignement est souhaitable.\n" +
//             "Section: Criteres de l'annonce pour le poste : Professeur Permanent en Communication - Casablanca",

//             "Job Title: Délégués Médicaux - Casablanca\n" +
//             "Section: Poste proposé : Délégués Médicaux - Casablanca\n" +
//             "- Relation client: Écoute active, conseil personnalisé, fidélisation.\n" +
//             "- Communication: Présentation claire et convaincante des prestations proposées.\n" +
//             "- Vente: Négociation, conclusion de contrats.\n" +
//             "Marketing: Identification des besoins des clients, elaboration d'offres commerciales.\n" +
//             "Section: Profil recherché pour le poste : Délégués Médicaux - Casablanca\n" +
//             "- . De formation Bac+4/ Bac+3, ayant une expérience de 1 an minimum dans un poste similaire. Très bonne élocution. Dynamique. Ayant le sens du travail en équipe pluridisciplinaire, du dialogue et du relationnel. Force de proposition et esprit de synthèse\n" +
//             "Section: Critères de l'annonce pour le poste : Délégués Médicaux - Casablanca"
//         };

//         // Step 1: Parse text data into structured objects
//         List<JobData> jobDataList = Arrays.stream(rawJobData)
//                                           .map(JobTextClustering::parseJobData)
//                                           .collect(Collectors.toList());

//         // Step 2: Vectorize data into numerical features
//         List<Point> points = vectorizeJobData(jobDataList);

//         // Step 3: Apply K-Means Clustering
//         int numClusters = 2; // Adjust as needed
//         KMeansClustering kMeans = KMeansClustering.setup(numClusters, 100, "cosinesimilarity");
//         ClusterSet clusterSet = kMeans.applyTo(points);

//         // Step 4: Print clustered results
//         printClusters(clusterSet, jobDataList);
//     }

//     // Parse raw text into a structured JobData object
//     private static JobData parseJobData(String rawText) {
//         String[] lines = rawText.split("\n");
//         String jobTitle = lines[0].replace("Job Title: ", "").trim();
//         String description = Arrays.stream(lines)
//                                     .filter(line -> line.startsWith("Section: Poste") || line.startsWith("- "))
//                                     .collect(Collectors.joining(" "));
//         String profile = Arrays.stream(lines)
//                                .filter(line -> line.startsWith("Section: Profil"))
//                                .collect(Collectors.joining(" "));
//         return new JobData(jobTitle, description, profile);
//     }

//     // Convert JobData objects into numerical vectors
//     private static List<Point> vectorizeJobData(List<JobData> jobDataList) {
//         // Example simple vectorization: count keyword occurrences
//         String[] keywords = {"communication", "teaching", "marketing", "research", "sales", "team"};
//         List<Point> points = new ArrayList<>();

//         for (JobData job : jobDataList) {
//             double[] vector = new double[keywords.length];
//             for (int i = 0; i < keywords.length; i++) {
//                 vector[i] = countOccurrences(job.getDescription().toLowerCase(), keywords[i])
//                           + countOccurrences(job.getProfile().toLowerCase(), keywords[i]);
//             }
//             points.add(new Point(Nd4j.create(vector)));
//         }
//         return points;
//     }

//     // Count occurrences of a keyword in text
//     private static int countOccurrences(String text, String keyword) {
//         return text.split(keyword, -1).length - 1;
//     }

//     // Print clustered results
//     private static void printClusters(ClusterSet clusterSet, List<JobData> jobDataList) {
//         List<List<JobData>> clusters = clusterSet.getClusters().stream()
//                 .map(cluster -> cluster.getPoints().stream()
//                         .map(Point::getId)
//                         .map(Integer::parseInt)
//                         .map(jobDataList::get)
//                         .collect(Collectors.toList()))
//                 .collect(Collectors.toList());

//         for (int i = 0; i < clusters.size(); i++) {
//             System.out.println("Cluster " + (i + 1) + ":");
//             for (JobData job : clusters.get(i)) {
//                 System.out.println(" - " + job);
//             }
//         }
//     }

//     // Data structure for jobs
//     static class JobData {
//         private final String jobTitle;
//         private final String description;
//         private final String profile;

//         public JobData(String jobTitle, String description, String profile) {
//             this.jobTitle = jobTitle;
//             this.description = description;
//             this.profile = profile;
//         }

//         public String getJobTitle() {
//             return jobTitle;
//         }

//         public String getDescription() {
//             return description;
//         }

//         public String getProfile() {
//             return profile;
//         }

//         @Override
//         public String toString() {
//             return "Job Title: " + jobTitle + ", Description: " + description + ", Profile: " + profile;
//         }
//     }
// }
