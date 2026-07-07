import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import enums.VehicleSize;
import exceptions.ParkingException;
import models.ParkingFloor;
import models.ParkingSpot;
import models.ParkingTicket;
import models.Vehicle;
import strategies.FeeStrategy;
import strategies.SpotAllocationStrategy;

class ParkingLot {
    private static volatile ParkingLot INSTANCE;
    private static final Object lock = new Object();

    private List<ParkingFloor> floors;
    private final Map<String, ParkingTicket> activeTickets;
    private FeeStrategy feeStrategy;
    private SpotAllocationStrategy allocationStrategy;

    private ParkingLot() {
        activeTickets = new ConcurrentHashMap<>();
    }

    public static ParkingLot getInstance() {
        if (INSTANCE == null) {
            synchronized (lock) {
                if (INSTANCE == null) {
                    INSTANCE = new ParkingLot();
                }
            }
        }

        return INSTANCE;
    }

    public void initialize(List<ParkingFloor> floors, FeeStrategy feeStrategy, SpotAllocationStrategy allocationStrategy) {
        this.floors = floors;
        this.allocationStrategy = allocationStrategy;
        this.feeStrategy = feeStrategy; 
    }

    public ParkingTicket parkVehicle(Vehicle vehicle) {
        ParkingSpot spot;
        //Retry if another thread claims the spot before we do
        while(true) {
            //Find a spot using the allocatoin strategy
            spot = allocationStrategy.findSpot(floors, vehicle.getSize());

            if(spot == null) {
                throw new ParkingException("No available spot for vehicle size: " + 
                    vehicle.getSize());
            }

            //Try to claim it; another thread may have taken it first 
            try {
                spot.parkVehicle(vehicle);
                break;
            } catch(ParkingException e) {
                //Spot was taken between findSpot and parkVehicle; try again
            }
        }
        
        //Create and store ticket
        String ticketId = UUID.randomUUID().toString().substring(0, 8);
        ParkingTicket ticket = new ParkingTicket(ticketId, vehicle, spot);
        activeTickets.put(ticketId, ticket);

        System.out.println("Parked " + vehicle + " at spot " + spot.getSpotId() +
            ". Ticket: " + ticketId);

        return ticket;
    }

    public double unparkVehicle(String ticketId) {
        //remove() is atomic on ConcurrentHashMap; only onw caller wins the exit 
        ParkingTicket ticket = activeTickets.remove(ticketId);

        if(ticket == null) {
            throw new ParkingException("Invalid ticket: " + ticketId);
        }

        //Set exitTime and calculate fee
        ticket.setExitTime(java.time.LocalDateTime.now());
        double fee = feeStrategy.calculateFee(ticket);

        //free the spot
        ticket.getSpot().unParkVehicle();

        System.out.printf("Unparked %s from spot %s. Fee: $%.2f%n",
            ticket.getVehicle(), ticket.getSpot().getSpotId(), fee);

        return fee;
    }

    public void displayAvailability() {
        System.out.println("\n==== PARKING AVAILABILITY =====");
        for(ParkingFloor floor: floors) {
            StringBuilder sb = new StringBuilder("Floor ");
            sb.append(floor.getFloorNumber()).append(": ");

            for(VehicleSize size: VehicleSize.values()) {
                int count = 0;
                for(ParkingSpot spot: floor.getSpots()) {
                    if(spot.isAvailable() && spot.getSize() == size) {
                        count++;
                    }
                }
                sb.append(size).append("=").append(count);
                if(size.ordinal() < VehicleSize.values().length - 1) {
                    sb.append(", ");
                }
            }
            System.out.println(sb);
        }
        System.out.println("=====================================\n");
    }

    public void setFeeStrategy(FeeStrategy feeStrategy) {
        this.feeStrategy = feeStrategy;
    }

    public void setAllocationStrategy(SpotAllocationStrategy allocationStrategy) {
        this.allocationStrategy = allocationStrategy;
    }

    public static void resetInstance() {
        synchronized (lock) {
            INSTANCE = null;
        }
    }
}