
public class Main {
    public static void main(String[] args) {
        // Create Stock Company
        StockCompany company = new StockCompany("Tesla", "TSLA");

        // Create Stocks
        Stock stock1 = new Stock("Tesla Stock", 700.0, company);
        Stock stock2 = new Stock("Tesla Stock", 710.0, company);

        // Create a User
        User user = new User("John Doe", "john.doe@example.com");

        // Create Transactions
        Transaction transaction1 = new Transaction(stock1, 10, "2025-03-26");
        Transaction transaction2 = new Transaction(stock2, 5, "2025-03-27");

        // Create Portfolio
        Portfolio portfolio = new Portfolio(user);
        portfolio.addTransaction(transaction1);
        portfolio.addTransaction(transaction2);

        // Display Portfolio
        portfolio.displayPortfolio();
    }
}
