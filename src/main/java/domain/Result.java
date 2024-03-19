package domain;

import domain.card.Blackjack;
import domain.card.Deck;
import domain.user.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Result {

    public static final double BLACKJACK_PLAYER_PROFIT_FOR_ONE_ROUND = 1.5;

    public void setUltimateProfitAtRoundOne(List<User> users) {
        Map<String, List<Player>> resultMap = getWinnerAndLosers(users);
        List<Player> winners = resultMap.get("winners");
        List<Player> losers = resultMap.get("losers");
        if (Blackjack.isBlackjack(new Deck(users.get(0).getCards()))) {
            if (winners.size() == 0) {
                users.get(0).setPrize(getSumOfBettingMoney(losers));
            }
        } else {
            winners.forEach(winner -> winner.setPrize(winner.getBettingMoney() * BLACKJACK_PLAYER_PROFIT_FOR_ONE_ROUND));
            users.get(0).setPrize(Math.negateExact((int) (getSumOfBettingMoney(winners) * BLACKJACK_PLAYER_PROFIT_FOR_ONE_ROUND)));
        }
        Print.printUltimateProfitInfo(users);
    }

    public void setUltimateProfitAfterRoundOne(List<User> users) {
        Map<String, List<Player>> resultMap = getWinnerAndLosers(users);
        List<Player> winners = resultMap.get("winners");
        List<Player> losers = resultMap.get("losers");
        double loserBettingMoney = getSumOfBettingMoney(losers);
        double dividedLoserBettingMoney = winners.size() > 0 ? loserBettingMoney / winners.size() : loserBettingMoney;
        if (Blackjack.isBlackjack(new Deck(users.get(0).getCards()))) {
            if (winners.size() == 0) {
                users.get(0).setPrize(loserBettingMoney);
            } else {
                copyPrize(users, dividedLoserBettingMoney, resultMap);
            }
        } else {
            copyPrize(users, dividedLoserBettingMoney, resultMap);
            users.get(0).setPrize(Math.negateExact((int) getSumOfBettingMoney(winners)));
        }
        Print.printUltimateProfitInfo(users);
    }

    public Map<String, List<Player>> getWinnerAndLosers(List<User> users) {
        Map<String, List<Player>> playerResult = new HashMap<>();
        List<Player> winners = new ArrayList<>();
        List<Player> losers = new ArrayList<>();

        List<Player> players = users.stream()
                .filter(user -> !(user instanceof Dealer))
                .map(user -> (Player) user)
                .collect(Collectors.toList());
        for (Player player : players) {
            if (Blackjack.isBlackjack(new Deck(player.getCards()))) {
                winners.add(player);
            } else {
                losers.add(player);
            }
        }
        playerResult.put("winners", winners);
        playerResult.put("losers", losers);
        return playerResult;
    }

    public double getSumOfBettingMoney(List<Player> players) {
        return players.stream()
                .mapToDouble(Player::getBettingMoney).sum();
    }

    public List<User> copyPrize(List<User> users, double prize, Map<String, List<Player>> playerResult) {
        for (Player winner : playerResult.get("winners")) {
            users.stream()
                    .filter(user -> user.getName().equals(winner.getName()))
                    .forEach(user -> user.setPrize(prize));
        }
        for (Player loser : playerResult.get("losers")) {
            users.stream()
                    .filter(user -> user.getName().equals(loser.getName()))
                    .forEach(user -> user.setPrize(Math.negateExact((int) prize)));
        }
        return users;
    }
}
