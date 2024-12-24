# Job Offers Web Scraping and Analysis System Architecture

## Overview
This Java application is designed to scrape, analyze, and present job listings from various Moroccan job portals. It combines web scraping capabilities with data storage, machine learning analysis, and a JavaFX-based GUI presentation layer.

## Architecture Layers

### 1. Data Layer (`data/`)
Handles data persistence and database operations.

#### Components:
- **Models**
  - `Job.java`: Core job entity
  - `Offer.java`: Job offer with metadata

- **Data Access**
  - `JobDAO.java`: Interface for database operations
  - `JobDAOImpl.java`: Implementation of database operations
  - `DatabaseConnectionManager.java`: Connection pooling and management

### 2. Web Scraping Layer (`scrapper/`)
Responsible for extracting job data from various websites.

#### Components:
- **Specialized Scrapers**
  - `RekruteScraper.java`: For Rekrute.com
  - `EmploiScraper.java`: For Emploi.ma
  - `MarocemploiScraper.java`: For Marocemploi.ma
  - `EmploiPublicScraper.java`: For public sector jobs

- **Core Components**
  - `WebScraper.java`: Base scraper interface
  - `Mjob_Skuld_Operation.java`: Specialized scraping operations
  - `Offre.java`: Raw scraped offer representation

### 3. Service Layer (`service/`)
Implements business logic and orchestrates operations.

#### Services:
- `ScraperService.java`: Coordinates scraping operations
- `JobInsertionService.java`: Manages data persistence
- `JobRetrievalService.java`: Handles data retrieval
- `JobExportService.java`: Manages data export operations

### 4. Machine Learning Layer (`machine_learning/`)
Provides predictive analytics and data analysis.

#### Components:
- `EnhancedJobPrediction.java`: Advanced prediction algorithms
- `JobRecommendationAdapter.java`: Data adaptation for ML
- `JobStatistics.java`: Statistical analysis
- `jobRecommendation.java`: Core recommendation engine

### 5. GUI Layer (`gui/`)
JavaFX-based user interface implementation.

#### Components:
- **Main Application**
  - `JobScraperApp.java`: Application entry point

- **Core Components**
  - `HomeComponent.java`: Main dashboard
  - `DatabaseComponent.java`: Database management
  - `JobListComponent.java`: Job listings display
  - `ScrapingComponent.java`: Scraping controls
  - `PredictionComponent.java`: ML predictions interface
  - `RecommendationComponent.java`: Job recommendations
  - `StatisticsComponent.java`: Statistical displays

- **Charts**
  - `CityDistributionChart.java`: Geographic distribution
  - `ContractTypeDistributionChart.java`: Contract analysis
  - `EducationDistributionChart.java`: Education requirements
  - `SectorDistributionChart.java`: Industry sectors

- **Utilities**
  - `AlertUtils.java`: User notifications

### 6. Parser Layer (`parser/`)
Handles data parsing and text processing.

#### Components:
- `JobParser.java`: Structured data extraction
- `JobPosting.java`: Parsed job representation
- `JobTextClustering.java`: Text analysis

## Design Patterns Used

### 1. DAO Pattern
- Separates data access logic
- Provides clean interface for database operations
- Implemented in `JobDAO` and `JobDAOImpl`

### 2. Service Layer Pattern
- Encapsulates business logic
- Manages transactions
- Coordinates between layers

### 3. Component-Based Architecture
- Modular GUI components
- Reusable chart implementations
- Separation of concerns in UI

### 4. Adapter Pattern
- Used in ML layer for data adaptation
- Facilitates integration between components

### 5. Strategy Pattern
- Different scraping strategies
- Flexible parsing approaches

## Testing Architecture

### Test Categories:
1. **Scraper Tests**
   - `RekruteScraperTest.java`
   - `EmploiScraperTest.java`
   - `Mjob_Skuld_OperationTest.java`

2. **Service Tests**
   - `JobExportServiceTest.java`
   - `JobInsertionServiceTest.java`
   - `JobRetrievalServiceTest.java`
   - `ScraperServiceTest.java`

## Data Flow

1. **Scraping Process**
   ```
   ScraperService → Specialized Scrapers → JobParser → JobInsertionService → Database
   ```

2. **Data Retrieval**
   ```
   JobRetrievalService → JobDAO → Database → GUI Components
   ```

3. **Machine Learning Pipeline**
   ```
   Data → JobRecommendationAdapter → EnhancedJobPrediction → GUI Display
   ```

## Best Practices Implemented

1. **Separation of Concerns**
   - Clear layer separation
   - Modular components
   - Distinct responsibilities

2. **Clean Code**
   - Interface-based design
   - Dependency injection
   - Clear naming conventions

3. **Error Handling**
   - Comprehensive testing
   - Alert system for user feedback
   - Exception management

4. **Maintainability**
   - Modular architecture
   - Reusable components
   - Clear documentation
