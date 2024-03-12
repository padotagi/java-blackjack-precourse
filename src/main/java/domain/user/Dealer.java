package domain.user;

import domain.Score;
import domain.card.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * 게임 딜러를 의미하는 객체
 */
public class Dealer {
    private final List<Card> cards = new ArrayList<>();
    private final Score score = new Score();
    private double prize;

    public Dealer() {}

    public void addCard(Card card) {
        cards.add(card);
        score.addScore(card.getSymbol());
    }

    public void setPrize(double prize) {
        this.prize = prize;
    }

    public List<Card> getCards() {
        return cards;
    }

    public int getScore() {
        return score.getScore();
    }

    public double getPrize() {
        return prize;
    }

}
