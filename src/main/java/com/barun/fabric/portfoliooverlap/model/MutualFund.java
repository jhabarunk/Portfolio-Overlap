package com.barun.fabric.portfoliooverlap.model;

import java.util.Collections;
import java.util.Set;

public class MutualFund {
    private final Set<MutualFundDetails> funds;

    public MutualFund(Set<MutualFundDetails> funds) {
        this.funds = funds;
    }

    public Set<MutualFundDetails> getFunds() {
        return Collections.unmodifiableSet(funds);
    }
}
