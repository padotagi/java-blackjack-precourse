package domain;

import domain.card.Symbol;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScoreTest {

    @Test
    void addScore() {
        assertEquals(22, Symbol.ACE.getScore() + Symbol.SEVEN.getScore() + Symbol.FOUR.getScore() + Symbol.JACK.getScore());
    }
}
