package com.github.kagokla.scoreboard;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum TennisPoint {

    ZERO(0, "0"),
    FIFTEEN(1, "15"),
    THIRTY(2, "30"),
    FORTY(3, "40"),
    ADVANTAGE(4, "Advantage");

    private final int gamePoint;
    private final String prettyGamePoint;

    TennisPoint(final int gamePoint, final String prettyGamePoint) {
        this.gamePoint = gamePoint;
        this.prettyGamePoint = prettyGamePoint;
    }

    public static TennisPoint fromGamePoint(final int gamePoint) {
        if (gamePoint < 0) {
            throw new IllegalArgumentException("Negative value can not be scored when playing Tennis");
        }
        return Arrays.stream(values())
                .filter(tennisPoint -> tennisPoint.gamePoint == gamePoint)
                .findFirst()
                .orElse(ADVANTAGE);
    }

    @Override
    public String toString() {
        return prettyGamePoint;
    }
}
