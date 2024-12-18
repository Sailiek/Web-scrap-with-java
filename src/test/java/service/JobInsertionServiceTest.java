package service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import data.dao.JobDAO;
import data.dao.JobDAOImpl;
import data.model.Offer;

@ExtendWith(MockitoExtension.class)
public class JobInsertionServiceTest {

    @Mock
    private JobDAO mockJobDAO;

    private JobInsertionService jobInsertionService;

    @BeforeEach
    void setUp() {
        // Create service with mocked DAO
        jobInsertionService = new JobInsertionService() {
            @Override
            public JobDAO getJobDAO() {
                return mockJobDAO;
            }
        };
    }

    private Offer createValidOffer() {
        return new Offer(
            0,
            "Software Developer",
            "http://example.com/job",
            "TestSite",
            new Date(),
            new Date(),
            "Casablanca",
            "http://example.com",
            "Tech Company",
            "Leading tech company",
            "Developer position",
            "Casa-Settat",
            "Casablanca",
            "IT",
            "Development",
            "CDI",
            "Bac+5",
            "Computer Science",
            "2-3 years",
            "Strong Java skills",
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
    void testInsertOffer_Success() {
        // Prepare test data
        Offer offer = createValidOffer();

        // Configure mock behavior
        doNothing().when(mockJobDAO).saveJob(any(Offer.class));

        // Execute the method
        assertDoesNotThrow(() -> jobInsertionService.insertOffer(offer));

        // Verify the mock was called with correct parameters
        verify(mockJobDAO).saveJob(offer);
    }

    @Test
    void testInsertOffer_NullOffer() {
        // Test with null offer
        Exception exception = assertThrows(IllegalArgumentException.class,
            () -> jobInsertionService.insertOffer(null));
        assertEquals("Offer cannot be null", exception.getMessage());

        // Verify the mock was never called
        verify(mockJobDAO, never()).saveJob(any());
    }

    @Test
    void testInsertOffer_EmptyTitle() {
        // Prepare offer with empty title
        Offer offer = createValidOffer();
        offer.setTitre("");

        // Test with empty title
        Exception exception = assertThrows(IllegalArgumentException.class,
            () -> jobInsertionService.insertOffer(offer));
        assertEquals("Offer title cannot be empty", exception.getMessage());

        // Verify the mock was never called
        verify(mockJobDAO, never()).saveJob(any());
    }

    @Test
    void testInsertOffer_DAOException() {
        // Prepare test data
        Offer offer = createValidOffer();

        // Configure mock to throw exception
        doThrow(new RuntimeException("Database error"))
            .when(mockJobDAO).saveJob(any(Offer.class));

        // Test exception handling
        Exception exception = assertThrows(RuntimeException.class,
            () -> jobInsertionService.insertOffer(offer));
        assertTrue(exception.getMessage().contains("Error while inserting offer"));

        // Verify the mock was called
        verify(mockJobDAO).saveJob(offer);
    }

    @Test
    void testInsertOffers_Success() {
        // Prepare test data
        List<Offer> offers = Arrays.asList(
            createValidOffer(),
            createValidOffer()
        );

        // Configure mock behavior
        doNothing().when(mockJobDAO).saveJob(any(Offer.class));

        // Execute the method
        assertDoesNotThrow(() -> jobInsertionService.insertOffers(offers));

        // Verify the mock was called for each offer
        verify(mockJobDAO, times(2)).saveJob(any(Offer.class));
    }

    @Test
    void testInsertOffers_NullList() {
        // Test with null list
        Exception exception = assertThrows(IllegalArgumentException.class,
            () -> jobInsertionService.insertOffers(null));
        assertEquals("Offers list cannot be null or empty", exception.getMessage());

        // Verify the mock was never called
        verify(mockJobDAO, never()).saveJob(any());
    }

    @Test
    void testInsertOffers_EmptyList() {
        // Test with empty list
        Exception exception = assertThrows(IllegalArgumentException.class,
            () -> jobInsertionService.insertOffers(List.of()));
        assertEquals("Offers list cannot be null or empty", exception.getMessage());

        // Verify the mock was never called
        verify(mockJobDAO, never()).saveJob(any());
    }

    @Test
    void testInsertJobs_Success() {
        // Prepare test data
        List<String> jobData = Arrays.asList(
            "Title: Software Engineer\n" +
            "Company: Tech Corp\n" +
            "Education: Bac+5\n" +
            "Experience: 3-5 years\n" +
            "Location: Casablanca\n" +
            "Contract: CDI\n" +
            "URL: http://example.com\n" +
            "Description: Java developer position",
            
            "Title: Data Scientist\n" +
            "Company: AI Corp\n" +
            "Education: Bac+5\n" +
            "Experience: 2-3 years\n" +
            "Location: Rabat\n" +
            "Contract: CDI\n" +
            "URL: http://example.com/ds\n" +
            "Description: ML expert needed"
        );

        // Configure mock behavior
        doNothing().when(mockJobDAO).saveJob(any(Offer.class));

        // Execute the method
        assertDoesNotThrow(() -> jobInsertionService.insertJobs(jobData));

        // Verify the mock was called twice (once for each job)
        verify(mockJobDAO, times(2)).saveJob(any(Offer.class));
    }

    @Test
    void testInsertJobs_InvalidFormat() {
        // Prepare invalid test data
        List<String> invalidJobData = Arrays.asList(
            "Invalid Format",
            "Title only\nCompany only"
        );

        // Test with invalid format
        Exception exception = assertThrows(RuntimeException.class,
            () -> jobInsertionService.insertJobs(invalidJobData));
        assertTrue(exception.getMessage().contains("Error while inserting job"));

        // Verify the mock was never called
        verify(mockJobDAO, never()).saveJob(any());
    }

    @Test
    void testInsertJobs_NullList() {
        // Test with null list
        Exception exception = assertThrows(IllegalArgumentException.class,
            () -> jobInsertionService.insertJobs(null));
        assertEquals("Job data list cannot be null or empty", exception.getMessage());

        // Verify the mock was never called
        verify(mockJobDAO, never()).saveJob(any());
    }

    @Test
    void testInsertJobs_EmptyList() {
        // Test with empty list
        Exception exception = assertThrows(IllegalArgumentException.class,
            () -> jobInsertionService.insertJobs(List.of()));
        assertEquals("Job data list cannot be null or empty", exception.getMessage());

        // Verify the mock was never called
        verify(mockJobDAO, never()).saveJob(any());
    }
}
