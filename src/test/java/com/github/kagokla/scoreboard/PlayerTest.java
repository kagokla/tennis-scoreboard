package com.github.kagokla.scoreboard;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

class PlayerTest {

    @Test
    void shouldCreateNewPlayer() {
        final var player = new Player("Koko");

        assertThat(player).isNotNull();
    }

    @Test
    void shouldFailWhenNameMissing() {
        assertThatNullPointerException().isThrownBy(() -> new Player(null));
    }

    @Test
    void shouldReturnStringRepresentation() {
        final var player = new Player("Yannick NOAH");

        assertThat(player).isNotNull().hasToString("Player Yannick NOAH");
    }
}
