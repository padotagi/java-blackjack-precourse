import domain.card.Blackjack;
import domain.card.Card;
import domain.user.Dealer;
import domain.user.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Result {

    List<Player> winnerList = new ArrayList<>();
    List<Player> loserList = new ArrayList<>();

    public void getUltimateProfitAtRoundOne(Dealer dealer, List<Player> playerList) {
        getWinnerAndLoserList(playerList);

        if (Blackjack.isBlackjack(dealer.getCards())) {
            if (winnerList.size() == 0) {
                dealer.setPrize(getSumOfBettingMoney(loserList));
            }
        } else {
            winnerList.forEach(winner -> winner.setPrize(winner.getBettingMoney() * Print.BLACKJACK_PLAYER_PROFIT_FOR_ONE_ROUND));
            dealer.setPrize(Math.negateExact((int) (getSumOfBettingMoney(winnerList) * Print.BLACKJACK_PLAYER_PROFIT_FOR_ONE_ROUND)));
        }
        Print.printUltimateProfitInfo(dealer, playerList);
    }

    public void getUltimateProfitAfterRoundOne(Dealer dealer, List<Player> playerList) {
        getWinnerAndLoserList(playerList);

        double loserBettingMoney = getSumOfBettingMoney(loserList);
        double dividedLoserBettingMoney = winnerList.size() > 0 ? loserBettingMoney / winnerList.size() : loserBettingMoney;
        if (Blackjack.isBlackjack(dealer.getCards())) {
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
            if (Blackjack.isBlackjack(player.getCards())) {
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

    public void getUltimateResult(Dealer dealer, List<Player> playerList) {
        System.out.println("\n" + Print.DEALER + dealer.getCards().stream()
                .map(Card::toStringInKorean)
                .collect(Collectors.joining(Print.DELIMITER)) + Print.SCORE_RESULT + dealer.getScore());
        for (Player player : playerList) {
            System.out.println(player.getName() + Print.CARD + player.getCards().stream()
                    .map(Card::toStringInKorean)
                    .collect(Collectors.joining(Print.DELIMITER)) + Print.SCORE_RESULT + Blackjack.getSumOfScore(player.getCards()));
        }
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
