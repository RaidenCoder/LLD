package models;

import enums.VehicleSize;
import exceptions.ParkingException;

public class ParkingSpot {
    private final String spotId;
    private final VehicleSize size;
    private Vehicle parkedVehicle;


    public ParkingSpot(String spotId, VehicleSize size) {
        this.spotId = spotId;
        this.size = size;
        this.parkedVehicle = null;
    }

    public boolean isAvailable() {
        return parkedVehicle == null;
    }

    public boolean canFitVehicle(VehicleSize vehicleSize) {
        return this.size.ordinal() >= vehicleSize.ordinal();
    }

    public synchronized void parkVehicle(Vehicle vehicle) {
        if(!isAvailable()) {
            throw new ParkingException("spotId: " + spotId + " is already occupied");
        }
        if(!canFitVehicle(vehicle.getSize())) {
            throw new ParkingException("Vehicle size: " + vehicle.getSize() + 
                " cannot fit in spot size " + size);
        }

        this.parkedVehicle = vehicle;
    }


    public synchronized Vehicle unParkVehicle() {
        if(isAvailable()) {
            throw new ParkingException("Spot " + spotId + " is already available");
        }

        Vehicle vehicle = this.parkedVehicle;
        this.parkedVehicle = null;
        return vehicle;
    }

    //Getters
    public String getSpotId() { return spotId; }
    public VehicleSize getSize() { return size; }
    public Vehicle getParkedVehicle() { return parkedVehicle; }
}
