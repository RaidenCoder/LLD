package strategies;

import java.util.List;

import enums.VehicleSize;
import models.ParkingFloor;
import models.ParkingSpot;

public interface SpotAllocationStrategy {
    ParkingSpot findSpot(List<ParkingFloor> floors, VehicleSize size);
}
