package com.crusa.autopark.repository;

import com.crusa.autopark.entity.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepo extends JpaRepository<Car, String> {
    Page<Car> findAllProjectedBy(Pageable pageable);

    List<Car> findAllByOwnerIsNull();

    List<Car> findAllByServicedIsNull();
}
