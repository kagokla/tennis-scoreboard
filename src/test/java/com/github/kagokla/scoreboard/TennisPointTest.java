package com.github.kagokla.scoreboard;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class TennisPointTest {

    @Test
    void shouldReturnStringRepresentation() {
        assertThat(TennisPoint.ZERO).hasToString("0");
        assertThat(TennisPoint.FIFTEEN).hasToString("15");
        assertThat(TennisPoint.THIRTY).hasToString("30");
        assertThat(TennisPoint.FORTY).hasToString("40");
        assertThat(TennisPoint.ADVANTAGE).hasToString("Advantage");
    }

    @Test
    void shouldReturnEnumFromInteger() {
        assertThat(TennisPoint.fromWonPoint(0)).isEqualTo(TennisPoint.ZERO);
        assertThat(TennisPoint.fromWonPoint(1)).isEqualTo(TennisPoint.FIFTEEN);
        assertThat(TennisPoint.fromWonPoint(2)).isEqualTo(TennisPoint.THIRTY);
        assertThat(TennisPoint.fromWonPoint(3)).isEqualTo(TennisPoint.FORTY);
        assertThat(TennisPoint.fromWonPoint(4)).isEqualTo(TennisPoint.ADVANTAGE);
        assertThat(TennisPoint.fromWonPoint(Integer.MAX_VALUE)).isEqualTo(TennisPoint.ADVANTAGE);
    }

    @Test
    void shouldThrowExceptionFromNegativeInteger() {
        assertThatIllegalArgumentException().isThrownBy(() -> TennisPoint.fromWonPoint(Integer.MIN_VALUE));
    }
}