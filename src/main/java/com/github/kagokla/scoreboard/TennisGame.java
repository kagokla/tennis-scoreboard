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
        return toPrettyPoint(firstPlayer, firstPlayerPoints) + " / " + toPrettyPoint(secondPlayer, secondPlayerPoints);
    }

    @Override
    public boolean isDraw() {
        return firstPlayerPoints >= 3 && firstPlayerPoints == secondPlayerPoints;
    }

    @Override
    public boolean hasWinner() {
        return (firstPlayerPoints >= 4 && firstPlayerPoints - secondPlayerPoints >= 2) || (secondPlayerPoints >= 4 && secondPlayerPoints - firstPlayerPoints >= 2);
    }

    private void saveWinner() {
        winner = firstPlayerPoints > secondPlayerPoints ? firstPlayer : secondPlayer;
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

    private String toPrettyPoint(final Player player, final int playerPoints) {
        return player + " : " + TennisPoint.fromGamePoint(playerPoints);
    }
}
