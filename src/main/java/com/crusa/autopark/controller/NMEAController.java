package com.crusa.autopark.controller;

import com.crusa.autopark.service.GpsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/nmea")
public class NMEAController {

    private final GpsService gpsService;

    public NMEAController(GpsService gpsService) {
        this.gpsService = gpsService;
    }

    @GetMapping()
    public String index() {
        return "nmea";
    }

    @PostMapping()
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();

        double result = gpsService.calculateDistance(convFile);

        model.addAttribute("distance", result);
        return "nmea";
    }
}
