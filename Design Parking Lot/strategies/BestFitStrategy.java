package strategies;

import java.util.List;

import enums.VehicleSize;
import models.ParkingFloor;
import models.ParkingSpot;

public class BestFitStrategy implements SpotAllocationStrategy {
    @Override
    public ParkingSpot findSpot(List<ParkingFloor> floors, VehicleSize size) {
        // Walk sizes from the requested size upward, so an exact match wins
        // before any larger spot is considered
        for(VehicleSize spotSize: VehicleSize.values()) {
            if(spotSize.ordinal() < size.ordinal()) {
                continue;   // too small
            }
            

            for(ParkingFloor floor : floors) {
                for(ParkingSpot spot : floor.getSpots()) {
                    if(spot.isAvailable() && spot.canFitVehicle(size)) {
                        return spot;
                    }
                }
            } 
        }

        return null;
    }
}
