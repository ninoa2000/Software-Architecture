# Stock Price Comparison Web Application

A robust and scalable web application for comparing share prices over time, built with Java and Spring Boot.

## Features

- Obtain daily price information for stock symbols between specified dates (up to 2 years)
- Compare performance of multiple stocks side by side
- Persistent storage of price data for offline access
- Interactive charts for visual comparison
- Clean UI with responsive design

## Architecture

The application follows Clean Architecture principles with clear separation of concerns:

1. **Domain Layer**: Core entities like Stock and StockPrice
2. **Repository Layer**: Data access interfaces for persistence
3. **Service Layer**: Business logic and integration with Alpha Vantage
4. **Web Layer**: Controllers and UI templates
5. **External Integration**: Alpha Vantage API for real-time data

## Technologies Used

- Java 17
- Spring Boot 3.2.3
- Spring Data JPA
- H2 Database (file-based for persistence)
- Thymeleaf templating
- Chart.js for visualization
- Bootstrap 5 for responsive UI
- Alpha Vantage API for stock data

## Running the Application

### Prerequisites

- Java 17 or higher
- Maven

### Steps to Run

1. Clone the repository
2. Navigate to the project directory
3. Build the project:
   ```
   mvn clean package
   ```
4. Run the application:
   ```
   java -jar target/stockcompare-0.0.1-SNAPSHOT.jar
   ```
5. Access the application in your browser at:
   ```
   http://localhost:8080
   ```

## Usage

1. Navigate to the "Compare Stocks" page
2. Enter two stock symbols (e.g., AAPL for Apple, MSFT for Microsoft)
3. Select date range (maximum 2 years)
4. Click "Compare Stocks" to view the comparison chart
5. The chart displays normalized performance for easy comparison

## Database Access

The application uses H2 database with file persistence. You can access the database console at:
```
http://localhost:8080/h2-console
```

Connection details:
- JDBC URL: `jdbc:h2:file:./stockcompare_db`
- Username: `sa`
- Password: `password` 