package com.stockcompare.controller;

import com.stockcompare.model.Stock;
import com.stockcompare.model.StockPrice;
import com.stockcompare.service.StockService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class WebController {

    private final StockService stockService;
    
    public WebController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/compare")
    public String compareForm() {
        return "compare";
    }

    @PostMapping("/compare")
    public String compareStocks(
            @RequestParam String symbol1,
            @RequestParam String symbol2,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            Model model) {
        
        try {
            // Add the form values back to the model in case of error
            model.addAttribute("symbol1", symbol1);
            model.addAttribute("symbol2", symbol2);
            model.addAttribute("startDate", startDate);
            model.addAttribute("endDate", endDate);
            
            // Get stock data for both symbols
            List<StockPrice> prices1;
            List<StockPrice> prices2;
            
            try {
                prices1 = stockService.getStockPrices(symbol1, startDate, endDate);
            } catch (Exception e) {
                model.addAttribute("error", "Error retrieving data for " + symbol1 + ": " + e.getMessage());
                return "compare";
            }
            
            try {
                prices2 = stockService.getStockPrices(symbol2, startDate, endDate);
            } catch (Exception e) {
                model.addAttribute("error", "Error retrieving data for " + symbol2 + ": " + e.getMessage());
                return "compare";
            }
            
            Stock stock1 = stockService.findStock(symbol1).orElse(null);
            Stock stock2 = stockService.findStock(symbol2).orElse(null);
            
            if (stock1 == null) {
                model.addAttribute("error", "Could not find stock information for " + symbol1);
                return "compare";
            }
            
            if (stock2 == null) {
                model.addAttribute("error", "Could not find stock information for " + symbol2);
                return "compare";
            }
            
            if (prices1.isEmpty()) {
                model.addAttribute("error", "No price data available for " + symbol1 + " in the selected date range");
                return "compare";
            }
            
            if (prices2.isEmpty()) {
                model.addAttribute("error", "No price data available for " + symbol2 + " in the selected date range");
                return "compare";
            }
            
            // Prepare data for chart - convert to JSON-friendly format
            List<LocalDate> dates = prices1.stream().map(StockPrice::getDate).collect(Collectors.toList());
            List<Double> closePrices1 = prices1.stream().map(StockPrice::getClose).collect(Collectors.toList());
            List<Double> closePrices2 = prices2.stream().map(StockPrice::getClose).collect(Collectors.toList());
            
            model.addAttribute("stock1", stock1);
            model.addAttribute("stock2", stock2);
            model.addAttribute("dates", dates);
            model.addAttribute("prices1", closePrices1);
            model.addAttribute("prices2", closePrices2);
            
            return "result";
        } catch (Exception e) {
            model.addAttribute("error", "Error comparing stocks: " + e.getMessage());
            return "compare";
        }
    }
} 