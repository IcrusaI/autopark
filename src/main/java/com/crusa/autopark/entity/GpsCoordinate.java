package com.crusa.autopark.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
public class GpsCoordinate {
    private final double latitude;
    private final double longitude;
    @Setter
    private double speed;

    public GpsCoordinate(double latitude, double longitude, double speed) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.speed = speed;
    }
}

