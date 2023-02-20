package com.barun.fabric.portfoliooverlap.util;

import com.barun.fabric.portfoliooverlap.manager.PortfolioManager;
import com.barun.fabric.portfoliooverlap.manager.PortfolioManagerImpl;
import com.barun.fabric.portfoliooverlap.model.MasterFundsData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InputCommandParserTest {

    private MutualFundOverlapCalculator calculator;
    private PortfolioManager portfolioManager;


    @BeforeEach
    void beforeEach() {
        MasterFundsData masterFundsData = Mockito.mock(MasterFundsData.class);
        calculator = new MutualFundOverlapCalculator();

        portfolioManager = new PortfolioManagerImpl(masterFundsData, calculator);
    }

    @Test
    void shouldReturnListOfRunnables() throws IOException {
        InputCommandParser inputCommandParser = new InputCommandParser(portfolioManager);

        Path path = Files.createTempFile("test", ".txt");
        String contents = "CURRENT_PORTFOLIO FUND1 FUND2 \n" +
                "CALCULATE_OVERLAP FUND3 \n" +
                "CALCULATE_OVERLAP FUND3 \n" +
                "ADD_STOCK FUND2 ABC";
        Files.write(path, contents.getBytes());
        List<Runnable> lines = inputCommandParser.parseInputFromFile(path);
        assertEquals(4, lines.size());
    }

    @Test
    void shouldThrowExceptionWhenCommandIsNotValid() throws IOException {
        InputCommandParser inputCommandParser = new InputCommandParser(portfolioManager);

        Path path = Files.createTempFile("test", ".txt");
        String contents = "CURRENT_PORTFOLIO_1 FUND1 FUND2 \n" +
                "CALCULATE_OVERLAP FUND3 \n" +
                "CALCULATE_OVERLAP FUND3 \n" +
                "ADD_STOCK FUND2 ABC";
        Files.write(path, contents.getBytes());
        ;
        assertThrows(RuntimeException.class, () -> inputCommandParser.parseInputFromFile(path));
    }
}