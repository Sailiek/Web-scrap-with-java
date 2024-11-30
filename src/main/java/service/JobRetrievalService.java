package service;

import data.dao.JobDAO;
import data.dao.JobDAOImpl;
import data.model.Job;

import java.util.List;

public class JobRetrievalService {
    private final JobDAO jobDAO;

    public JobRetrievalService() {
        this.jobDAO = new JobDAOImpl(); // or inject it if you prefer Dependency Injection
    }

    // Retrieve all jobs from the database
    public List<Job> getAllJobs() {
        return jobDAO.getAllJobs();
    }
}
