package com.oyerickshaw.tracker.dto.request;

import com.oyerickshaw.tracker.dto.Coordinates;

public class UpdateLocationRequest {
    private String driverId;
    private Coordinates coordinates;

    public UpdateLocationRequest() {
    }

    public UpdateLocationRequest(String driverId, Coordinates coordinates) {

        this.driverId = driverId;
        this.coordinates = coordinates;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
}
