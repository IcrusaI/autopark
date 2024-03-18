package com.crusa.autopark.controller;

import com.crusa.autopark.dto.UserIdName;
import com.crusa.autopark.entity.User;
import com.crusa.autopark.service.CarService;
import com.crusa.autopark.service.DealerService;
import com.crusa.autopark.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final CarService carService;
    private final DealerService dealerService;

    @Autowired
    public UserController(UserService userService, CarService carService, DealerService dealerService) {
        this.userService = userService;
        this.carService = carService;
        this.dealerService = dealerService;
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute("user") User user) {
        userService.add(user);
        return "redirect:/users";
    }

    @GetMapping("/add")
    public String showAddCarForm(Model model) {
        model.addAttribute("user", new User());

        return "addUser";
    }

    @GetMapping
    public String listOfUsers(@RequestParam(defaultValue = "0") Integer page,
                              @RequestParam(defaultValue = "10") Integer count,
                              Model model) {
        if (page < 0) {
            return "redirect:cars?page=0";
        }

        Pageable pageable = PageRequest.of(page, count);

        Page<UserIdName> pageData = userService.findPaginated(pageable);

        if (page >= pageData.getTotalPages()) {
            return "redirect:users?page=" + (pageData.getTotalPages() - 1);
        }

        model.addAttribute("users", pageData.getContent());
        model.addAttribute("totalPages", pageData.getTotalPages());
        model.addAttribute("currentPage", pageData.getNumber());

        return "usersPage";
    }

    @GetMapping("/{id}")
    public String getUser(@PathVariable Long id,
                          Model model) {
        Optional<User> user = userService.findById(id);

        if (user.isEmpty()) {
            return "redirect:error";
        }


        if (user.get().getDelegateDealer() == null) {
            model.addAttribute("dealers", dealerService.findByNullDelegate());
        }
        model.addAttribute("cars", carService.findByNullOwner());
        model.addAttribute("user", user.get());

        return "userPage";
    }
}
