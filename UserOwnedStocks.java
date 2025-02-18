<<<<<<< HEAD
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
=======
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
>>>>>>> c06d3e0488ed528f9b7ad836fba2b179e4b76c76
