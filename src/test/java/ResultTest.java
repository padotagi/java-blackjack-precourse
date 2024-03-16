import domain.card.*;
import domain.user.Dealer;
import domain.user.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ResultTest {

    @Test
    void 라운드1_딜러_블랙잭() {
        //given
        Dealer dealer = new Dealer();
        dealer.addCard(new Card(Symbol.ACE, Type.HEART));
        dealer.addCard(new Card(Symbol.TEN, Type.HEART));

        List<Player> playerList = new ArrayList<>();
        playerList.add(new Player("pobi", 10000));
        playerList.add(new Player("jason", 20000));
        for (int i = 0; i < 2; i++) {
            playerList.forEach(player -> player.addCard(Card.getCard()));
        }

        //when
        boolean isBlackjack = Blackjack.isBlackjack(new Deck(dealer.getCards()));

        //then
        assertTrue(isBlackjack);
    }

    @Test
    void 예시로_1라운드_이후_블랙잭_수익계산() {
        //given
        Dealer dealer = new Dealer();
        List<Player> playerList = new ArrayList<>();
        Player pobi = new Player("pobi", 10000);
        Player jason = new Player("jason", 20000);

        dealer.addCard(new Card(Symbol.THREE, Type.DIAMOND));
        dealer.addCard(new Card(Symbol.NINE, Type.CLUB));
        dealer.addCard(new Card(Symbol.EIGHT, Type.DIAMOND));

        pobi.addCard(new Card(Symbol.TWO, Type.HEART));
        pobi.addCard(new Card(Symbol.EIGHT, Type.SPADE));
        pobi.addCard(new Card(Symbol.ACE, Type.CLUB));
        jason.addCard(new Card(Symbol.SEVEN, Type.CLUB));
        jason.addCard(new Card(Symbol.KING, Type.SPADE));

        playerList.add(pobi);
        playerList.add(jason);

        //when
        List<Player> winnerList = new ArrayList<>();
        List<Player> loserList = new ArrayList<>();
        for (Player player : playerList) {
            if (Blackjack.isBlackjack(new Deck(player.getCards()))) {
                winnerList.add(player);
            } else {
                loserList.add(player);
            }
        }

        double winnerBettingMoney = winnerList.stream().mapToDouble(Player::getBettingMoney).sum();
        double loserBettingMoney = loserList.stream().mapToDouble(Player::getBettingMoney).sum();
        double dividedLoserBettingMoney = loserBettingMoney / winnerList.size();
        if (Blackjack.isBlackjack(new Deck(dealer.getCards()))) {
            if (winnerList.size() == 0) {
                dealer.setPrize(loserBettingMoney);
            } else {
                for (Player winner : winnerList) {
                    playerList.stream()
                            .filter(player -> player.getName().equals(winner.getName()))
                            .forEach(player -> player.setPrize(dividedLoserBettingMoney));
                }
                for (Player loser : loserList) {
                    playerList.stream()
                            .filter(player -> player.getName().equals(loser.getName()))
                            .forEach(player -> player.setPrize(Math.negateExact((int) dividedLoserBettingMoney)));
                }
            }
        } else {
            for (Player winner : winnerList) {
                playerList.stream()
                        .filter(player -> player.getName().equals(winner.getName()))
                        .forEach(player -> player.setPrize(dividedLoserBettingMoney));
            }
            for (Player loser : loserList) {
                playerList.stream()
                        .filter(player -> player.getName().equals(loser.getName()))
                        .forEach(player -> player.setPrize(Math.negateExact((int) dividedLoserBettingMoney)));
            }
            dealer.setPrize(Math.negateExact((int) winnerBettingMoney));
        }

        //then
        Assertions.assertEquals((int) pobi.getPrize(), 20000);
        Assertions.assertEquals((int) jason.getPrize(), -20000);
    }
}
