# Job Offers Web Scraping and Analysis Project

## Project Overview
This Java project is a comprehensive job offers management system that combines web scraping, data storage, GUI presentation, and machine learning capabilities. The application scrapes job listings from various Moroccan job portals, processes the data, stores it in a database, and presents it through a user-friendly JavaFX interface with additional analytical features.

## Package Structure

### 1. Data Package (`data/`)
This package handles all data persistence and database operations.

#### Components:
- **Model Classes**
  - `Job.java`: Represents the core job entity with properties like title, description, location, etc.
  - `Offer.java`: Represents a job offer with additional metadata and processing information.

- **DAO (Data Access Object) Classes**
  - `JobDAO.java`: Interface defining the contract for job-related database operations.
  - `JobDAOImpl.java`: Implementation of JobDAO interface, handling actual database operations.

- **Utility Classes**
  - `DatabaseConnectionManager.java`: Manages database connections and provides connection pooling.

### 2. Machine Learning Package (`machine_learning/`)
Handles predictive analytics and job recommendations.

#### Components:
- `EnhancedJobPrediction.java`: Implements advanced job prediction algorithms.
- `JobRecommendationAdapter.java`: Adapts job data for the recommendation system.
- `JobStatistics.java`: Generates statistical analysis of job data.
- `jobRecommendation.java`: Core recommendation engine.

### 3. GUI Package (`gui/`)
JavaFX-based user interface components.

#### Components:
- **Main Application**
  - `JobScraperApp.java`: Main application entry point and window management.

- **Components**
  - `HomeComponent.java`: Main dashboard view.
  - `DatabaseComponent.java`: Database management interface.
  - `JobListComponent.java`: Displays list of scraped jobs.
  - `PredictionComponent.java`: Shows job predictions.
  - `RecommendationComponent.java`: Displays job recommendations.
  - `ScrapingComponent.java`: Controls for web scraping operations.
  - `StatisticsComponent.java`: Shows statistical analysis.

- **Charts**
  - `CityDistributionChart.java`: Visualizes job distribution by city.
  - `ContractTypeDistributionChart.java`: Shows distribution of contract types.
  - `EducationDistributionChart.java`: Displays education requirement statistics.
  - `SectorDistributionChart.java`: Visualizes job sector distribution.

- **Utils**
  - `AlertUtils.java`: Utility for showing user notifications and alerts.

### 4. Service Package (`service/`)
Business logic and core services.

#### Components:
- `JobExportService.java`: Handles exporting job data to various formats.
- `JobInsertionService.java`: Manages job data insertion into the database.
- `JobRetrievalService.java`: Handles fetching job data from the database.
- `ScraperService.java`: Orchestrates web scraping operations.

### 5. Scrapper Package (`scrapper/`)
Web scraping implementation for different job portals.

#### Components:
- `RekruteScraper.java`: Scrapes from Rekrute.com
- `EmploiScraper.java`: Scrapes from Emploi.ma
- `EmploiPublicScraper.java`: Scrapes from public sector job listings
- `MarocemploiScraper.java`: Scrapes from Marocemploi.ma
- `Mjob_Skuld_Operation.java`: Specialized scraper for specific job operations
- `WebScraper.java`: Base scraper interface/abstract class

### 6. Parser Package (`parser/`)
Handles parsing and processing of scraped job data.

#### Components:
- `JobParser.java`: Parses raw job data into structured format
- `JobPosting.java`: Represents a parsed job posting
- `JobTextClustering.java`: Implements text clustering for job descriptions

## Test Package Structure
The test package mirrors the main package structure and includes comprehensive tests for each component.

### Test Categories:

#### 1. Scraper Tests
- `RekruteScraperTest.java`: Validates Rekrute scraping functionality
- `EmploiScraperTest.java`: Tests Emploi.ma scraping
- `Mjob_Skuld_OperationTest.java`: Tests specialized scraping operations

#### 2. Service Tests
- `JobExportServiceTest.java`: Validates job data export functionality
- `JobInsertionServiceTest.java`: Tests database insertion operations
- `JobRetrievalServiceTest.java`: Validates data retrieval operations
- `ScraperServiceTest.java`: Tests scraping service orchestration

## Component Interactions

### Data Flow
1. **Scraping Flow**:
   - ScraperService coordinates multiple scrapers
   - Each scraper implements specific site scraping
   - Parsed data is passed to JobParser
   - Structured data is sent to JobInsertionService

2. **Data Storage Flow**:
   - JobInsertionService uses JobDAO
   - DatabaseConnectionManager provides connections
   - JobDAOImpl handles SQL operations

3. **GUI Flow**:
   - JobScraperApp initializes main components
   - Each component handles specific functionality
   - Components use services for data operations
   - Charts display statistical visualizations

4. **Machine Learning Flow**:
   - JobRecommendationAdapter prepares data
   - EnhancedJobPrediction processes predictions
   - Results displayed via PredictionComponent

## Test Coverage

### Purpose of Each Test Category:

1. **Scraper Tests**
   - Verify correct HTML parsing
   - Validate data extraction accuracy
   - Test error handling for network issues
   - Ensure proper handling of different page formats

2. **Service Tests**
   - Validate business logic implementation
   - Test data transformation accuracy
   - Verify database operations
   - Ensure proper error handling
   - Test data export functionality

3. **Integration Tests**
   - Verify component interactions
   - Test end-to-end workflows
   - Validate system behavior under various conditions

## Best Practices and Design Patterns Used

1. **DAO Pattern**
   - Separates data access logic
   - Provides clean interface for database operations

2. **Service Layer Pattern**
   - Encapsulates business logic
   - Provides transaction management

3. **Component-Based Architecture**
   - Modular GUI components
   - Reusable chart components

4. **Adapter Pattern**
   - Used in JobRecommendationAdapter
   - Converts data for machine learning operations

5. **Strategy Pattern**
   - Different scraping strategies for different websites
   - Flexible parsing strategies

## Conclusion
This project demonstrates a well-structured Java application that combines multiple technologies and design patterns to create a comprehensive job offers management system. The modular architecture allows for easy maintenance and extension of functionality, while the comprehensive test suite ensures reliability and stability.
