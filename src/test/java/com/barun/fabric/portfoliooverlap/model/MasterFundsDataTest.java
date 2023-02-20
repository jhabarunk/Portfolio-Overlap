package com.barun.fabric.portfoliooverlap.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class MasterFundsDataTest {

    private MasterFundsData masterFundsData;

    @BeforeEach
    void beforeEach() {
        masterFundsData = new MasterFundsData("data/stock_data.json");
    }

    @Test
    void shouldReturnStockPresentInMutualFund() {
        Set<String> actualStocks = masterFundsData.getStocksInMutualFund("FUND 1");
        Set<String> expectedStocks = Set.of("A", "B");
        assertEquals(actualStocks.size(), 2);
        assertEquals(expectedStocks, actualStocks);
    }

    @Test
    void shouldThrowRuntimeExceptionWhenMfNotPresentInMasterFundsData() {
        assertEquals(masterFundsData.getStocksInMutualFund("FUND 3"), Set.of());
    }

    @Test
    void shouldAddStocksInMutualFunds() {
        masterFundsData.addStocksInMutualFund("FUND 1", Set.of("M"));

        Set<String> stocksInMutualFund = masterFundsData.getStocksInMutualFund("FUND 1");
        assertEquals(stocksInMutualFund.size(), 3);
        assertEquals(stocksInMutualFund, Set.of("A", "B", "M"));
    }

    @Test
    void shouldReturnTrueIfMutualFundPresentInMasterData() {
        assertTrue(masterFundsData.mutualFundPresentInMasterData("FUND 1"));
    }

    @Test
    void shouldThrowRuntimeExceptionIfMutualFundNotPresentInMasterData() {
        assertThrows(RuntimeException.class, () -> masterFundsData.mutualFundPresentInMasterData("FUND 3"));
    }

}