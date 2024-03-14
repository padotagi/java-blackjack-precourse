package domain;

import domain.card.Symbol;

public class Score {

    public static final int ACE_POINT_ONE = 1;
    public static final int ACE_POINT_ELEVEN = 11;
    public static final int BLACKJACK_POINT = 21;
    public static final int MINIMUM_POINT_FOR_DEALER = 16;
    private int score = 0;

    public void addScore(Symbol symbol) {
        score += symbol.getScore();
    }

    public int getScore() {
        return score;
    }
}
