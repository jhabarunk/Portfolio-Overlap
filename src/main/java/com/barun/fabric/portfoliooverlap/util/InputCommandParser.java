package com.barun.fabric.portfoliooverlap.util;

import com.barun.fabric.portfoliooverlap.manager.PortfolioManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class InputCommandParser {
    public static final String CURRENT_PORTFOLIO = "CURRENT_PORTFOLIO";
    public static final String CALCULATE_OVERLAP = "CALCULATE_OVERLAP";
    public static final String ADD_STOCK = "ADD_STOCK";
    private final Map<String, Consumer<String>> commandToUseCaseMap;

    public InputCommandParser(PortfolioManager portfolioManager) {
        commandToUseCaseMap = Map.ofEntries(
                new AbstractMap.SimpleImmutableEntry<String, Consumer<String>>(CURRENT_PORTFOLIO, portfolioManager::setCurrentPortfolio),
                new AbstractMap.SimpleImmutableEntry<String, Consumer<String>>(CALCULATE_OVERLAP, portfolioManager::printFundOverlap),
                new AbstractMap.SimpleImmutableEntry<String, Consumer<String>>(ADD_STOCK, portfolioManager::addStockToMutualFund)
        );
    }

    public List<Runnable> parseInputFromFile(Path path) throws IOException {
        List<String> lines = Files.readAllLines(path);
        long count = lines.stream()
                .map(line -> line.split(" ")[0])
                .filter(line -> !(CURRENT_PORTFOLIO.equals(line)
                        || CALCULATE_OVERLAP.equals(line)
                        || ADD_STOCK.equals(line)))
                .count();
        if (count > 0)
            throw new RuntimeException("Input contains command/s not allowed");

        return lines.stream().map(this::convertLineToUseCaseCommand)
                .toList();
    }

    private Runnable convertLineToUseCaseCommand(String line) {
        Consumer<String> commandConsumer = commandToUseCaseMap.get(commandFromInput(line));
        return () -> commandConsumer.accept(line);
    }

    private String commandFromInput(String input) {
        return input.split(" ")[0];
    }
}
