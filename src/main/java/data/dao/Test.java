package data.dao;

import data.model.Job;

public class Test {
    public static void main(String[] args) {
        JobDAOImpl jobDAOImpl = new JobDAOImpl();
        Job job = new Job("Test Title", "Test Company", "Test Description", "Bachelor's", "3 years", "Full-Time", "http://example.com");
        jobDAOImpl.saveJob(job);
    }
}