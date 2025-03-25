import java.util.ArrayList;
import java.util.List;

public class Portfolio {
    private User user;
    private List<Share> shares;

    public Portfolio(User user) {
        this.user = user;
        this.shares = new ArrayList<>();
    }

    public void addShare(Share share) {
        shares.add(share);
    }

    public double getTotalValue() {
        double totalValue = 0;
        for (Share share : shares) {
            totalValue += share.calculateTotalValue();
        }
        return totalValue;
    }

    public User getUser() {
        return user;
    }

    public List<Share> getShares() {
        return shares;
    }
}
