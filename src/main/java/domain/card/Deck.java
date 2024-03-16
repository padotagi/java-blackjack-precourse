package domain.card;

import domain.Score;

import java.util.ArrayList;
import java.util.List;

public class Deck {

    private List<Card> deck;

    public Deck(List<Card> deck) {
        this.deck = deck;
    }

    public int getSumOfScore() {
        List<Card> someCards = new ArrayList<>();
        return deck.stream()
                .map(card -> {
                    if ("ACE".equals(card.getSymbol().name())) {
                        return getAceScore(someCards);
                    } else {
                        someCards.add(card);
                        return card.getSymbol().getScore();
                    }
                })
                .mapToInt(score -> score)
                .sum();
    }

    public int getAceScore(List<Card> someCards) {
        int currentScore = someCards.stream()
                .mapToInt(card -> card.getSymbol().getScore())
                .sum();
        if (currentScore == 0) {
            return Score.ACE_POINT_ELEVEN;
        } else if (currentScore + Score.ACE_POINT_ONE == Score.BLACKJACK_POINT) {
            return Score.ACE_POINT_ONE;
        } else if (currentScore + Score.ACE_POINT_ELEVEN == Score.BLACKJACK_POINT) {
            return Score.ACE_POINT_ELEVEN;
        } else if (currentScore + Score.ACE_POINT_ELEVEN < Score.BLACKJACK_POINT) {
            return Score.ACE_POINT_ELEVEN;
        } else {
            return Score.ACE_POINT_ONE;
        }
    }
}
