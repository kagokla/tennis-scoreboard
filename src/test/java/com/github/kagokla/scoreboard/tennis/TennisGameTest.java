package com.github.kagokla.scoreboard.tennis;

import com.github.kagokla.scoreboard.Player;
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
        assertThereIsNoWinnerAndLeaderIs(server);
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
        assertThereIsNoWinnerAndLeaderIs(server);
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
        assertThereIsNoWinnerAndLeaderIs(receiver);
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
        assertThereIsNoWinnerAndLeaderIs(server);
    }

    @Test
    void shouldBeServerFifteenReceiverFortyWhenServerWin1PointAndReceiverWin3Points() {
        // Given
        setGamePoints(1, 3);
        // When
        final var prettyScore = game.toPrettyScore();

        // Then
        final var expectedScore = toExpectedScore("15", "40");
        assertThat(prettyScore).isNotBlank().isEqualTo(expectedScore);
        assertThereIsNoWinnerAndLeaderIs(receiver);
    }

    @Test
    void shouldBeReceiverVictoryWhenReceiverWin4Points() {
        // Given
        setGamePoints(1, 4);
        // When
        final var prettyScore = game.toPrettyScore();

        // Then
        final var expectedScore = toWinnerScore(receiver);
        assertThat(prettyScore).isNotBlank().isEqualTo(expectedScore);
        assertThereIsWinner(receiver);
    }

    @Test
    void shouldBeServerVictoryWhenServerWin4Points() {
        // Given
        setGamePoints(4, 0);
        // When
        final var prettyScore = game.toPrettyScore();

        // Then
        final var expectedScore = toWinnerScore(server);
        assertThat(prettyScore).isNotBlank().isEqualTo(expectedScore);
        assertThereIsWinner(server);
    }

    @Test
    void shouldBeDeuceWhenServerWin3PointsAndReceiverWin3Points() {
        // Given
        setGamePoints(3, 3);
        // When
        final var prettyScore = game.toPrettyScore();

        // Then
        final var expectedScore = toDeuceScore();
        assertThat(prettyScore).isNotBlank().isEqualTo(expectedScore);
        assertThereIsNoWinnerAndLeaderIs(server);
    }

    @Test
    void shouldBeServerAdvantageWhenServerWin5PointsAndReceiverWin4Points() {
        // Given
        setGamePoints(5, 4);
        // When
        final var prettyScore = game.toPrettyScore();

        // Then
        final var expectedScore = toAdvantageScore(server);
        assertThat(prettyScore).isNotBlank().isEqualTo(expectedScore);
        assertThereIsNoWinnerAndLeaderIs(server);
    }

    @Test
    void shouldBeReceiverAdvantageWhenReceiverWin6PointsAndServerWin5Points() {
        // Given
        setGamePoints(5, 6);
        // When
        final var prettyScore = game.toPrettyScore();

        // Then
        final var expectedScore = toAdvantageScore(receiver);
        assertThat(prettyScore).isNotBlank().isEqualTo(expectedScore);
        assertThereIsNoWinnerAndLeaderIs(receiver);
    }

    @Test
    void shouldBeServerVictoryWhenServerWinPointAfterAdvantage() {
        // Given
        shouldBeServerAdvantageWhenServerWin5PointsAndReceiverWin4Points();
        setGamePoints(1, 0);
        // When
        final var prettyScore = game.toPrettyScore();

        // Then
        final var expectedScore = toWinnerScore(server);
        assertThat(prettyScore).isNotBlank().isEqualTo(expectedScore);
        assertThereIsWinner(server);
    }

    @Test
    void shouldBeReceiverVictoryWhenReceiverWinPointAfterAdvantage() {
        // Given
        shouldBeReceiverAdvantageWhenReceiverWin6PointsAndServerWin5Points();
        setGamePoints(0, 1);
        // When
        final var prettyScore = game.toPrettyScore();

        // Then
        final var expectedScore = toWinnerScore(receiver);
        assertThat(prettyScore).isNotBlank().isEqualTo(expectedScore);
        assertThereIsWinner(receiver);
    }

    @Test
    void simulateGameWithoutDeuceNorAdvantage() {
        game.wonGamePoint(server);
        assertThat(game.toPrettyScore()).isNotBlank().isEqualTo(toExpectedScore("15", "0"));

        game.wonGamePoint(receiver);
        assertThat(game.toPrettyScore()).isNotBlank().isEqualTo(toExpectedScore("15", "15"));

        game.wonGamePoint(server);
        assertThat(game.toPrettyScore()).isNotBlank().isEqualTo(toExpectedScore("30", "15"));

        game.wonGamePoint(receiver);
        assertThat(game.toPrettyScore()).isNotBlank().isEqualTo(toExpectedScore("30", "30"));

        game.wonGamePoint(server);
        assertThat(game.toPrettyScore()).isNotBlank().isEqualTo(toExpectedScore("40", "30"));

        game.wonGamePoint(server);
        assertThat(game.toPrettyScore()).isNotBlank().isEqualTo(toWinnerScore(server));
        assertThereIsWinner(server);
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

    private String toWinnerScore(final Player player) {
        return player + " wins the game";
    }

    private String toDeuceScore() {
        return "Deuce";
    }

    private String toAdvantageScore(final Player player) {
        return player + " : Advantage";
    }

    private void assertThereIsWinner(final Player winner) {
        assertThat(game.hasWinner()).isTrue();
        assertThat(game.getLeader()).isEqualTo(winner);
    }

    private void assertThereIsNoWinnerAndLeaderIs(final Player leader) {
        assertThat(game.hasWinner()).isFalse();
        assertThat(game.getLeader()).isEqualTo(leader);
    }
}