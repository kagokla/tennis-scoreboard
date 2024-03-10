package com.github.kagokla.scoreboard;

import com.github.kagokla.scoreboard.tennis.TennisGame;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;
import java.util.Scanner;
import java.util.function.Predicate;

public class Main {

    private static final String INVALID_INPUT = "The entry you have made is not valid.";
    private static final String MANDATORY_NAME = "Player's name is mandatory.";

    public static void main(String[] args) {
        final var banner =
                """
                 ____                     _                         _\s
                / ___|  ___ ___  _ __ ___| |__   ___   __ _ _ __ __| |
                \\___ \\ / __/ _ \\| '__/ _ \\ '_ \\ / _ \\ / _` | '__/ _` |
                 ___) | (_| (_) | | |  __/ |_) | (_) | (_| | | | (_| |
                |____/ \\___\\___/|_|  \\___|_.__/ \\___/ \\__,_|_|  \\__,_|
                """;
        System.out.println(banner);

        final var scan = new Scanner(System.in);
        final Predicate<String> validInputPredicate = StringUtils::isNotBlank;

        final var firstPlayer = getValidInput(
                        scan, validInputPredicate, "Enter the name of the 1st player: ", MANDATORY_NAME)
                .map(Player::new)
                .orElseThrow(() -> new IllegalArgumentException(INVALID_INPUT));
        final var secondPlayer = getValidInput(
                        scan, validInputPredicate, "Enter the name of the 2nd player: ", MANDATORY_NAME)
                .map(Player::new)
                .orElseThrow(() -> new IllegalArgumentException(INVALID_INPUT));

        final var game = new TennisGame(firstPlayer, secondPlayer);
        do {
            final var scorer = getValidInput(
                            scan,
                            validInputPredicate,
                            "Which player won the point? ",
                            "Please choose between " + firstPlayer + " and " + secondPlayer)
                    .map(Player::new)
                    .orElseThrow(() -> new IllegalArgumentException(INVALID_INPUT));
            game.wonGamePoint(scorer);
            System.out.println(game.toPrettyScore());
        } while (!game.hasWinner());

        System.out.println("Goodbye!");
    }

    private static Optional<String> getValidInput(
            final Scanner scan,
            final Predicate<String> predicate,
            final String promptMsg,
            final String invalidInputMsg) {
        for (var i = 0; i < 10; i++) {
            System.out.println(promptMsg);
            final var input = scan.nextLine();
            if (predicate.test(input)) {
                return Optional.of(input);
            }
            System.out.println(invalidInputMsg);
        }
        return Optional.empty();
    }
}
