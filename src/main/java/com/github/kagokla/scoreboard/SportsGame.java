package com.github.kagokla.scoreboard;

public interface SportsGame {

    String toPrettyScore();

    boolean isDraw();

    boolean hasWinner();

    Player getWinner();

    void wonGamePoint(Player player);
}
