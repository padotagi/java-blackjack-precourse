package domain.card;

import domain.Score;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    @Test
    void getSumOfScore() {
        //given
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Symbol.ACE, Type.SPADE));
        cards.add(new Card(Symbol.TEN, Type.HEART));
        cards.add(new Card(Symbol.EIGHT, Type.CLUB));
        Deck deck = new Deck(cards);
        //when
        List<Card> someCards = new ArrayList<>();
        int result = cards.stream()
                .map(card -> {
                    if ("ACE".equals(card.getSymbol().name())) {
                        return deck.getAceScore(someCards);
                    } else {
                        someCards.add(card);
                        return card.getSymbol().getScore();
                    }
                })
                .mapToInt(score -> score)
                .sum();
        //then
        assertEquals(result, 29);
    }

    @Test
    void getAceScore() {
        //given
        List<Integer> expectedList = new ArrayList<>();
        expectedList.add(11);//expectedList.add(11);
        expectedList.add(11);//expectedList.add(12);
        expectedList.add(11);//expectedList.add(13);
        expectedList.add(11);//expectedList.add(14);
        expectedList.add(11);//expectedList.add(15);
        expectedList.add(11);//expectedList.add(16);
        expectedList.add(11);//expectedList.add(17);
        expectedList.add(11);//expectedList.add(18);
        expectedList.add(11);//expectedList.add(19);
        expectedList.add(11);//expectedList.add(20);
        expectedList.add(11);//expectedList.add(21);
        expectedList.add(1);//expectedList.add(12);
        expectedList.add(1);//expectedList.add(13);
        expectedList.add(1);//expectedList.add(14);
        expectedList.add(1);//expectedList.add(15);
        expectedList.add(1);//expectedList.add(16);
        expectedList.add(1);//expectedList.add(17);
        expectedList.add(1);//expectedList.add(18);
        expectedList.add(1);//expectedList.add(19);
        expectedList.add(1);//expectedList.add(20);
        expectedList.add(1);//expectedList.add(21);
        expectedList.add(1);//expectedList.add(22);
        //when
        List<Integer> totalScores = new ArrayList<>();
        int aceScore = 0;
        for (int currentScore = 0; currentScore <= Score.BLACKJACK_POINT; currentScore++) {
            if (currentScore == 0) {
                aceScore = Score.ACE_POINT_ELEVEN;
            } else if (currentScore + Score.ACE_POINT_ONE == Score.BLACKJACK_POINT) {
                aceScore = Score.ACE_POINT_ONE;
            } else if (currentScore + Score.ACE_POINT_ELEVEN == Score.BLACKJACK_POINT) {
                aceScore = Score.ACE_POINT_ELEVEN;
            } else if (currentScore + Score.ACE_POINT_ELEVEN < Score.BLACKJACK_POINT) {
                aceScore = Score.ACE_POINT_ELEVEN;
            } else {
                aceScore = Score.ACE_POINT_ONE;
            }
            totalScores.add(aceScore);
        }
        //then
        assertArrayEquals(expectedList.toArray(), totalScores.toArray());
    }
}
