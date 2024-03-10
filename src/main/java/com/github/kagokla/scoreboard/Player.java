package com.github.kagokla.scoreboard;

import java.util.Objects;

/**
 * Player's information
 *
 * @param name player's name
 */
public record Player(String name) {
    public Player {
        Objects.requireNonNull(name);
    }

    @Override
    public String toString() {
        return "Player " + name;
    }
}
