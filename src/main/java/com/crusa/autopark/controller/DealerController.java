package com.crusa.autopark.controller;

import com.crusa.autopark.dto.UserIdName;
import com.crusa.autopark.entity.Dealer;
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

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/dealers")
public class DealerController {
    private final UserService userService;
    private final DealerService dealerService;
    private final CarService carService;

    @Autowired
    public DealerController(UserService userService, DealerService dealerService, CarService carService) {
        this.userService = userService;
        this.dealerService = dealerService;
        this.carService = carService;
    }


    @GetMapping
    public String listOfDealers(@RequestParam(defaultValue = "0") Integer page,
                                @RequestParam(defaultValue = "10") Integer count,
                                Model model) {
        if (page < 0) {
            return "redirect:dealers?page=0";
        }

        Pageable pageable = PageRequest.of(page, count);

        Page<Dealer> pageData = dealerService.findPaginated(pageable);

        if (page >= pageData.getTotalPages()) {
            return "redirect:dealers?page=" + (pageData.getTotalPages() - 1);
        }

        model.addAttribute("cars", carService.findByNullDealer());
        model.addAttribute("dealers", pageData.getContent());
        model.addAttribute("totalPages", pageData.getTotalPages());
        model.addAttribute("currentPage", pageData.getNumber());

        return "dealersPage";
    }

    @GetMapping("/{id}")
    public String getDealer(@PathVariable Long id,
                            Model model) {
        Optional<Dealer> dealer = dealerService.findById(id);

        if (dealer.isEmpty()) {
            return "redirect:error";
        }

        model.addAttribute("dealer", dealer.get());

        return "dealerPage";
    }

    @PostMapping("/add")
    public String addDealer(@ModelAttribute("dealer") Dealer dealer) {
        dealerService.add(dealer);

        return "redirect:/dealers";
    }

    @GetMapping("/add")
    public String showAddDealerForm(Model model) {
        model.addAttribute("dealer", new Dealer());

        List<UserIdName> userList = userService.getAll();

        model.addAttribute("users", userList);

        return "addDealer";
    }

}
