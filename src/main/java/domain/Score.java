package domain;

import domain.card.Card;
import domain.card.Symbol;

public class Score {

    private int score = 0;
    public static final int ACE_POINT_ONE = 1;
    public static final int ACE_POINT_ELEVEN = 11;
    public static final int BLACKJACK_POINT = 21;
    public static final int MINIMUM_POINT_FOR_DEALER = 16;
    private static final double MULTIPLES_OF_BETTING_MONEY_FOR_WINNING_ROUND_ONE = 1.5;

    public void addScore(Symbol symbol) {
        score += symbol.getScore();
    }

    public int getScore() {
        return score;
    }
}
