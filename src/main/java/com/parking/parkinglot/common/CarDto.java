package com.parking.parkinglot.common;

public class CarDto {
    Long id;
    String licensePlate;
    String parkingSpot;
    String ownerName;

    public CarDto(Long id, String licensePlate, String parkingSpot, String ownerName){
        this.id=id;
        this.licensePlate=licensePlate;
        this.parkingSpot=parkingSpot;
        this.ownerName=ownerName;
    }

    public String getParkingSpot() {
        return parkingSpot;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public Long getId() {
        return id;
    }

    public String getOwnerName() {
        return ownerName;
    }
}
