package domain.user;

import domain.Score;
import domain.card.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * 게임 딜러를 의미하는 객체
 */
public class Dealer {
    private static final int NUMBER_OF_CARDS_FOR_ROUND_ONE = 2;
    private final List<Card> cards = new ArrayList<>();
    private final Score score = new Score();
    private double prize;

    public Dealer() {}

    public void shuffleCards(Dealer dealer, List<Player> playerList) {
        for (int i = 0; i < NUMBER_OF_CARDS_FOR_ROUND_ONE; i++) {
            dealer.addCard(Card.getCard());
            playerList.forEach(player -> player.addCard(Card.getCard()));
        }
    }

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
