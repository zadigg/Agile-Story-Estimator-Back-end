package com.storyestimationcore.score_estimation_core.domain.common;

public enum Score {
    ZERO(0);

    private final int value;

    Score(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
