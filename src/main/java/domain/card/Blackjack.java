package domain.card;

import domain.Score;

import java.util.List;

public class Blackjack {

    public static boolean isBlackjack(List<Card> cards) {
        return Score.BLACKJACK_POINT == getSumOfScore(cards);
    }

    public static boolean isUnderTwenty(List<Card> cards) {
        return Score.BLACKJACK_POINT > getSumOfScore(cards);
    }

    public static boolean isUnderSixteen(List<Card> cards) {
        return Score.MINIMUM_POINT_FOR_DEALER >= getSumOfScore(cards);
    }

    public static int getSumOfScore(List<Card> cards) {
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

    public static int checkAce(int totalScore) {
        if (totalScore == 0) {
            totalScore += Score.ACE_POINT_ELEVEN;
        } else if (totalScore + Score.ACE_POINT_ONE == Score.BLACKJACK_POINT) {
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
