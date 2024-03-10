package com.github.kagokla.scoreboard;

public interface SportsGame {

    String toPrettyScore();

    boolean isDeuce();

    boolean hasWinner();

    Player getWinner();

    void wonGamePoint(Player player);
}
