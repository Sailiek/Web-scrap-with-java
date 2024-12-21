package service;

import data.model.Offer;
import machine_learning.jobRecommendation;
import org.junit.jupiter.api.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class JobExportServiceTest {
    private JobExportService exportService;
    private List<Offer> testOffers;
    private static final String TEST_DOWNLOAD_DIR = "src/main/java/downloads";

    @BeforeEach
    void setUp() {
        exportService = new JobExportService();
        testOffers = createTestOffers();
        // Ensure test directory exists
        new File(TEST_DOWNLOAD_DIR).mkdirs();
    }

    @AfterEach
    void cleanup() throws IOException {
        // Clean up test files
        Files.walk(Paths.get(TEST_DOWNLOAD_DIR))
             .filter(Files::isRegularFile)
             .map(Path::toFile)
             .forEach(File::delete);
    }

    private List<Offer> createTestOffers() {
        List<Offer> offers = new ArrayList<>();
        Offer offer = new Offer();
        offer.setTitre("Software Engineer");
        offer.setNomEntreprise("Test Company");
        offer.setVille("Casablanca");
        offer.setRegion("Grand Casablanca");
        offer.setTypeContrat("CDI");
        offer.setExperience("2-3 years");
        offer.setDescriptionPoste("Test description");
        offers.add(offer);
        return offers;
    }

    @Test
    void testExportToPdf() throws IOException {
        String filename = "test_export";
        String resultPath = exportService.exportJobs(testOffers, JobExportService.ExportFormat.PDF, filename);
        
        File exportedFile = new File(resultPath);
        assertTrue(exportedFile.exists(), "PDF file should be created");
        assertTrue(exportedFile.length() > 0, "PDF file should not be empty");
    }

    @Test
    void testExportToCsv() throws IOException {
        String filename = "test_export";
        String resultPath = exportService.exportJobs(testOffers, JobExportService.ExportFormat.CSV, filename);
        
        File exportedFile = new File(resultPath);
        assertTrue(exportedFile.exists(), "CSV file should be created");
        assertTrue(exportedFile.length() > 0, "CSV file should not be empty");
        
        // Verify CSV content
        List<String> lines = Files.readAllLines(exportedFile.toPath());
        assertTrue(lines.size() >= 2, "CSV should have header and at least one data row");
        assertTrue(lines.get(0).contains("Title"), "CSV should contain header with Title");
        assertTrue(lines.get(1).contains("Software Engineer"), "CSV should contain test data");
    }



    @Test
    void testExportWithEmptyList() throws IOException {
        List<Offer> emptyList = new ArrayList<>();
        String filename = "empty_export";
        
        String resultPath = exportService.exportJobs(emptyList, JobExportService.ExportFormat.CSV, filename);
        File exportedFile = new File(resultPath);
        
        assertTrue(exportedFile.exists(), "File should be created even with empty list");
        List<String> lines = Files.readAllLines(exportedFile.toPath());
        assertEquals(1, lines.size(), "CSV should only contain header row");
    }

    @Test
    void testExportWithSpecialCharacters() throws IOException {
        Offer offer = new Offer();
        offer.setTitre("Title with \"quotes\" and ,comma");
        offer.setDescriptionPoste("Description with \n newline");
        List<Offer> specialOffers = List.of(offer);

        String filename = "special_chars";
        String resultPath = exportService.exportJobs(specialOffers, JobExportService.ExportFormat.CSV, filename);
        
        File exportedFile = new File(resultPath);
        assertTrue(exportedFile.exists(), "File should be created with special characters");
        List<String> lines = Files.readAllLines(exportedFile.toPath());
        assertTrue(lines.size() >= 2, "CSV should have header and data row");
    }

    @Test
    void testInvalidFilenameHandling() {
        List<Offer> offers = createTestOffers();
        String invalidFilename = "invalid/file:name";
        
        assertThrows(IOException.class, () -> {
            exportService.exportJobs(offers, JobExportService.ExportFormat.PDF, invalidFilename);
        });
    }
}
