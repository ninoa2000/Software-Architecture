package com.stockcompare.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stockcompare.model.Stock;
import com.stockcompare.model.StockPrice;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class AlphaVantageService {

    private static final Logger logger = Logger.getLogger(AlphaVantageService.class.getName());

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${alphavantage.api.key}")
    private String apiKey;

    @Value("${alphavantage.api.base-url}")
    private String baseUrl;
    
    public AlphaVantageService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public Stock getStock(String symbol) {
        try {
            // First, get stock company information
            String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                    .queryParam("function", "OVERVIEW")
                    .queryParam("symbol", symbol)
                    .queryParam("apikey", apiKey)
                    .toUriString();

            String response = restTemplate.getForObject(url, String.class);
            JsonNode rootNode = objectMapper.readTree(response);

            if (rootNode.has("Symbol") && !rootNode.get("Symbol").asText().isEmpty()) {
                String name = rootNode.has("Name") ? rootNode.get("Name").asText() : symbol;
                String exchange = rootNode.has("Exchange") ? rootNode.get("Exchange").asText() : "Unknown";
                
                return new Stock(symbol, name, exchange);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error fetching stock information for symbol: " + symbol, e);
        }
        
        // If API call fails or no data found, create a basic stock with just the symbol
        return new Stock(symbol, symbol, "Unknown");
    }

    public List<StockPrice> getHistoricalPrices(Stock stock, LocalDate startDate, LocalDate endDate) {
        List<StockPrice> prices = new ArrayList<>();
        
        try {
            // Call Alpha Vantage API for time series data
            String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                    .queryParam("function", "TIME_SERIES_DAILY")
                    .queryParam("symbol", stock.getSymbol())
                    .queryParam("outputsize", "full")
                    .queryParam("apikey", apiKey)
                    .toUriString();

            logger.info("Calling Alpha Vantage API with URL: " + url);
            String response = restTemplate.getForObject(url, String.class);
            
            if (response == null || response.isEmpty()) {
                logger.severe("Empty response from Alpha Vantage API for symbol: " + stock.getSymbol());
                return prices;
            }
            
            logger.info("Response received from Alpha Vantage API for symbol: " + stock.getSymbol());
            JsonNode rootNode = objectMapper.readTree(response);
            
            // Check for API error messages
            if (rootNode.has("Error Message")) {
                logger.severe("Alpha Vantage API error: " + rootNode.get("Error Message").asText());
                return prices;
            }
            
            // Check for information messages (like exceeded API call limits)
            if (rootNode.has("Note")) {
                logger.warning("Alpha Vantage API note: " + rootNode.get("Note").asText());
                // Continue processing as we might still have data
            }
            
            if (rootNode.has("Time Series (Daily)")) {
                JsonNode timeSeriesNode = rootNode.get("Time Series (Daily)");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                
                // Iterate through all dates in the response
                Iterator<String> dateFields = timeSeriesNode.fieldNames();
                while (dateFields.hasNext()) {
                    String dateStr = dateFields.next();
                    LocalDate date = LocalDate.parse(dateStr, formatter);
                    
                    // Only include dates in the requested range
                    if ((date.isEqual(startDate) || date.isAfter(startDate)) && 
                        (date.isEqual(endDate) || date.isBefore(endDate))) {
                        
                        JsonNode priceNode = timeSeriesNode.get(dateStr);
                        double open = priceNode.get("1. open").asDouble();
                        double high = priceNode.get("2. high").asDouble();
                        double low = priceNode.get("3. low").asDouble();
                        double close = priceNode.get("4. close").asDouble();
                        long volume = priceNode.get("5. volume").asLong();
                        
                        StockPrice price = new StockPrice(
                            stock, 
                            date, 
                            open, 
                            high, 
                            low, 
                            close, 
                            volume
                        );
                        
                        prices.add(price);
                    }
                }
                
                logger.info("Retrieved " + prices.size() + " price records for " + stock.getSymbol() + 
                           " between " + startDate + " and " + endDate);
            } else {
                logger.warning("No time series data found for symbol: " + stock.getSymbol());
            }
            
            // Sort by date (ascending)
            Collections.sort(prices, (p1, p2) -> p1.getDate().compareTo(p2.getDate()));
            
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error parsing JSON response for symbol: " + stock.getSymbol(), e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error fetching historical prices for symbol: " + stock.getSymbol(), e);
        }
        
        return prices;
    }
} 