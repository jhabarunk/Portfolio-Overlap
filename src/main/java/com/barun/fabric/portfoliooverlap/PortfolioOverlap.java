package com.barun.fabric.portfoliooverlap;

import com.barun.fabric.portfoliooverlap.manager.PortfolioManager;
import com.barun.fabric.portfoliooverlap.manager.PortfolioManagerImpl;
import com.barun.fabric.portfoliooverlap.model.MasterFundsData;
import com.barun.fabric.portfoliooverlap.util.InputCommandParser;
import com.barun.fabric.portfoliooverlap.util.MutualFundOverlapCalculator;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class PortfolioOverlap {
    public static void main(String[] args) throws IOException {

        MasterFundsData masterFundsData = new MasterFundsData("data/stock_data.json");

        MutualFundOverlapCalculator mutualFundOverlapCalculator = new MutualFundOverlapCalculator();

        PortfolioManager portfolioManager = new PortfolioManagerImpl(masterFundsData, mutualFundOverlapCalculator);

        InputCommandParser inputCommandParser = new InputCommandParser(portfolioManager);

        List<Runnable> commands = inputCommandParser.parseInputFromFile(Paths.get(args[0]));

        commands.forEach(Runnable::run);


    }
}
