package com.crusa.autopark.service;

import com.crusa.autopark.entity.Car;
import com.crusa.autopark.entity.Dealer;
import com.crusa.autopark.entity.User;
import com.crusa.autopark.repository.CarRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    private final CarRepo carRepo;

    @Autowired
    public CarService(CarRepo carRepo) {
        this.carRepo = carRepo;
    }


    public Car add(Car car) {
        if (carRepo.existsById(car.getNumber())) {
            throw new DataIntegrityViolationException("Car with number " + car.getNumber() + " already exists.");
        }

        return carRepo.save(car);
    }

    public List<Car> findByNullOwner() {
        return carRepo.findAllByOwnerIsNull();
    }

    public List<Car> findByNullDealer() {
        return carRepo.findAllByServicedIsNull();
    }

    public Page<Car> findPaginated(Pageable pageable) {
        return carRepo.findAllProjectedBy(pageable);
    }

    public Optional<Car> findById(String number) {
        return carRepo.findById(number);
    }

    public void removeOwner(Car car) {
        car.setOwner(null);

        carRepo.save(car);
    }

    public void setOwner(Car car, User user) {
        car.setOwner(user);

        carRepo.save(car);
    }

    public void removeDealer(Car car) {
        car.setServiced(null);

        carRepo.save(car);
    }

    public void setDealer(Car car, Dealer dealer) {
        car.setServiced(dealer);

        carRepo.save(car);
    }
}
