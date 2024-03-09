package com.github.kagokla.scoreboard;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class TennisGameTest {

    private Player server;
    private Player receiver;
    private TennisGame game;

    @BeforeEach
    public void setUp() {
        server = new Player("Server");
        receiver = new Player("Receiver");
        game = new TennisGame(server, receiver);
    }

    @AfterEach
    public void tearDown() {
        server = null;
        receiver = null;
        game = null;
    }

    @Test
    void shouldNotWinPointWhenPlayerUnknown() {
        // Given
        final var unknownPlayer = new Player("unknown");

        // Then
        assertThatIllegalArgumentException().isThrownBy(() -> game.wonGamePoint(unknownPlayer));
    }

    @Test
    void shouldBeServerZeroReceiverZeroWhenGameStart() {
        // When
        final var prettyScore = game.toPrettyScore();

        // Then
        final var expectedScore = toExpectedScore("0", "0");
        assertThat(prettyScore).isNotBlank().isEqualTo(expectedScore);
        assertThereIsNoWinner();
    }

    @Test
    void shouldBeServerFifteenReceiverZeroWhenServerWinFirstGamePoint() {
        // Given
        setGamePoints(1, 0);
        // When
        final var prettyScore = game.toPrettyScore();

        // Then
        final var expectedScore = toExpectedScore("15", "0");
        assertThat(prettyScore).isNotBlank().isEqualTo(expectedScore);
        assertThereIsNoWinner();
    }

    @Test
    void shouldBeServerZeroReceiverFifteenWhenReceiverWinFirstGamePoint() {
        // Given
        setGamePoints(0, 1);
        // When
        final var prettyScore = game.toPrettyScore();

        // Then
        final var expectedScore = toExpectedScore("0", "15");
        assertThat(prettyScore).isNotBlank().isEqualTo(expectedScore);
        assertThereIsNoWinner();
    }

    @Test
    void shouldBeServerFifteenReceiverFifteenWhenServerWin1PointAndReceiverWin1Point() {
        // Given
        setGamePoints(1, 1);
        // When
        final var prettyScore = game.toPrettyScore();

        // Then
        final var expectedScore = toExpectedScore("15", "15");
        assertThat(prettyScore).isNotBlank().isEqualTo(expectedScore);
        assertThereIsNoWinner();
    }

    @Test
    void shouldBeServerFifteenReceiverFortyWhenServerWin1PointAndReceiverWin3Point() {
        // Given
        setGamePoints(1, 3);
        // When
        final var prettyScore = game.toPrettyScore();

        // Then
        final var expectedScore = toExpectedScore("15", "40");
        assertThat(prettyScore).isNotBlank().isEqualTo(expectedScore);
        assertThereIsNoWinner();
    }

    private void assertThereIsNoWinner() {
        assertThat(game.hasWinner()).isFalse();
        assertThat(game.getWinner()).isNull();
    }

    private void setGamePoints(final int serverGamePoints, final int receiverGamePoints) {
        for (var i = 0; i < serverGamePoints; i++) {
            game.wonGamePoint(server);
        }
        for (var i = 0; i < receiverGamePoints; i++) {
            game.wonGamePoint(receiver);
        }
    }

    private String toExpectedScore(final String serverPrettyGamePoints, final String receiverPrettyGamePoints) {
        return server + " : " + serverPrettyGamePoints + " / " + receiver + " : " + receiverPrettyGamePoints;
    }
}