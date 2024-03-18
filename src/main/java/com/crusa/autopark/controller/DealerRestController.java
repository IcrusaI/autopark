package com.crusa.autopark.controller;

import com.crusa.autopark.entity.Dealer;
import com.crusa.autopark.entity.User;
import com.crusa.autopark.service.DealerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dealers")
public class DealerRestController {
    private final DealerService dealerService;

    public DealerRestController(DealerService dealerService) {
        this.dealerService = dealerService;
    }

    @DeleteMapping("/{dealer}/delegate")
    public void removeDelegate(@PathVariable("dealer") Dealer dealer) {
        dealerService.removeDelegate(dealer);
    }

    @PutMapping("/{dealer}/delegate")
    public void setOwner(@PathVariable("dealer") @ModelAttribute("dealer") Dealer dealer, @ModelAttribute("userId") User user) {
        dealerService.setDelegate(dealer, user);
    }
}
