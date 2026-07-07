package strategies;

import models.ParkingTicket;

public interface FeeStrategy {
    double calculateFee(ParkingTicket ticket);
}
