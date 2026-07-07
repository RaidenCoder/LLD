package strategies;

import java.util.Map;

import enums.VehicleSize;
import models.ParkingTicket;

public class VehicleBasedFeeStrategy implements FeeStrategy {
    private final Map<VehicleSize, Double> ratesPerHour;

    public VehicleBasedFeeStrategy(Map<VehicleSize, Double> ratesPerHour) {
        this.ratesPerHour = ratesPerHour;
    }

    public double calculateFee(ParkingTicket ticket) {
        double rate = ratesPerHour.getOrDefault(ticket.getVehicle().getSize(), 0.0);
        return ticket.getDurationInHours() * rate;
    }
}
