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

    private final int wonPoint;
    private final String prettyWonPoint;

    TennisPoint(final int wonPoint, final String prettyWonPoint) {
        this.wonPoint = wonPoint;
        this.prettyWonPoint = prettyWonPoint;
    }

    public static TennisPoint fromWonPoint(final int wonPoint) {
        if (wonPoint < 0) {
            throw new IllegalArgumentException("Negative value can not be scored when playing Tennis");
        }
        return Arrays.stream(values())
                .filter(tennisPoint -> tennisPoint.wonPoint == wonPoint)
                .findFirst()
                .orElse(ADVANTAGE);
    }

    @Override
    public String toString() {
        return prettyWonPoint;
    }
}
