package com.test.article.exemple.purchase.domain.customer.model;

import com.test.article.exemple.purchase.domain.customer.model.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomerTest {

    @Test
    void customers_are_identical() {
        // given
        final var customer1 = new Customer(1, "foo", "bar@bar.com");
        final var customer2 = new Customer(1, "foo", "bar@bar.com");
        // when
        final var result = customer1.equals(customer2);
        // then
        assertTrue(result);
    }

    @ParameterizedTest
    @MethodSource("customerParam")
    void customers_are_not_identical(final Object customer2) {
        // given
        final var customer1 = new Customer(1, "foo", "bar@bar.com");
        // when
        final var result = customer1.equals(customer2);
        // then
        assertFalse(result);
    }

    public static Stream<Arguments> customerParam() {
        return Stream.of(
                null,
                Arguments.of(new Customer(2, "foo", "bar@bar.com")),
                Arguments.of(new Customer(1, "name", "bar@bar.com")),
                Arguments.of(new Customer(1, "foo", "email@email.com")),
                Arguments.of(new Object())
        );
    }
}
