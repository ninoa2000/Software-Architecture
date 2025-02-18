<<<<<<< HEAD
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

    public UserFinance getUserFinance() {
        return userFinance;
    }

    public UserOwnedStocks getUserOwnedStocks() {
        return userOwnedStocks;
    }
}
=======
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
>>>>>>> c06d3e0488ed528f9b7ad836fba2b179e4b76c76
