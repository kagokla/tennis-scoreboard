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
        server = new Player("server");
        receiver = new Player("receiver");
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
        assertThatIllegalArgumentException().isThrownBy(() -> game.wonPoint(unknownPlayer));
    }

    @Test
    void shouldBeServerZeroReceiverZeroWhenGameStart() {
        // When
        final var prettyScore = game.toPrettyScore();

        // Then
        assertThat(prettyScore).isNotBlank().isEqualTo("Player A : 0 / Player B : 0");
    }

    @Test
    void shouldBeServerFifteenReceiverZeroWhenServerWinFirstPoint() {
        // Given
        setWonPoints(1, 0);
        // When
        final var prettyScore = game.toPrettyScore();

        // Then
        assertThat(prettyScore).isNotBlank().isEqualTo("Player A : 15 / Player B : 0");
    }

    @Test
    void shouldBeServerZeroReceiverFifteenWhenReceiverWinFirstPoint() {
        // Given
        setWonPoints(0, 1);
        // When
        final var prettyScore = game.toPrettyScore();

        // Then
        assertThat(prettyScore).isNotBlank().isEqualTo("Player A : 0 / Player B : 15");
    }

    private void setWonPoints(final int serverPoints, final int receiverPoints) {
        for (var i = 0; i < serverPoints; i++) {
            game.wonPoint(server);
        }
        for (var i = 0; i < receiverPoints; i++) {
            game.wonPoint(receiver);
        }
    }
}