package com.crusa.autopark.service;

import com.crusa.autopark.entity.GpsCoordinate;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Service
public class GpsService {
    private GpsCoordinate lastCoordinate;

    public double calculateDistance(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        double totalDistance = 0.0;
        String line;

        while ((line = reader.readLine()) != null) {
            try {
                GpsCoordinate currentCoordinate = parseCoordinate(line);

                if (currentCoordinate != null) {
                    if (lastCoordinate != null && lastCoordinate.getSpeed() != 0) {
                        totalDistance += calculateDistanceBetween(lastCoordinate, currentCoordinate);
                        lastCoordinate.setSpeed(currentCoordinate.getSpeed());
                    }

                    lastCoordinate = currentCoordinate;
                }
            } catch (Exception e) {
                continue;
            }
        }

        return totalDistance;
    }

    private GpsCoordinate parseCoordinate(String line) {
        String[] nmeaData = line.split(",");

        switch (nmeaData[0].trim()) {
            case "$GPGGA":
                if (nmeaData.length < 6 || nmeaData[2].isEmpty() || nmeaData[3].isEmpty() || nmeaData[4].isEmpty() || nmeaData[5].isEmpty()) {
                    return null;
                }

                double lat = parseLatitude(nmeaData[2], nmeaData[3]);
                double lon = parseLongitude(nmeaData[4], nmeaData[5]);
                double speed = lastCoordinate != null ? lastCoordinate.getSpeed() : 0.0;

                return new GpsCoordinate(lat, lon, speed);
            case "$GNVTG":
                if (nmeaData.length < 8 || nmeaData[7].isEmpty() || nmeaData[8].isEmpty()) {
                    return null;
                }

                double newSpeed = parseSpeed(nmeaData[7], nmeaData[8]);

                if (lastCoordinate != null) {
                    lastCoordinate.setSpeed(newSpeed);
                }

                return null;
            default:
                return null;
        }
    }

    private double parseLatitude(String latValue, String latDirection) {
        double lat = Double.parseDouble(latValue.substring(0, 2))
                + Double.parseDouble(latValue.substring(2)) / 60;

        if (latDirection.equalsIgnoreCase("S")) {
            lat = -lat;
        }

        return lat;
    }

    private double parseLongitude(String lonValue, String lonDirection) {
        double lon = Double.parseDouble(lonValue.substring(0, 3))
                + Double.parseDouble(lonValue.substring(3)) / 60;

        if (lonDirection.equalsIgnoreCase("W")) {
            lon = -lon;
        }

        return lon;
    }

    private double parseSpeed(String speedValue, String speedUnit) {
        double speed = Double.parseDouble(speedValue);

        if (speedUnit.equalsIgnoreCase("K")) {
            speed = speed * 1.852;
        }

        return speed;
    }

    private double calculateDistanceBetween(GpsCoordinate c1, GpsCoordinate c2) {
        int radiusOfEarth = 6371;
        double lat1 = Math.toRadians(c1.getLatitude());
        double len1 = Math.toRadians(c1.getLongitude());
        double lat2 = Math.toRadians(c2.getLatitude());
        double len2 = Math.toRadians(c2.getLongitude());

        double latDiff = lat2 - lat1;
        double lenDiff = len2 - len1;

        double a = Math.pow(Math.sin(latDiff / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(lenDiff / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return radiusOfEarth * c;
    }
}
