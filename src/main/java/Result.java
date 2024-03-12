import domain.Score;
import domain.card.Card;
import domain.user.Dealer;
import domain.user.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Result {

    public void getUltimateProfitAtRoundOne(Dealer dealer, List<Player> playerList) {
        List<Player> winnerList = new ArrayList<>();
        List<Player> loserList = new ArrayList<>();
        for (Player player : playerList) {
            if (isBlackJack(player.getCards())) {
                winnerList.add(player);
            } else {
                loserList.add(player);
            }
        }
        double winnerBettingMoney = winnerList.stream().mapToDouble(Player::getBettingMoney).sum();
        double loserBettingMoney = loserList.stream().mapToDouble(Player::getBettingMoney).sum();
        if (isBlackJack(dealer.getCards())) {
            if (winnerList.size() == 0) {
                dealer.setPrize(loserBettingMoney);
            }
        } else {
            winnerList.forEach(winner -> winner.setPrize(winner.getBettingMoney() * Print.BLACKJACK_PLAYER_PROFIT_FOR_ONE_ROUND));
            dealer.setPrize(Math.negateExact((int) (winnerBettingMoney * Print.BLACKJACK_PLAYER_PROFIT_FOR_ONE_ROUND)));
        }
        Print.printUltimateProfitInfo(dealer, playerList);
    }

    public void getUltimateProfitAfterRoundOne(Dealer dealer, List<Player> playerList) {
        List<Player> winnerList = new ArrayList<>();
        List<Player> loserList = new ArrayList<>();
        for (Player player : playerList) {
            if (isBlackJack(player.getCards())) {
                winnerList.add(player);
            } else {
                loserList.add(player);
            }
        }
        double winnerBettingMoney = winnerList.stream().mapToDouble(Player::getBettingMoney).sum();
        double loserBettingMoney = loserList.stream().mapToDouble(Player::getBettingMoney).sum();
        if (isBlackJack(dealer.getCards())) {
            if (winnerList.size() == 0) {
                dealer.setPrize(loserBettingMoney);
            } else {
                loserBettingMoney /= winnerList.size();
                double finalLoserBettingMoney = loserBettingMoney;
                winnerList.forEach(winner -> winner.setPrize(finalLoserBettingMoney));
            }
        } else {
            loserBettingMoney /= winnerList.size();
            double finalLoserBettingMoney = loserBettingMoney;
            winnerList.forEach(winner -> winner.setPrize(finalLoserBettingMoney));
            dealer.setPrize(Math.negateExact((int) winnerBettingMoney));
        }
        Print.printUltimateProfitInfo(dealer, playerList);
    }

    public void getUltimateResult(Dealer dealer, List<Player> playerList) {
        System.out.println("\n" + Print.DEALER + dealer.getCards().stream()
                .map(Card::toStringInKorean)
                .collect(Collectors.joining(",")) + Print.SCORE_RESULT + dealer.getScore());
        for (Player player : playerList) {
            System.out.println(player.getName() + Print.CARD + player.getCards().stream()
                    .map(Card::toStringInKorean)
                    .collect(Collectors.joining(",")) + Print.SCORE_RESULT + getSum(player.getCards()));
        }
    }

    public boolean isBlackJack(List<Card> cards) {
        return Score.BLACKJACK_POINT == getSum(cards);
    }

    public boolean isUnderTwenty(List<Card> cards) {
        return Score.BLACKJACK_POINT > getSum(cards);
    }

    public boolean isUnderSixteen(List<Card> cards) {
        return Score.MINIMUM_POINT_FOR_DEALER >= getSum(cards);
    }

    public int getSum(List<Card> cards) {
        int totalScore = 0;
        for (Card card : cards) {
            if (!"ACE".equals(card.getSymbol().name())) {
                totalScore += card.getSymbol().getScore();
            } else {
                totalScore = checkAce(totalScore);
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
