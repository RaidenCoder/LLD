package entities;

public class Split {
    private final String userId;
    private double amount;
    
    public Split(String userId) {
        this.userId = userId;
    }

    //Getters
    public String getUserId() { return userId; }
    public double getAmount() { return amount; }

    //Setter
    public void setAmount(double newAmout) { this.amount = newAmout; }
}
