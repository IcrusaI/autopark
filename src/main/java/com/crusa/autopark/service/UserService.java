package com.crusa.autopark.service;

import com.crusa.autopark.dto.UserIdName;
import com.crusa.autopark.entity.User;
import com.crusa.autopark.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User add(User user) {
        return userRepo.save(user);
    }

    public List<UserIdName> getAll() {
        return userRepo.getAllProjectedBy();
    }

    public Page<UserIdName> findPaginated(Pageable pageable) {
        return userRepo.findAllProjectedBy(pageable);
    }

    public Optional<User> findById(Long id) {
        return userRepo.findById(id);
    }
}
