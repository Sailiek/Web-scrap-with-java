package scrapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class RekruteScraperTest {
    
    private RekruteScraper scraper;

    @BeforeEach
    void setUp() {
        scraper = new RekruteScraper();
    }

    @Test
    void testGetScrapedData() {
        // Test basic scraping functionality
        List<String> scrapedData = scraper.getScrapedData();
        
        // Verify that we got some data
        assertNotNull(scrapedData, "Scraped data should not be null");
        assertFalse(scrapedData.isEmpty(), "Scraped data should not be empty");

        // Test the first job offer's structure
        String firstOffer = scrapedData.get(0);
        assertNotNull(firstOffer, "First offer should not be null");
        
        // Verify that the offer contains all required fields
        assertTrue(firstOffer.contains("Title:"), "Offer should contain a title");
        assertTrue(firstOffer.contains("Company:"), "Offer should contain a company name");
        assertTrue(firstOffer.contains("Location:"), "Offer should contain a location");
        assertTrue(firstOffer.contains("Contract:"), "Offer should contain a contract type");
        assertTrue(firstOffer.contains("Experience:"), "Offer should contain experience requirements");
        assertTrue(firstOffer.contains("Education:"), "Offer should contain education requirements");
        assertTrue(firstOffer.contains("Sector:"), "Offer should contain a sector");
        assertTrue(firstOffer.contains("Function:"), "Offer should contain a function");
        assertTrue(firstOffer.contains("URL:"), "Offer should contain a URL");
        assertTrue(firstOffer.contains("Remote Work:"), "Offer should contain remote work status");
        assertTrue(firstOffer.contains("Description:"), "Offer should contain a job description");
        assertTrue(firstOffer.contains("Company Description:"), "Offer should contain a company description");

        // Print the scraped data for manual verification
        System.out.println("Number of offers scraped: " + scrapedData.size());
        System.out.println("\nFirst offer details:");
        System.out.println(firstOffer);
    }

    @Test
    void testScrapMethodOutput() {
        // Redirect System.out to capture output
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));

        // Call the scrap method
        scraper.scrap();

        // Get the output
        String output = outContent.toString();

        // Reset System.out
        System.setOut(System.out);

        // Verify the output contains expected messages
        assertTrue(output.contains("Starting Rekrute scraping..."), 
            "Output should contain start message");
        assertTrue(output.contains("Scraped"), 
            "Output should contain number of scraped offers");
        assertTrue(output.contains("job offers from Rekrute"), 
            "Output should contain completion message");
    }
}
