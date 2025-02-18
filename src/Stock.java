public class Stock {
    private String stockName;
    private Data data;

    public Stock(Data data) {
        this.stockName = "Bitcoin"; // Default stock
        this.data = data;
    }

    public String getStockName() {
        return stockName;
    }

    public void storeStockData() {
        data.storeData("Stock data for Bitcoin stored.");
    }
}
