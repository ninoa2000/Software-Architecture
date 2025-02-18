public class Main {
    public static void main(String[] args) {
        // Initialize components
        Data data = new Data();
        Stock stock = new Stock(data);
        User user = new User();
        UserFinance userFinance = new UserFinance(data);
        UserOwnedStocks userOwnedStocks = new UserOwnedStocks();

        // Simulate actions
        user.manageFinance(userFinance);
        user.ownStock(userOwnedStocks);
        userOwnedStocks.trackStock(stock);

        // Using abstraction to store data
        stock.storeData(); 
        userFinance.storeData();

        System.out.println("Bitcoin API System Initialized Successfully!");
    }
}
