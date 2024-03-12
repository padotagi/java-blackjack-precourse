package domain.card;

import camp.nextstep.edu.missionutils.Randoms;

import java.util.List;
import java.util.Objects;

/**
 * 카드 한장을 의미하는 객체
 */
public class Card {
    private final Symbol symbol;
    private final Type type;

    private static final List<Card> cardList = CardFactory.create();

    public Card(Symbol symbol, Type type) {
        this.symbol = symbol;
        this.type = type;
    }

    public static Card getCard() {
        int cardIndex = Randoms.pickNumberInRange(0, cardList.size() - 1);
        return cardList.get(cardIndex);
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public String toStringInKorean() {
        return symbol.getScore() + type.getKorName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return symbol == card.symbol &&
                type == card.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol, type);
    }

    @Override
    public String toString() {
        return "Card{" +
                "symbol=" + symbol +
                ", type=" + type +
                '}';
    }
}
