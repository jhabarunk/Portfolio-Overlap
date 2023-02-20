package com.barun.fabric.portfoliooverlap.manager;

import java.util.Set;

public interface PortfolioManager {
    void setCurrentPortfolio(String command);

    void printFundOverlap(String command);

    void addStockToMutualFund(String command);

    Set<String> getMutualFunds();
}
