package domain.card;

import domain.Score;

public class Blackjack {

    public static boolean isBlackjack(Deck deck) {
        return Score.BLACKJACK_POINT == deck.getSumOfScore();
    }

    public static boolean isUnderTwenty(Deck deck) {
        return Score.BLACKJACK_POINT > deck.getSumOfScore();
    }
    public static boolean isUnderSixteen(Deck deck) {
        return Score.MINIMUM_POINT_FOR_DEALER >= deck.getSumOfScore();
    }
}
