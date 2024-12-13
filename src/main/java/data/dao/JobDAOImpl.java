package data.dao;

import data.model.Job;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import data.util.DatabaseConnectionManager;

public class JobDAOImpl implements JobDAO {

    private Connection connection;

    public JobDAOImpl() {
        this.connection = DatabaseConnectionManager.getConnection();
    }

    @Override
    public void saveJob(Job job) {
        String query = "INSERT INTO jobs (title, company, description, education_level, experience, localisation,contract_type, job_link,jobDetails) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, job.getTitle());
            stmt.setString(2, job.getCompany());
            stmt.setString(3, job.getDescription());
            stmt.setString(4, job.getEducationLevel());
            stmt.setString(5, job.getExperience());
            stmt.setString(6, job.getLocalisation());
            stmt.setString(7, job.getContractType());
            stmt.setString(8, job.getJobLink());
            stmt.setString(9, job.getJobDetails());
            stmt.executeUpdate();
            System.out.println("Inserting job: " + job);
            System.out.println("Query: " + query);
        } catch (SQLException e) {
            System.err.println("Error saving job: " + e.getMessage());
        }
    }

    @Override
    public List<Job> getAllJobs() {
        List<Job> jobs = new ArrayList<>();
        String query = "SELECT * FROM jobs";

        try (Statement stmt = connection.createStatement(); ResultSet resultSet = stmt.executeQuery(query)) {
            while (resultSet.next()) {
                Job job = new Job(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("company"),
                        resultSet.getString("description"),
                        resultSet.getString("education_level"),
                        resultSet.getString("experience"),
                        resultSet.getString("localisation"),
                        resultSet.getString("contract_type"),
                        resultSet.getString("job_link"),
                        resultSet.getString("jobDetails")
                );
                jobs.add(job);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving jobs: " + e.getMessage());
        }
        return jobs;
    }
}
