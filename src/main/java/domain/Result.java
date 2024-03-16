package domain;

import domain.card.Blackjack;
import domain.card.Deck;
import domain.user.Dealer;
import domain.user.Player;

import java.util.ArrayList;
import java.util.List;

public class Result {

    List<Player> winnerList = new ArrayList<>();
    List<Player> loserList = new ArrayList<>();

    public void setUltimateProfitAtRoundOne(Dealer dealer, List<Player> playerList) {
        getWinnerAndLoserList(playerList);

        if (Blackjack.isBlackjack(new Deck(dealer.getCards()))) {
            if (winnerList.size() == 0) {
                dealer.setPrize(getSumOfBettingMoney(loserList));
            }
        } else {
            winnerList.forEach(winner -> winner.setPrize(winner.getBettingMoney() * Print.BLACKJACK_PLAYER_PROFIT_FOR_ONE_ROUND));
            dealer.setPrize(Math.negateExact((int) (getSumOfBettingMoney(winnerList) * Print.BLACKJACK_PLAYER_PROFIT_FOR_ONE_ROUND)));
        }
        Print.printUltimateProfitInfo(dealer, playerList);
    }

    public void setUltimateProfitAfterRoundOne(Dealer dealer, List<Player> playerList) {
        getWinnerAndLoserList(playerList);

        double loserBettingMoney = getSumOfBettingMoney(loserList);
        double dividedLoserBettingMoney = winnerList.size() > 0 ? loserBettingMoney / winnerList.size() : loserBettingMoney;
        if (Blackjack.isBlackjack(new Deck(dealer.getCards()))) {
            if (winnerList.size() == 0) {
                dealer.setPrize(loserBettingMoney);
            } else {
                copyPrize(playerList, dividedLoserBettingMoney);
            }
        } else {
            copyPrize(playerList, dividedLoserBettingMoney);
            dealer.setPrize(Math.negateExact((int) getSumOfBettingMoney(winnerList)));
        }
        Print.printUltimateProfitInfo(dealer, playerList);
    }

    public void getWinnerAndLoserList(List<Player> playerList) {
        for (Player player : playerList) {
            if (Blackjack.isBlackjack(new Deck(player.getCards()))) {
                winnerList.add(player);
            } else {
                loserList.add(player);
            }
        }
    }

    public double getSumOfBettingMoney(List<Player> playerList) {
        return playerList.stream()
                .mapToDouble(Player::getBettingMoney).sum();
    }

    public void copyPrize(List<Player> playerList, double prize) {
        for (Player winner : winnerList) {
            playerList.stream()
                    .filter(player -> player.getName().equals(winner.getName()))
                    .forEach(player -> player.setPrize(prize));
        }
        for (Player loser : loserList) {
            playerList.stream()
                    .filter(player -> player.getName().equals(loser.getName()))
                    .forEach(player -> player.setPrize(Math.negateExact((int) prize)));
        }
    }
}
