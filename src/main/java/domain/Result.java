package domain;

import domain.card.Blackjack;
import domain.card.Deck;
import domain.user.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Result {

    List<Winner> winners = new ArrayList<>();
    List<Loser> losers = new ArrayList<>();

    public void setUltimateProfitAtRoundOne(List<User> users) {
        getWinnerAndLosers(users);
        if (Blackjack.isBlackjack(new Deck(users.get(0).getCards()))) {
            if (winners.size() == 0) {
                users.get(0).setPrize(getSumOfBettingMoney(losers.stream()
                        .map(loser -> (Player) loser)
                        .collect(Collectors.toList())));
            }
        } else {
            winners.forEach(winner -> winner.setPrize(winner.getBettingMoney() * Print.BLACKJACK_PLAYER_PROFIT_FOR_ONE_ROUND));
            users.get(0).setPrize(Math.negateExact((int) (getSumOfBettingMoney(winners.stream()
                    .map(winner -> (Player) winner)
                    .collect(Collectors.toList())
            ) * Print.BLACKJACK_PLAYER_PROFIT_FOR_ONE_ROUND)));
        }
        Print.printUltimateProfitInfo(users);
    }

    public void setUltimateProfitAfterRoundOne(List<User> users) {
        getWinnerAndLosers(users);
        double loserBettingMoney = getSumOfBettingMoney(losers.stream()
                .map(loser -> (Player) loser)
                .collect(Collectors.toList()));
        double dividedLoserBettingMoney = winners.size() > 0 ? loserBettingMoney / winners.size() : loserBettingMoney;
        if (Blackjack.isBlackjack(new Deck(users.get(0).getCards()))) {
            if (winners.size() == 0) {
                users.get(0).setPrize(loserBettingMoney);
            } else {
                copyPrize(users, dividedLoserBettingMoney);
            }
        } else {
            copyPrize(users, dividedLoserBettingMoney);
            users.get(0).setPrize(Math.negateExact((int) getSumOfBettingMoney(winners.stream()
                    .map(winner -> (Player) winner)
                    .collect(Collectors.toList()))));
        }
        Print.printUltimateProfitInfo(users);
    }

    public void getWinnerAndLosers(List<User> users) {
        List<Player> players = users.stream()
                .filter(user -> !(user instanceof Dealer))
                .map(user -> (Player) user)
                .collect(Collectors.toList());

        for (Player player : players) {
            if (Blackjack.isBlackjack(new Deck(player.getCards()))) {
                winners.add((Winner) player);
            } else {
                losers.add((Loser) player);
            }
        }
    }

    public double getSumOfBettingMoney(List<Player> players) {
        return players.stream()
                .mapToDouble(Player::getBettingMoney).sum();
    }

    public void copyPrize(List<User> users, double prize) {
        for (Winner winner : winners) {
            users.stream()
                    .filter(user -> user.getName().equals(winner.getName()))
                    .forEach(user -> user.setPrize(prize));
        }
        for (Loser loser : losers) {
            users.stream()
                    .filter(user -> user.getName().equals(loser.getName()))
                    .forEach(user -> user.setPrize(Math.negateExact((int) prize)));
        }
    }
}
