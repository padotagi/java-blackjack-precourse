package domain.user;

import domain.Score;
import domain.card.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * 게임 참여자를 의미하는 객체
 */
public class Player extends User {
    private String name;
    private double bettingMoney;
    private final List<Card> cards = new ArrayList<>();
    private final Score score = new Score();
    private double prize;

    public Player(String name, double bettingMoney) {
        super(name, bettingMoney);
        this.name = name;
        this.bettingMoney = bettingMoney;
    }

    @Override
    public void addCard(Card card) {
        cards.add(card);
        score.addScore(card.getSymbol());
    }

    @Override
    public void setPrize(double prize) {
        this.prize = prize;
    }

    @Override
    public String getName() {
        return name;
    }

    public double getBettingMoney() {
        return bettingMoney;
    }

    @Override
    public List<Card> getCards() {
        return cards;
    }

    @Override
    public double getPrize() {
        return prize;
    }
}
