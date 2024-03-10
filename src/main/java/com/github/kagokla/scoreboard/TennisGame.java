package com.github.kagokla.scoreboard;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TennisGame implements SportsGame {

    private final Player firstPlayer;
    private final Player secondPlayer;
    private Player winner = null;
    private int firstPlayerPoints = 0;
    private int secondPlayerPoints = 0;

    @Override
    public String toPrettyScore() {
        if (hasWinner()) {
            saveWinner();
            return winner + " wins the game";
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
        return (firstPlayerPoints >= 4 && firstPlayerPoints - secondPlayerPoints >= 2) || (secondPlayerPoints >= 4 && secondPlayerPoints - firstPlayerPoints >= 2);
    }

    @Override
    public Player getLeader() {
        return firstPlayerPoints > secondPlayerPoints ? firstPlayer : secondPlayer;
    }

    @Override
    public void wonGamePoint(@NonNull final Player player) {
        if (firstPlayer.equals(player)) {
            firstPlayerPoints++;
        } else if (secondPlayer.equals(player)) {
            secondPlayerPoints++;
        } else {
            throw new IllegalArgumentException(player + " is not part of this game");
        }
    }

    public boolean isDeuceOrAdvantage() {
        return firstPlayerPoints >= 3 && secondPlayerPoints >= 3;
    }

    private void saveWinner() {
        winner = firstPlayerPoints > secondPlayerPoints ? firstPlayer : secondPlayer;
    }

    private String toPrettyPoint(final Player player) {
        if (firstPlayer.equals(player)) {
            return player + " : " + TennisPoint.fromGamePoint(firstPlayerPoints);
        }
        return player + " : " + TennisPoint.fromGamePoint(secondPlayerPoints);
    }
}
