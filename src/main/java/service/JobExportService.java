package service;

import data.model.Offer;
import machine_learning.JobRecommendationAdapter;
import machine_learning.jobRecommendation;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.*;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JobExportService {
    private static final String DOWNLOAD_DIR = "src/main/java/downloads";

    public enum ExportFormat {
        PDF, CSV
    }

    public String exportJobs(List<Offer> offers, ExportFormat format, String filename) throws IOException {
        return exportJobsInternal(offers, format, filename);
    }

    public String exportRecommendedJobs(List<jobRecommendation.Job> jobs, ExportFormat format, String filename) throws IOException {
        List<Offer> offers = JobRecommendationAdapter.convertToOffers(jobs);
        return exportJobsInternal(offers, format, filename);
    }

    private String exportJobsInternal(List<Offer> offers, ExportFormat format, String filename) throws IOException {
        // Ensure downloads directory exists
        Files.createDirectories(Paths.get(DOWNLOAD_DIR));
        
        String fullPath = DOWNLOAD_DIR + "/" + filename + "." + format.toString().toLowerCase();
        
        switch (format) {
            case PDF:
                exportToPdf(offers, fullPath);
                break;
            case CSV:
                exportToCsv(offers, fullPath);
                break;
        }
        
        return fullPath;
    }

    private void exportToPdf(List<Offer> offers, String filePath) throws IOException {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            document.add(new Paragraph("Job Recommendations", titleFont));
            document.add(new Paragraph("\n"));
            
            Font contentFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
            
            for (int i = 0; i < offers.size(); i++) {
                Offer offer = offers.get(i);
                document.add(new Paragraph("Job Offer #" + (i + 1) + ":", contentFont));
                document.add(new Paragraph("Title: " + offer.getTitre(), contentFont));
                document.add(new Paragraph("Company: " + offer.getNomEntreprise(), contentFont));
                document.add(new Paragraph("Location: " + offer.getVille() + ", " + offer.getRegion(), contentFont));
                document.add(new Paragraph("Contract Type: " + offer.getTypeContrat(), contentFont));
                document.add(new Paragraph("Experience: " + offer.getExperience(), contentFont));
                document.add(new Paragraph("Description: " + offer.getDescriptionPoste(), contentFont));
                document.add(new Paragraph("\n"));
            }
        } catch (DocumentException e) {
            throw new IOException("Failed to create PDF", e);
        } finally {
            document.close();
        }
    }

    private void exportToCsv(List<Offer> offers, String filePath) throws IOException {
        try (PrintWriter writer = new PrintWriter(new File(filePath))) {
            // Write CSV header with all relevant fields
            writer.println("Title,Company,Location,Region,Contract Type,Experience,Education Level,Specialty,Sector," +
                         "Required Skills,Soft Skills,Languages,Language Level,Salary,Benefits,Remote Work,Description");
            
            // Write offer data
            for (Offer offer : offers) {
                writer.println(String.format("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"," +
                                          "\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"",
                    escapeCsvField(offer.getTitre()),
                    escapeCsvField(offer.getNomEntreprise()),
                    escapeCsvField(offer.getVille()),
                    escapeCsvField(offer.getRegion()),
                    escapeCsvField(offer.getTypeContrat()),
                    escapeCsvField(offer.getExperience()),
                    escapeCsvField(offer.getNiveauEtudes()),
                    escapeCsvField(offer.getSpecialiteDiplome()),
                    escapeCsvField(offer.getSecteurActivite()),
                    escapeCsvField(offer.getCompetencesRequises()),
                    escapeCsvField(offer.getSoftSkills()),
                    escapeCsvField(offer.getLangue()),
                    escapeCsvField(offer.getNiveauLangue()),
                    escapeCsvField(offer.getSalaire()),
                    escapeCsvField(offer.getAvantagesSociaux()),
                    offer.isTeletravail() ? "Yes" : "No",
                    escapeCsvField(offer.getDescriptionPoste())
                ));
            }
        }
    }

    private String escapeCsvField(String field) {
        if (field == null) return "";
        return field.replace("\"", "\"\"");
    }
}
