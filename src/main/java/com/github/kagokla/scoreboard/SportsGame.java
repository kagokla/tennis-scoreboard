package com.github.kagokla.scoreboard;

public interface SportsGame {

    String toPrettyScore();

    boolean hasWinner();

    Player getLeader();

    void wonGamePoint(Player player);
}
