package com.crusa.autopark.repository;

import com.crusa.autopark.dto.UserIdName;
import com.crusa.autopark.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Page<UserIdName> findAllProjectedBy(Pageable pageable);

    List<UserIdName> getAllProjectedBy();
}
