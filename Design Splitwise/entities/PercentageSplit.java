package entities;

public class PercentageSplit extends Split {
    private double percentage;
    public PercentageSplit(String userId, double percentage) {
        super(userId);
        this.percentage = percentage;
    }

    public double getPercentage() { return percentage; }
}
