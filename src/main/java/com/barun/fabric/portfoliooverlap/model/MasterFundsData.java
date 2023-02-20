package com.barun.fabric.portfoliooverlap.model;

import com.barun.fabric.portfoliooverlap.util.FileReaderUtil;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class MasterFundsData {

    private final String masterFundsDataPath;
    private Map<String, Set<String>> masterMutualFundsData;

    public MasterFundsData(String filePath) {
        masterFundsDataPath = filePath;
        this.initialiseMasterFundsData();
    }

    private void initialiseMasterFundsData() {
        MutualFund mutualFund = FileReaderUtil.readJson(this.masterFundsDataPath, MutualFund.class);
        this.masterMutualFundsData = mutualFund
                .getFunds()
                .stream()
                .collect(Collectors.toMap(MutualFundDetails::getName, MutualFundDetails::getStocks));
    }

    public Set<String> getStocksInMutualFund(String mutualFund) throws RuntimeException {
        Set<String> stockList = this.masterMutualFundsData.get(mutualFund);
        if (Objects.isNull(stockList)) {
            System.out.println("FUND_NOT_FOUND");
            return Set.of();
        }
        return stockList;
    }

    public void addStocksInMutualFund(String mutualFundName, Set<String> stocksToAdd) {
        getStocksInMutualFund(mutualFundName).addAll(stocksToAdd);
    }

    public boolean mutualFundPresentInMasterData(String mutualFund) {
        if (masterMutualFundsData.containsKey(mutualFund))
            return true;
        throw new RuntimeException(mutualFund + " not present in master data");
    }
}

