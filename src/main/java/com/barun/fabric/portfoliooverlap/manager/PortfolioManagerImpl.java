package com.barun.fabric.portfoliooverlap.manager;

import com.barun.fabric.portfoliooverlap.model.MasterFundsData;
import com.barun.fabric.portfoliooverlap.util.MutualFundOverlapCalculator;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PortfolioManagerImpl implements PortfolioManager {

    private final MasterFundsData masterFundsData;
    private final MutualFundOverlapCalculator calculator;
    private Set<String> mutualFunds;

    public PortfolioManagerImpl(MasterFundsData masterFundsData, MutualFundOverlapCalculator calculator) {
        this.masterFundsData = masterFundsData;
        this.calculator = calculator;
    }

    @Override
    public void setCurrentPortfolio(String command) {
        Set<String> mutualFunds = Stream.of(getCommandArgs(command))
                .skip(1)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        mutualFunds.forEach(masterFundsData::mutualFundPresentInMasterData);

        this.mutualFunds = mutualFunds;
    }

    @Override
    public void printFundOverlap(String command) {
        String[] args = getCommandArgs(command);

        if (args.length > 2) {
            throw new RuntimeException("Only one mutual fund allowed to check overlap");
        }

        String overlapMfName = args[1];

        Set<String> stocksInMutualFundOverlap = masterFundsData.getStocksInMutualFund(overlapMfName);

        mutualFunds.forEach(mutualFund -> {
            Set<String> stocks = masterFundsData.getStocksInMutualFund(mutualFund);
            if (!stocks.isEmpty()) {
                Optional<String> overlapPercent = calculator
                        .calculateOverlap(masterFundsData.getStocksInMutualFund(mutualFund),
                                stocksInMutualFundOverlap);
                overlapPercent.ifPresent(s -> System.out.println(overlapMfName + " " + mutualFund + " " + s + "%"));
            }
        });

    }

    @Override
    public void addStockToMutualFund(String command) {
        String[] args = getCommandArgs(command);
        String mutualFundName = args[1];
        Set<String> stocksToAdd = Stream.of(args).skip(2).collect(Collectors.toCollection(HashSet::new));
        masterFundsData.addStocksInMutualFund(mutualFundName, stocksToAdd);
    }

    private String[] getCommandArgs(String command) {
        return command.split(" ");
    }

    @Override
    public Set<String> getMutualFunds() {
        return Set.copyOf(mutualFunds);
    }
}
