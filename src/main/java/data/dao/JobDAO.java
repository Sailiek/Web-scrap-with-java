package data.dao;

import data.model.Job;
import data.model.Offer;

import java.util.List;

public interface JobDAO {
    void saveJob(Offer offre);
    List<Offer> getAllJobs();
    void emptyDatabase();
}
