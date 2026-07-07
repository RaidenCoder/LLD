package strategies;

import models.ParkingTicket;

public class FlatRateStrategy implements FeeStrategy {
    private final double flatRate;

    public FlatRateStrategy(double flatRate) {
        this.flatRate = flatRate;
    }

    @Override
    public double calculateFee(ParkingTicket ticket) {
        return flatRate;
    }
}
