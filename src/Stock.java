<<<<<<< HEAD
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
=======
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
>>>>>>> c06d3e0488ed528f9b7ad836fba2b179e4b76c76
