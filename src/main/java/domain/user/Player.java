package domain.user;

import domain.Score;
import domain.card.Card;
import domain.card.Deck;

import java.util.ArrayList;
import java.util.List;

/**
 * 게임 참여자를 의미하는 객체
 */
public class Player {
    private final String name;
    private final double bettingMoney;
    private final List<Card> cards = new ArrayList<>();
    private final Score score = new Score();
    private double prize;

    public Player(String name, double bettingMoney) {
        this.name = name;
        this.bettingMoney = bettingMoney;
    }

    public void addCard(Card card) {
        cards.add(card);
        score.addScore(card.getSymbol());
    }

    public void setPrize(double prize) {
        this.prize = prize;
    }

    public String getName() {
        return name;
    }

    public double getBettingMoney() {
        return bettingMoney;
    }

    public List<Card> getCards() {
        return cards;
    }

    public double getPrize() {
        return prize;
    }
}
