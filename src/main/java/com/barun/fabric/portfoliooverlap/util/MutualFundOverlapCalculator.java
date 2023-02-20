package com.barun.fabric.portfoliooverlap.util;

import java.util.Optional;
import java.util.Set;

public class MutualFundOverlapCalculator {

    private final String DECIMAL_PRECISION = "%.2f";

    public Optional<String> calculateOverlap(Set<String> currentStocks, Set<String> newStocks) {
        long totalCount = currentStocks.size() + newStocks.size();

        long intersectionStocksCount = currentStocks.stream().filter(newStocks::contains).count();

        double overlap = (200 * (double) intersectionStocksCount / totalCount);

        return overlap == 0d ? Optional.empty() : Optional.of(String.format(DECIMAL_PRECISION, overlap));
    }
}
