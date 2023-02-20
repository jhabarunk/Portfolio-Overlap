package com.barun.fabric.portfoliooverlap.model;

import java.util.LinkedHashSet;
import java.util.Set;

public class MutualFundDetails {
    private final String name;
    private final Set<String> stocks;

    public MutualFundDetails(String name, Set<String> stocks) {
        this.name = name;
        this.stocks = stocks;
    }

    public String getName() {
        return name;
    }

    public Set<String> getStocks() {
        return new LinkedHashSet<>(stocks);
    }

}
