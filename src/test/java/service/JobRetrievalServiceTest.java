package service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import data.dao.JobDAO;
import data.model.Offer;

@ExtendWith(MockitoExtension.class)
public class JobRetrievalServiceTest {

    @Mock
    private JobDAO mockJobDAO;

    private JobRetrievalService jobRetrievalService;

    @BeforeEach
    void setUp() {
        // Create service with mocked DAO
        jobRetrievalService = new JobRetrievalService() {
            @Override
            protected JobDAO getJobDAO() {
                return mockJobDAO;
            }
        };
    }

    private Offer createTestOffer(String title, String company, String description, String location) {
        return new Offer(
            0,
            title,
            "http://example.com/job",
            "TestSite",
            new Date(),
            new Date(),
            location,
            "http://example.com",
            company,
            "Company description",
            description,
            "Region",
            "City",
            "IT",
            "Development",
            "CDI",
            "Bac+5",
            "Computer Science",
            "2-3 years",
            description,
            "Team player",
            "Java, Spring",
            "Communication",
            "Docker, K8s",
            "French",
            "Fluent",
            "Competitive",
            "Health insurance",
            true
        );
    }

    @Test
    void testGetAllJobs_Success() {
        // Prepare test data
        List<Offer> expectedOffers = Arrays.asList(
            createTestOffer("Java Developer", "Tech Co", "Java position", "Casablanca"),
            createTestOffer("Data Scientist", "AI Corp", "ML position", "Rabat")
        );

        // Configure mock behavior
        when(mockJobDAO.getAllJobs()).thenReturn(expectedOffers);

        // Execute the method
        List<Offer> actualOffers = jobRetrievalService.getAllJobs();

        // Verify results
        assertNotNull(actualOffers);
        assertEquals(expectedOffers.size(), actualOffers.size());
        assertEquals(expectedOffers, actualOffers);

        // Verify the mock was called
        verify(mockJobDAO).getAllJobs();
    }

    @Test
    void testGetAllJobs_EmptyResult() {
        // Configure mock to return empty list
        when(mockJobDAO.getAllJobs()).thenReturn(Collections.emptyList());

        // Execute the method
        List<Offer> results = jobRetrievalService.getAllJobs();

        // Verify results
        assertNotNull(results);
        assertTrue(results.isEmpty());

        // Verify the mock was called
        verify(mockJobDAO).getAllJobs();
    }



    @Test
    void testGetAllJobs_DAOThrowsException() {
        // Configure mock to throw exception
        when(mockJobDAO.getAllJobs()).thenThrow(new RuntimeException("Database error"));

        // Test exception handling
        Exception exception = assertThrows(RuntimeException.class,
            () -> jobRetrievalService.getAllJobs());
        assertTrue(exception.getMessage().contains("Error retrieving jobs from database"));

        // Verify the mock was called
        verify(mockJobDAO).getAllJobs();
    }



    @Test
    void testSearchJobs_NoMatches() {
        // Prepare test data
        List<Offer> allOffers = Arrays.asList(
            createTestOffer("Java Developer", "Tech Co", "Java position", "Casablanca"),
            createTestOffer("Data Scientist", "AI Corp", "ML position", "Rabat")
        );

        // Configure mock behavior
        when(mockJobDAO.getAllJobs()).thenReturn(allOffers);

        // Execute the method with non-matching keyword
        List<Offer> results = jobRetrievalService.searchJobs("python");

        // Verify results
        assertNotNull(results);
        assertTrue(results.isEmpty());

        // Verify the mock was called
        verify(mockJobDAO).getAllJobs();
    }

    @Test
    void testSearchJobs_NullKeyword() {
        // Test with null keyword
        Exception exception = assertThrows(IllegalArgumentException.class,
            () -> jobRetrievalService.searchJobs(null));
        assertEquals("Search keyword cannot be null", exception.getMessage());

        // Verify the mock was never called
        verify(mockJobDAO, never()).getAllJobs();
    }






}
