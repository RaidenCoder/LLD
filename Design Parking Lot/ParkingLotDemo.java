import java.util.HashMap;
import java.util.List;
import java.util.Map;

import enums.VehicleSize;
import exceptions.ParkingException;
import models.Bike;
import models.Car;
import models.ParkingFloor;
import models.ParkingTicket;
import models.Truck;
import strategies.BestFitStrategy;
import strategies.HourlyFeeStrategy;
import strategies.NearestFirstStrategy;

public class ParkingLotDemo {
    public static void main(String[] args) {
        //Build 2 floors with a mix of spot sizes
        Map<VehicleSize, Integer> floor1Counts = new HashMap<>();
        floor1Counts.put(VehicleSize.SMALL, 2);
        floor1Counts.put(VehicleSize.MEDIUM, 2);
        floor1Counts.put(VehicleSize.LARGE, 1);

        Map<VehicleSize, Integer> floor2Counts = new HashMap<>();
        floor2Counts.put(VehicleSize.SMALL, 1);
        floor2Counts.put(VehicleSize.MEDIUM, 1);
        floor2Counts.put(VehicleSize.LARGE, 1);

        List<ParkingFloor> floors = List.of(
            new ParkingFloor(1, floor1Counts),
            new ParkingFloor(2, floor2Counts)
        );

        ParkingLot lot = ParkingLot.getInstance();
        lot.initialize(floors, new HourlyFeeStrategy(10.0), new NearestFirstStrategy());

        lot.displayAvailability();

        // === Scenario 1: Park a few vehicles (nearest-first) ===
        System.out.println("========== SCENARIO 1: Park Vehicles (Nearest First) ==========");
        ParkingTicket bikeTicket = lot.parkVehicle(new Bike("KA-01-1111"));
        ParkingTicket carTicket = lot.parkVehicle(new Car("KA-02-2222"));
        lot.parkVehicle(new Truck("KA-03-3333"));

        lot.displayAvailability();

        // === Scenario 2: Switch to best-fit allocation ===
        System.out.println("========== SCENARIO 2: Park With Best Fit ==========");
        lot.setAllocationStrategy(new BestFitStrategy());
        lot.parkVehicle(new Car("KA-04-4444"));

        // === Scenario 3: Unpark and pay ===
        System.out.println("\n========== SCENARIO 3: Unpark Vehicles ==========");
        lot.unparkVehicle(bikeTicket.getTicketId());
        lot.unparkVehicle(carTicket.getTicketId());

        lot.displayAvailability();

        // === Scenario 4: Fill the large spots, then fail to park a truck ===
        System.out.println("========== SCENARIO 4: No Spot Available ==========");
        try {
            lot.setAllocationStrategy(new NearestFirstStrategy());
            lot.parkVehicle(new Truck("KA-05-5555"));  // remaining large spot
            lot.parkVehicle(new Truck("KA-06-6666"));  // should fail
        } catch (ParkingException e) {
            System.out.println("Caught expected error: " + e.getMessage());
        }

        // Clean up the singleton so repeated runs start fresh
        ParkingLot.resetInstance();
    }
}
