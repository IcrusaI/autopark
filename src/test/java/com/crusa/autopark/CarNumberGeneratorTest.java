package com.crusa.autopark;

import com.crusa.autopark.common.CarNumberGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CarNumberGeneratorTest {
    @Test
    public void generatedCarNumberMatchesPattern() {
        String carNumber = CarNumberGenerator.generate();
        assertTrue(carNumber.matches(CarNumberGenerator.VALID_REGEX),
                "Generated car number " + carNumber + " does not match pattern");
    }
}

