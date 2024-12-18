package service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import data.model.Offer;
import scrapper.EmploiScraper;
import scrapper.WebScraper;

@ExtendWith(MockitoExtension.class)
public class ScraperServiceTest {

    @Mock
    private EmploiScraper mockEmploiScraper;

    private ScraperService scraperService;

    @BeforeEach
    void setUp() {
        // Create a new instance for each test
        scraperService = new ScraperService() {
            // Override constructor to inject our mock
            {
                scrapers.clear(); // Clear default scrapers
                scrapers.add(mockEmploiScraper);
            }
        };
    }

    @Test
    void testGetAllJobData_Success() {
        // Prepare test data
        List<String> mockScrapedData = Arrays.asList(
            "Title: Software Engineer\n" +
            "Company: Tech Corp\n" +
            "Location: Casablanca (Casa-Settat)\n" +
            "Contract: CDI\n" +
            "Experience: 3-5 years\n" +
            "Education: Bac+5\n" +
            "Sector: IT\n" +
            "Function: Development\n" +
            "URL: http://example.com\n" +
            "Remote Work: Yes\n" +
            "Description: Java developer position\n" +
            "Company Description: Leading tech company"
        );

        // Configure mock behavior
        when(mockEmploiScraper.getScrapedData()).thenReturn(mockScrapedData);

        // Execute the method
        List<Offer> results = scraperService.getAllJobData();

        // Verify results
        assertNotNull(results, "Results should not be null");
        assertEquals(1, results.size(), "Should have one job offer");
        
        Offer offer = results.get(0);
        assertEquals("Software Engineer", offer.getTitre());
        assertEquals("Tech Corp", offer.getNomEntreprise());
        assertEquals("Casablanca, Casa-Settat", offer.getAdresseEntreprise());
        assertEquals("CDI", offer.getTypeContrat());
        assertEquals("3-5 years", offer.getExperience());
        assertEquals("Bac+5", offer.getNiveauEtudes());
        assertEquals("IT", offer.getSecteurActivite());
        assertEquals("Development", offer.getMetier());
        assertTrue(offer.isTeletravail());
        
        // Verify the mock was called
        verify(mockEmploiScraper).getScrapedData();
    }

    @Test
    void testGetAllJobData_ScraperThrowsException() {
        // Configure mock to throw exception
        when(mockEmploiScraper.getScrapedData()).thenThrow(new RuntimeException("Scraping failed"));

        // Execute the method - should not throw exception
        List<Offer> results = scraperService.getAllJobData();

        // Verify results
        assertNotNull(results, "Results should not be null even when scraper fails");
        assertTrue(results.isEmpty(), "Results should be empty when scraper fails");
        
        // Verify the mock was called
        verify(mockEmploiScraper).getScrapedData();
    }

    @Test
    void testGetAllJobData_InvalidScrapedData() {
        // Prepare invalid test data
        List<String> invalidData = Arrays.asList(
            "Invalid Format",
            "Title: Only Title\n",
            "" // Empty string
        );

        // Configure mock behavior
        when(mockEmploiScraper.getScrapedData()).thenReturn(invalidData);

        // Execute the method
        List<Offer> results = scraperService.getAllJobData();

        // Verify results
        assertNotNull(results, "Results should not be null");
        assertEquals(0, results.size(), "Invalid offers should be filtered out");
        
        // Verify the mock was called
        verify(mockEmploiScraper).getScrapedData();
    }

    @Test
    void testGetAllJobData_MultipleScrapers() {
        // Create another mock scraper
        WebScraper mockSecondScraper = mock(WebScraper.class);
        
        // Add second scraper to service
        scraperService = new ScraperService() {
            {
                scrapers.clear();
                scrapers.add(mockEmploiScraper);
                scrapers.add(mockSecondScraper);
            }
        };

        // Prepare test data for both scrapers
        List<String> firstScraperData = Arrays.asList(
            "Title: Job 1\n" +
            "Company: Company 1\n" +
            "Location: Rabat (Rabat-Sale)\n" +
            "Contract: CDI\n" +
            "Experience: 2 years\n" +
            "Education: Bac+3\n" +
            "Sector: Finance\n" +
            "Function: Accounting\n" +
            "URL: http://example1.com\n" +
            "Remote Work: No\n" +
            "Description: Position 1\n" +
            "Company Description: Company 1 desc"
        );

        List<String> secondScraperData = Arrays.asList(
            "Title: Job 2\n" +
            "Company: Company 2\n" +
            "Location: Tanger (Tanger-Tetouan)\n" +
            "Contract: CDD\n" +
            "Experience: 1 year\n" +
            "Education: Bac+4\n" +
            "Sector: Marketing\n" +
            "Function: Digital Marketing\n" +
            "URL: http://example2.com\n" +
            "Remote Work: Yes\n" +
            "Description: Position 2\n" +
            "Company Description: Company 2 desc"
        );

        // Configure mocks
        when(mockEmploiScraper.getScrapedData()).thenReturn(firstScraperData);
        when(mockSecondScraper.getScrapedData()).thenReturn(secondScraperData);

        // Execute the method
        List<Offer> results = scraperService.getAllJobData();

        // Verify results
        assertNotNull(results, "Results should not be null");
        assertEquals(2, results.size(), "Should have two job offers");
        
        // Verify first offer
        Offer firstOffer = results.get(0);
        assertEquals("Job 1", firstOffer.getTitre());
        assertEquals("Company 1", firstOffer.getNomEntreprise());
        assertFalse(firstOffer.isTeletravail());
        
        // Verify second offer
        Offer secondOffer = results.get(1);
        assertEquals("Job 2", secondOffer.getTitre());
        assertEquals("Company 2", secondOffer.getNomEntreprise());
        assertTrue(secondOffer.isTeletravail());
        
        // Verify both mocks were called
        verify(mockEmploiScraper).getScrapedData();
        verify(mockSecondScraper).getScrapedData();
    }
}
