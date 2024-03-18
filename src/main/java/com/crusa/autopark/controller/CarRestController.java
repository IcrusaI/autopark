package com.crusa.autopark.controller;

import com.crusa.autopark.entity.Car;
import com.crusa.autopark.entity.Dealer;
import com.crusa.autopark.entity.User;
import com.crusa.autopark.service.CarService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cars")
public class CarRestController {
    private final CarService carService;

    public CarRestController(CarService carService) {
        this.carService = carService;
    }

    @DeleteMapping("/{car}/owner")
    public void removeOwner(@PathVariable("car") Car car) {
        carService.removeOwner(car);
    }

    @PutMapping("/{car}/owner")
    public void setOwner(@PathVariable("car") @ModelAttribute("car") Car car, @ModelAttribute("userId") User user) {
        carService.setOwner(car, user);
    }

    @DeleteMapping("/{car}/dealer")
    public void removeDealer(@PathVariable("car") Car car) {
        carService.removeDealer(car);
    }

    @PutMapping("/{car}/dealer")
    public void setDealer(@PathVariable("car") @ModelAttribute("car") Car car, @ModelAttribute("dealerId") Dealer dealer) {
        carService.setDealer(car, dealer);
    }
}
