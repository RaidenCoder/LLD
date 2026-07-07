package strategies;

import java.util.List;

import enums.VehicleSize;
import models.ParkingFloor;
import models.ParkingSpot;

public class NearestFirstStrategy implements SpotAllocationStrategy {
    @Override
    public ParkingSpot findSpot(List<ParkingFloor> floors, VehicleSize size) {
        for(ParkingFloor floor : floors) {
            ParkingSpot spot = floor.findAvailableSpot(size);
            if(spot != null) return spot;
        }

        return null;
    }
}
