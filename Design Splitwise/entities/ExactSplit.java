package entities;

public class ExactSplit extends Split {
    public ExactSplit(String userId, double amount) {
        super(userId);
        setAmount(amount);
    }
}
