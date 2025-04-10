package com.stockcompare.repository;

import com.stockcompare.model.Stock;
import com.stockcompare.model.StockPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StockPriceRepository extends JpaRepository<StockPrice, Long> {
    List<StockPrice> findByStockAndDateBetweenOrderByDate(Stock stock, LocalDate startDate, LocalDate endDate);
    boolean existsByStockAndDate(Stock stock, LocalDate date);
} 