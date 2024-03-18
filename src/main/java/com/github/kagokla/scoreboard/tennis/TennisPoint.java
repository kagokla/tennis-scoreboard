package com.github.kagokla.scoreboard.tennis;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Handling of the conversion between the points of the tennis game and their beautiful representation
 */
public enum TennisPoint {
    ZERO(0, "0"),
    FIFTEEN(1, "15"),
    THIRTY(2, "30"),
    FORTY(3, "40"),
    ADVANTAGE(4, "Advantage");

    private static final Map<Integer, TennisPoint> gamePointLookup = Arrays.stream(values())
            .collect(Collectors.toUnmodifiableMap(tennisPoint -> tennisPoint.gamePoint, Function.identity()));
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
        return gamePointLookup.getOrDefault(gamePoint, TennisPoint.ADVANTAGE);
    }

    @Override
    public String toString() {
        return prettyGamePoint;
    }
}
