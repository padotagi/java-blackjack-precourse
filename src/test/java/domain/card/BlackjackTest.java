package domain.card;

import domain.Score;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BlackjackTest {

    @Test
    void isUnderTwenty() {
        //given
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Symbol.TEN, Type.HEART));
        cards.add(new Card(Symbol.EIGHT, Type.CLUB));
        cards.add(new Card(Symbol.TWO, Type.SPADE));
        //when
        boolean result = Score.BLACKJACK_POINT > new Deck(cards).getSumOfScore();
        //then
        assertTrue(result);
    }
}
