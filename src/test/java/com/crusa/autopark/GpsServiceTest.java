package com.crusa.autopark;

import com.crusa.autopark.service.GpsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GpsServiceTest {
    private GpsService gpsService;

    @BeforeEach
    void setUp() {
        gpsService = new GpsService();
    }

    @Test
    void calculateDistance() throws Exception {
        File file = new ClassPathResource("nmea.log").getFile();

        double distance = gpsService.calculateDistance(file);

        assertEquals(1.91565, distance, 0.001);
    }
}