package data.dao;


import data.model.Job;

import java.util.List;

public interface JobDAO {
    void saveJob(Job job);
    List<Job> getAllJobs();
}
