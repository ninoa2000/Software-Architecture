public class Main {
    public static void main(String[] args) {
        // Sample data setup
        StockCompany company = new StockCompany("Tesla", "TSLA");
        Share share = new Share(company, 100, 500.0);
        User user = new User("John Doe", "john.doe@example.com");
        Portfolio portfolio = new Portfolio(user);
        portfolio.addShare(share);

        // Display portfolio total value
        System.out.println("Total Portfolio Value: " + portfolio.getTotalValue());
    }
}
