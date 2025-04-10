public class Stock implements StockInfo {
    private String name;
    private double price;
    private StockCompany company;

    public Stock(String name, double price, StockCompany company) {
        this.name = name;
        this.price = price;
        this.company = company;
    }

    public String getStockDetails() {
        return "Stock: " + name + ", Price: " + price + ", " + company.getStockDetails();
    }

    public double getPrice() {
        return price;
    }

    public StockCompany getCompany() {
        return company;
    }
}
