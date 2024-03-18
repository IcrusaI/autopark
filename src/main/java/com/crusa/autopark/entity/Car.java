package com.crusa.autopark.entity;

import com.crusa.autopark.common.CarNumberGenerator;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import java.util.Date;


@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "cars")
public class Car {
    @Id
    @NonNull
    @Pattern(regexp = CarNumberGenerator.VALID_REGEX, message = "Номер должен соответствовать формату x000xx000")
    private String number;

    @NonNull
    private Date buildDate;

    @ManyToOne
    private User owner;

    @ManyToOne
    private Dealer serviced;
}
