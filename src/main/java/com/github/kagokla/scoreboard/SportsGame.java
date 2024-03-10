package com.github.kagokla.scoreboard;

public interface SportsGame {

    String toPrettyScore();

    boolean hasWinner();

    Player getWinner();

    Player getLeader();

    void wonGamePoint(Player player);
}
