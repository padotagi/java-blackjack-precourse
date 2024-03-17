package domain.user;

import domain.Score;
import domain.card.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * 게임 딜러를 의미하는 객체
 */
public class Dealer extends Player {
    private static final int NUMBER_OF_CARDS_FOR_ROUND_ONE = 2;
    private final List<Card> cards = new ArrayList<>();
    private final Score score = new Score();
    private double prize;

    public Dealer(String name, double bettingMoney) {
        super(name, bettingMoney);
    }

    public List<User> shuffleCards(List<User> users) {
        for (int i = 0; i < NUMBER_OF_CARDS_FOR_ROUND_ONE; i++) {
            users.forEach(player -> player.addCard(Card.getCard()));
        }
        return users;
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
    public List<Card> getCards() {
        return cards;
    }

    public int getScore() {
        return score.getScore();
    }

    @Override
    public double getPrize() {
        return prize;
    }

}
