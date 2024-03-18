package com.crusa.autopark.common;

import com.crusa.autopark.entity.Car;
import com.crusa.autopark.entity.Dealer;
import com.crusa.autopark.entity.User;
import com.crusa.autopark.repository.CarRepo;
import com.crusa.autopark.repository.DealerRepo;
import com.crusa.autopark.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final UserRepo userRepo;
    private final CarRepo carRepo;
    private final DealerRepo dealerRepo;

    @Autowired
    public DatabaseSeeder(UserRepo userRepo, CarRepo carRepo, DealerRepo dealerRepo) {
        this.userRepo = userRepo;
        this.carRepo = carRepo;
        this.dealerRepo = dealerRepo;
    }

    @Override
    public void run(String... args) {
        List<User> users = new ArrayList<>();
        List<Dealer> dealers = new ArrayList<>();

        Random random = new Random();
        for (int i = 1; i <= 100; i++) {
            User user = new User();
            user.setFirstName("FirstName" + i);
            user.setLastName("LastName" + i);
            user.setSurName("SurName" + i);
            user.setPhone(String.valueOf(random.nextInt(900_000) + 100_000));
            user.setEmail("Email" + i + "@abazhenov.ru");
            users.add(user);
            userRepo.save(user);
        }

        for (int i = 1; i <= 20; i++) {
            Dealer dealer = new Dealer();
            dealer.setName("ООО Яндекс " + i);
            dealer.setEmail("Email" + i + "@abazhenov.ru");

            User user;
            do {
                user = users.get(random.nextInt(users.size()));
            } while (isUserADelegate(dealers, user));

            dealer.setDelegate(user);

            dealers.add(dealer);
            dealerRepo.save(dealer);
        }

        for (int i = 1; i <= 200; i++) {
            Car car = new Car();
            car.setNumber(CarNumberGenerator.generate());
            car.setBuildDate(new Date());
            if (random.nextInt(2) == 1) {
                car.setOwner(users.get(random.nextInt(users.size())));
            }
            if (random.nextInt(5) > 1) {
                car.setServiced(dealers.get(random.nextInt(dealers.size())));
            }

            carRepo.save(car);
        }
    }

    private boolean isUserADelegate(List<Dealer> dealers, User user) {
        return dealers.stream().anyMatch(dealer -> dealer.getDelegate() != null && dealer.getDelegate().equals(user));
    }
}
