package com.crusa.autopark.repository;

import com.crusa.autopark.entity.Dealer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DealerRepo extends JpaRepository<Dealer, Long> {
    Page<Dealer> findAllProjectedBy(Pageable pageable);

    List<Dealer> findAllByDelegateIsNull();
}
