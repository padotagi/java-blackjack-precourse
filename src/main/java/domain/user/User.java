package domain.user;

import domain.Score;
import domain.card.Card;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String name;
    private double bettingMoney;
    private List<Card> cards = new ArrayList<>();
    private Score score = new Score();
    private double prize;

    public User(String name, double bettingMoney) {
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

    public List<Card> getCards() {
        return cards;
    }

    public double getPrize() {
        return prize;
    }
}
