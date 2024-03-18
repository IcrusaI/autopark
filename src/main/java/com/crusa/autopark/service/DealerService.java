package com.crusa.autopark.service;

import com.crusa.autopark.entity.Dealer;
import com.crusa.autopark.entity.User;
import com.crusa.autopark.repository.DealerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DealerService {

    private final DealerRepo dealerRepo;

    @Autowired
    public DealerService(DealerRepo dealerRepo) {
        this.dealerRepo = dealerRepo;
    }

    public Dealer add(Dealer dealer) {
        return dealerRepo.save(dealer);
    }

    public List<Dealer> findByNullDelegate() {
        return dealerRepo.findAllByDelegateIsNull();
    }

    public Page<Dealer> findPaginated(Pageable pageable) {
        return dealerRepo.findAllProjectedBy(pageable);
    }

    public Optional<Dealer> findById(Long id) {
        return dealerRepo.findById(id);
    }


    public void removeDelegate(Dealer dealer) {
        dealer.setDelegate(null);

        dealerRepo.save(dealer);
    }

    public void setDelegate(Dealer dealer, User user) {
        dealer.setDelegate(user);

        dealerRepo.save(dealer);
    }
}
