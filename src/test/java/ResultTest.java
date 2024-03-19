import domain.Print;
import domain.Result;
import domain.card.*;
import domain.user.Dealer;
import domain.user.Player;
import domain.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class ResultTest {

    @Test
    void 라운드1_딜러_블랙잭_최종수익() {
        //given
        Dealer dealer = new Dealer(Print.DEALER, 1000);
        dealer.addCard(new Card(Symbol.ACE, Type.HEART));
        dealer.addCard(new Card(Symbol.TEN, Type.HEART));

        List<Player> players = new ArrayList<>();
        players.add(new Player("pobi", 10000));
        players.add(new Player("jason", 20000));
        for (int i = 0; i < 2; i++) {
            players.forEach(player -> player.addCard(Card.getCard()));
        }

        //when
        if (Blackjack.isBlackjack(new Deck(dealer.getCards()))) {
            //then
            double result = new Result().getSumOfBettingMoney(players);
            assertEquals(30000, result);
        }
    }

    @Test
    void 라운드1_참여자_블랙잭_최종수익() {
        //given
        List<User> users = new ArrayList<>();
        List<Player> winners = new ArrayList<>();
        Player player1 = new Player("a", 13000);
        Player player2 = new Player("b", 27000);
        player1.addCard(new Card(Symbol.TEN, Type.DIAMOND));
        player1.addCard(new Card(Symbol.JACK, Type.CLUB));
        player2.addCard(new Card(Symbol.ACE, Type.HEART));
        player2.addCard(new Card(Symbol.KING, Type.SPADE));
        users.add(player1);
        users.add(player2);
        winners.add(player2);
        //when
        Result result = new Result();
        winners.forEach(winner -> winner.setPrize(winner.getBettingMoney() * result.BLACKJACK_PLAYER_PROFIT_FOR_ONE_ROUND));

        Print.printUltimateProfitInfo(users);
        //then
        assertEquals(27000 * result.BLACKJACK_PLAYER_PROFIT_FOR_ONE_ROUND, winners.get(0).getPrize());
    }

    @Test
    void 예시로_1라운드_이후_블랙잭_수익계산() {
        //given
        Dealer dealer = new Dealer(Print.DEALER, 1000);
        List<Player> players = new ArrayList<>();
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

        players.add(pobi);
        players.add(jason);

        //when
        List<Player> winnerList = new ArrayList<>();
        List<Player> loserList = new ArrayList<>();
        for (Player player : players) {
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
                    players.stream()
                            .filter(player -> player.getName().equals(winner.getName()))
                            .forEach(player -> player.setPrize(dividedLoserBettingMoney));
                }
                for (Player loser : loserList) {
                    players.stream()
                            .filter(player -> player.getName().equals(loser.getName()))
                            .forEach(player -> player.setPrize(Math.negateExact((int) dividedLoserBettingMoney)));
                }
            }
        } else {
            for (Player winner : winnerList) {
                players.stream()
                        .filter(player -> player.getName().equals(winner.getName()))
                        .forEach(player -> player.setPrize(dividedLoserBettingMoney));
            }
            for (Player loser : loserList) {
                players.stream()
                        .filter(player -> player.getName().equals(loser.getName()))
                        .forEach(player -> player.setPrize(Math.negateExact((int) dividedLoserBettingMoney)));
            }
            dealer.setPrize(Math.negateExact((int) winnerBettingMoney));
        }

        //then
        Assertions.assertEquals((int) pobi.getPrize(), 20000);
        Assertions.assertEquals((int) jason.getPrize(), -20000);
    }

    @Test
    void getWinnerAndLosers() {
        //given
        List<User> users = new ArrayList<>();
        Dealer dealer = new Dealer(Print.DEALER, 0);
        Player player1 = new Player("a", 1000);
        Player player2 = new Player("b", 2000);
        users.add(dealer);
        users.add(player1);
        users.add(player2);
        player1.addCard(new Card(Symbol.ACE, Type.DIAMOND));
        player1.addCard(new Card(Symbol.THREE, Type.CLUB));
        player2.addCard(new Card(Symbol.ACE, Type.HEART));
        player2.addCard(new Card(Symbol.KING, Type.SPADE));
        //when
        List<Player> players = users.stream()
                .filter(user -> !(user instanceof Dealer))
                .map(user -> (Player) user)
                .collect(Collectors.toList());
        //when
        List<Player> winners = new ArrayList<>();
        List<Player> losers = new ArrayList<>();
        for (Player player : players) {
            if (Blackjack.isBlackjack(new Deck(player.getCards()))) {
                winners.add(player);
            } else {
                losers.add(player);
            }
        }
        //then
        assertFalse(players.contains(dealer));
        //then
        assertEquals(winners.get(0), player2);
        assertEquals(losers.get(0), player1);
    }

    @Test
    void getSumOfBettingMoney() {
        List<Player> players = new ArrayList<>();
        players.add(new Player("p1", 6000));
        players.add(new Player("p2", 7000));
        double expectedResult = 13000;
        double result = players.stream()
                .mapToDouble(Player::getBettingMoney).sum();
        assertEquals(expectedResult, result);
    }

    @Test
    void copyPrize() {
        //given
        double prize = 30000;
        List<User> users = new ArrayList<>();
        Dealer dealer = new Dealer(Print.DEALER, 0);
        Player player1 = new Player("p1", 1000);
        Player player2 = new Player("p2", 2000);
        player1.addCard(new Card(Symbol.ACE, Type.DIAMOND));
        player1.addCard(new Card(Symbol.THREE, Type.CLUB));
        player2.addCard(new Card(Symbol.ACE, Type.HEART));
        player2.addCard(new Card(Symbol.KING, Type.SPADE));
        users.add(dealer);
        users.add(player1);
        users.add(player2);

        Map<String, List<Player>> playerResult = new Result().getWinnerAndLosers(users);
        List<Player> winners = playerResult.get("winners");
        List<Player> losers = playerResult.get("losers");

        //when
        for (Player winner : winners) {
            users.stream()
                    .filter(user -> user.getName().equals(winner.getName()))
                    .forEach(user -> user.setPrize(prize));
        }
        for (Player loser : losers) {
            users.stream()
                    .filter(user -> user.getName().equals(loser.getName()))
                    .forEach(user -> user.setPrize(Math.negateExact((int) prize)));
        }
        //then
        assertEquals(winners.get(0), player2);
        assertEquals(losers.get(0), player1);
    }
}
