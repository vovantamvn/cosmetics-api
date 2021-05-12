package com.app.cosmetics.core.discount;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DiscountRepositoryTest {

    @Autowired
    private DiscountRepository discountRepository;

    @BeforeEach
    void setUp() {
        discountRepository.deleteAll();
    }

    @Test
    void it_should_find_discount_by_code() {
        // Arrange
        Discount discount = new Discount(
                "ABCD",
                20,
                "description",
                LocalDateTime.now(),
                10
        );

        discountRepository.save(discount);

        // Act
        Optional<Discount> optDiscount = discountRepository.findDiscountByCode("ABCD");

        // Assert
        assertTrue(optDiscount.isPresent());
    }
}