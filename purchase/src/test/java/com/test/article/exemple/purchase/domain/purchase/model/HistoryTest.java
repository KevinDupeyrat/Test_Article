package com.test.article.exemple.purchase.domain.purchase.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HistoryTest {

    @Test
    void history_are_identical() {
        // given
        final var history1 = new History(1, Product.TOOTH_PASTE, 3);
        final var history2 = new History(1, Product.TOOTH_PASTE, 3);
        // when
        final var result = history1.equals(history2);
        // then
        assertTrue(result);
    }

    @ParameterizedTest
    @MethodSource("customerParam")
    void customers_are_not_identical(final Object history2) {
        // given
        final var history = new History(1, Product.SHAMPOO, 5);
        // when
        final var result = history.equals(history2);
        // then
        assertFalse(result);
    }

    public static Stream<Arguments> customerParam() {
        return Stream.of(
                null,
                Arguments.of(new History(2, Product.SHAMPOO, 3)),
                Arguments.of(new History(1, Product.SHOWER_GEL, 3)),
                Arguments.of(new History(1, Product.SHAMPOO, 45)),
                Arguments.of(new Object())
        );
    }
}
