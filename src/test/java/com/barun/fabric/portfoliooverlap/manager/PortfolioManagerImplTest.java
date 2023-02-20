package com.barun.fabric.portfoliooverlap.manager;

import com.barun.fabric.portfoliooverlap.model.MasterFundsData;
import com.barun.fabric.portfoliooverlap.util.MutualFundOverlapCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

class PortfolioManagerImplTest {

    public static final String CURRENT_PORTFOLIO = "CURRENT_PORTFOLIO AXIS_BLUECHIP ICICI_PRU_BLUECHIP";
    public static final String AXIS_BLUECHIP = "AXIS_BLUECHIP";
    private final PrintStream STANDARD_OUT = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    private PortfolioManager portfolioManager;
    private MutualFundOverlapCalculator calculator = new MutualFundOverlapCalculator();
    private MasterFundsData masterFundsData;

    @BeforeEach
    void beforeEach() {
        masterFundsData = Mockito.mock(MasterFundsData.class);
        portfolioManager = new PortfolioManagerImpl(masterFundsData, calculator);
    }

    @Test
    @DisplayName(value = "Should Throw Runtime Exception when invalid fund is passed to be added in current portfolio")
    void shouldThrowRuntimeExceptionWhenInvalidFundIsPassed() {
        final String command = "CURRENT_PORTFOLIO AXIS_BLUECHIP";

        when(masterFundsData.mutualFundPresentInMasterData(AXIS_BLUECHIP)).thenThrow(new RuntimeException("AXIS_BLUECHIP not present in master data"));

        assertThrows(RuntimeException.class, () -> portfolioManager.setCurrentPortfolio(command));
    }

    @Test
    void shouldSetCurrentPortfolio() {
        String command = CURRENT_PORTFOLIO;

        portfolioManager.setCurrentPortfolio(command);

        assertEquals(portfolioManager.getMutualFunds().size(), 2);
    }

    @Test
    @DisplayName(value = "Should Throw Runtime Exception when there are more than 1 Mutual Funds passed in printFundOverlap method")
    void shouldThrowRuntimeExceptionForPrintOverlap() {
        assertThrows(
                RuntimeException.class,
                () -> portfolioManager.printFundOverlap("CALCULATE_OVERLAP MIRAE_ASSET_EMERGING_BLUECHIP MIRAE_ASSET_EMERGING_BLUECHIP_2")
        );
    }

    @Test
    void shouldPrintOverlapPercentage() {
        System.setOut(new PrintStream(outputStreamCaptor));

        portfolioManager.setCurrentPortfolio(CURRENT_PORTFOLIO);
        when(masterFundsData.getStocksInMutualFund("UTI_NIFTY_INDEX")).thenReturn(Set.of("B", "C", "D"));
        when(masterFundsData.getStocksInMutualFund(AXIS_BLUECHIP)).thenReturn(Set.of("A", "B", "C"));

        portfolioManager.printFundOverlap("CALCULATE_OVERLAP UTI_NIFTY_INDEX");

        assertEquals(outputStreamCaptor.toString().trim(), "UTI_NIFTY_INDEX AXIS_BLUECHIP 66.67%");

        System.setOut(STANDARD_OUT);
    }

    @Test
    @DisplayName(value = "Should throw Runtime Exception when invalid mutual fund is passed for addStockToMutualFund")
    void shouldThrowRuntimeExceptionWhenInvalidMfPassedForAddStockToMutualFund() {
        String command = "ADD_STOCK AXIS_BLUECHIP TCS";

        doThrow(new RuntimeException("FUND_NOT_FOUND: " + AXIS_BLUECHIP)).when(masterFundsData)
                .addStocksInMutualFund(AXIS_BLUECHIP, Set.of("TCS"));

        assertThrows(RuntimeException.class, () -> portfolioManager.addStockToMutualFund(command));
    }

}