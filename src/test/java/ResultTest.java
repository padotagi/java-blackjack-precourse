import domain.card.Card;
import domain.card.Symbol;
import domain.card.Type;
import domain.user.Dealer;
import domain.user.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ResultTest {

    @Test
    void 라운드1_딜러_블랙잭() {
        //given
        Dealer dealer = new Dealer();
        dealer.addCard(new Card(Symbol.THREE, Type.HEART));
        dealer.addCard(new Card(Symbol.EIGHT, Type.HEART));
        dealer.addCard(new Card(Symbol.TEN, Type.HEART));

        List<Player> playerList = new ArrayList<>();
        playerList.add(new Player("pobi", 1000));
        playerList.add(new Player("jason", 2000));
        for (int i = 0; i < Print.NUMBER_OF_CARDS_FOR_ROUND_ONE; i++) {
            playerList.forEach(player -> player.addCard(Card.getCard()));
        }

        //when
        boolean isBlackJack = new Result().isBlackJack(dealer.getCards());

        //then
        assertTrue(isBlackJack);
    }
}
