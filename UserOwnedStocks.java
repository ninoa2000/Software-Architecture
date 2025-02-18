import java.util.ArrayList;
import java.util.List;

public class UserOwnedStocks {
    private List<Stock> ownedStocks = new ArrayList<>();

    public void trackStock(Stock stock) {
        ownedStocks.add(stock);
        System.out.println("Tracking stock: " + stock.getStockName());
    }

    public List<Stock> getOwnedStocks() {
        return ownedStocks;
    }
}
