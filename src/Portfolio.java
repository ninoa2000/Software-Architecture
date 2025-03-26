import java.util.ArrayList;
import java.util.List;

public class Portfolio {
    private User user;
    private List<Transaction> transactions;

    public Portfolio(User user) {
        this.user = user;
        this.transactions = new ArrayList<>();  // Initialize the transactions list
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);  // Adds transaction to the list
    }

    public void displayPortfolio() {
        System.out.println("Portfolio of " + user.getUserInfo());
        for (Transaction transaction : transactions) {
            System.out.println(transaction.getTransactionDetails());
        }
    }
}
