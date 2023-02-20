package com.barun.fabric.portfoliooverlap.util;

import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MutualFundOverlapCalculatorTest {

    private final MutualFundOverlapCalculator calculator = new MutualFundOverlapCalculator();

    @Test
    void shouldReturnGreaterThanZeroForStocksInIntersection() {
        Set<String> stockSet1 = Set.of("ICICI", "AXIZ", "BAJAJ");
        Set<String> stockSet2 = Set.of("AXIZ", "ICICI", "BAJAJ", "JINDAL");

        Optional<String> expected = Optional.of("85.71");
        Optional<String> actual = calculator.calculateOverlap(stockSet1, stockSet2);
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnZeroAForDisjointStocks() {
        Set<String> stockSet1 = Set.of("ICICI", "AXIZ", "BAJAJ");
        Set<String> stockSet2 = Set.of("AXIZ2", "ICICI2", "BAJAJ3", "JINDAL");

        Optional<String> expected = Optional.empty();
        Optional<String> actual = calculator.calculateOverlap(stockSet1, stockSet2);
        assertEquals(expected, actual);
    }

}