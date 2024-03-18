package com.crusa.autopark.controller;

import com.crusa.autopark.common.CarNumberGenerator;
import com.crusa.autopark.dto.UserIdName;
import com.crusa.autopark.entity.Car;
import com.crusa.autopark.service.CarService;
import com.crusa.autopark.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cars")
public class CarController {
    private final CarService carService;
    private final UserService userService;

    @Autowired
    public CarController(CarService carService, UserService userService) {
        this.carService = carService;
        this.userService = userService;
    }


    @GetMapping
    public String listOfCars(@RequestParam(defaultValue = "0") Integer page,
                             @RequestParam(defaultValue = "10") Integer count,
                             Model model) {
        if (page < 0) {
            return "redirect:cars?page=0";
        }

        Pageable pageable = PageRequest.of(page, count);

        Page<Car> pageData = carService.findPaginated(pageable);

        if (page >= pageData.getTotalPages()) {
            return "redirect:cars?page=" + (pageData.getTotalPages() - 1);
        }

        model.addAttribute("cars", pageData.getContent());
        model.addAttribute("totalPages", pageData.getTotalPages());
        model.addAttribute("currentPage", pageData.getNumber());

        return "carsPage";
    }

    @GetMapping("/{number}")
    public String getCar(@PathVariable String number,
                         Model model) {
        Optional<Car> car = carService.findById(number);

        if (car.isEmpty()) {
            return "redirect:error";
        }

        model.addAttribute("car", car.get());

        return "userPage";
    }

    @PostMapping("/add")
    public String addCar(@ModelAttribute("car") Car car) {
        carService.add(car);
        return "redirect:/cars";
    }

    @GetMapping("/add")
    public String showAddCarForm(Model model) {
        model.addAttribute("car", new Car());

        List<UserIdName> userList = userService.getAll();

        model.addAttribute("users", userList);
        model.addAttribute("carNumberRegEx", CarNumberGenerator.VALID_REGEX);

        return "addCar";
    }
}
