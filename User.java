public class User {
    private UserFinance userFinance;
    private UserOwnedStocks userOwnedStocks;

    public void manageFinance(UserFinance userFinance) {
        this.userFinance = userFinance;
        System.out.println("User is managing finances.");
    }

    public void ownStock(UserOwnedStocks userOwnedStocks) {
        this.userOwnedStocks = userOwnedStocks;
        System.out.println("User owns stocks.");
    }
}
