package com.github.kagokla.scoreboard.tennis;

import com.github.kagokla.scoreboard.Player;
import com.github.kagokla.scoreboard.SportsGame;
import lombok.RequiredArgsConstructor;

/**
 * Score calculation rules for tennis games
 */
@RequiredArgsConstructor
public class TennisGame implements SportsGame {

    private final Player firstPlayer;
    private final Player secondPlayer;
    private int firstPlayerPoints = 0;
    private int secondPlayerPoints = 0;

    @Override
    public String toPrettyScore() {
        if (hasWinner()) {
            return getLeader() + " wins the game";
        }
        if (isDeuceOrAdvantage()) {
            if (firstPlayerPoints == secondPlayerPoints) {
                return "Deuce";
            }
            return toPrettyPoint(getLeader());
        }
        return toPrettyPoint(firstPlayer) + " / " + toPrettyPoint(secondPlayer);
    }

    @Override
    public boolean hasWinner() {
        return (firstPlayerPoints >= 4 || secondPlayerPoints >= 4)
                && (Math.abs(firstPlayerPoints - secondPlayerPoints) >= 2);
    }

    @Override
    public Player getLeader() {
        // Assuming there is a tie on points, leader is the first player
        return firstPlayerPoints >= secondPlayerPoints ? firstPlayer : secondPlayer;
    }

    @Override
    public void wonGamePoint(final Player player) {
        if (firstPlayer.equals(player)) {
            firstPlayerPoints++;
        } else if (secondPlayer.equals(player)) {
            secondPlayerPoints++;
        } else {
            throw new IllegalArgumentException(player + " is not part of this game");
        }
    }

    private boolean isDeuceOrAdvantage() {
        return firstPlayerPoints >= 3 && secondPlayerPoints >= 3;
    }

    private String toPrettyPoint(final Player player) {
        if (firstPlayer.equals(player)) {
            return player + " : " + TennisPoint.fromGamePoint(firstPlayerPoints);
        }
        return player + " : " + TennisPoint.fromGamePoint(secondPlayerPoints);
    }
}
