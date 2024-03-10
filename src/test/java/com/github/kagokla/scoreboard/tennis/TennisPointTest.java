package com.github.kagokla.scoreboard.tennis;

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
        assertThat(TennisPoint.fromGamePoint(0)).isEqualTo(TennisPoint.ZERO);
        assertThat(TennisPoint.fromGamePoint(1)).isEqualTo(TennisPoint.FIFTEEN);
        assertThat(TennisPoint.fromGamePoint(2)).isEqualTo(TennisPoint.THIRTY);
        assertThat(TennisPoint.fromGamePoint(3)).isEqualTo(TennisPoint.FORTY);
        assertThat(TennisPoint.fromGamePoint(4)).isEqualTo(TennisPoint.ADVANTAGE);
        assertThat(TennisPoint.fromGamePoint(Integer.MAX_VALUE)).isEqualTo(TennisPoint.ADVANTAGE);
    }

    @Test
    void shouldThrowExceptionFromNegativeInteger() {
        assertThatIllegalArgumentException().isThrownBy(() -> TennisPoint.fromGamePoint(Integer.MIN_VALUE));
    }
}