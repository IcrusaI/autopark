package com.crusa.autopark.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import java.util.List;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    private String surName;

    @NonNull
    private String phone;

    @NonNull
    private String email;

    @OneToMany(mappedBy = "owner")
    private List<Car> cars;

    @OneToOne(mappedBy = "delegate")
    private Dealer delegateDealer;
}
