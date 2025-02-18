public class Stock extends FinancialEntity {
    private String stockName;

    public Stock(Data data) {
        super(data);
        this.stockName = "Bitcoin"; // Default stock
    }

    public String getStockName() {
        return stockName;
    }

    @Override
    public void storeData() {
        data.storeData("Stock data for " + stockName + " stored.");
    }
}
