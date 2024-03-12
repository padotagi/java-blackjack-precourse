import domain.Score;
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

        if (isBlackJack(dealer.getCards())) {
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
        double dividedLoserBettingMoney = loserBettingMoney / winnerList.size();
        if (isBlackJack(dealer.getCards())) {
            if (winnerList.size() == 0) {
                dealer.setPrize(loserBettingMoney);
            } else {
                winnerList.forEach(winner -> winner.setPrize(dividedLoserBettingMoney));
            }
        } else {
            winnerList.forEach(winner -> winner.setPrize(dividedLoserBettingMoney));
            dealer.setPrize(Math.negateExact((int) getSumOfBettingMoney(winnerList)));
        }
        Print.printUltimateProfitInfo(dealer, playerList);
    }

    public void getWinnerAndLoserList(List<Player> playerList) {
        for (Player player : playerList) {
            if (isBlackJack(player.getCards())) {
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
                    .collect(Collectors.joining(Print.DELIMITER)) + Print.SCORE_RESULT + getSumOfScore(player.getCards()));
        }
    }

    public boolean isBlackJack(List<Card> cards) {
        return Score.BLACKJACK_POINT == getSumOfScore(cards);
    }

    public boolean isUnderTwenty(List<Card> cards) {
        return Score.BLACKJACK_POINT > getSumOfScore(cards);
    }

    public boolean isUnderSixteen(List<Card> cards) {
        return Score.MINIMUM_POINT_FOR_DEALER >= getSumOfScore(cards);
    }

    public int getSumOfScore(List<Card> cards) {
        int totalScore = 0;
        for (Card card : cards) {
            if ("ACE".equals(card.getSymbol().name())) {
                totalScore = checkAce(totalScore);
            } else {
                totalScore += card.getSymbol().getScore();
            }
        }
        return totalScore;
    }

    public int checkAce(int totalScore) {
        if (totalScore + Score.ACE_POINT_ONE == Score.BLACKJACK_POINT) {
            totalScore += Score.ACE_POINT_ONE;
        } else if (totalScore + Score.ACE_POINT_ELEVEN == Score.BLACKJACK_POINT) {
            totalScore += Score.ACE_POINT_ELEVEN;
        } else if (totalScore + Score.ACE_POINT_ONE > totalScore + Score.ACE_POINT_ELEVEN) {
            totalScore += Score.ACE_POINT_ELEVEN;
        } else {
            totalScore += Score.ACE_POINT_ONE;
        }
        return totalScore;
    }
}
