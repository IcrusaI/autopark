package com.crusa.autopark.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "dealers")
public class Dealer {
    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String email;

    @OneToOne
    private User delegate;

    @OneToMany(mappedBy = "serviced")
    private List<Car> servicedCar;
}
