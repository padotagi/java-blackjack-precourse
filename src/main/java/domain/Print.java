package domain;

import domain.card.Card;
import domain.card.Deck;
import domain.user.Dealer;
import domain.user.Player;

import java.util.List;
import java.util.stream.Collectors;

public class Print {

    public static final String INPUT_NAME = "게임에 참여할 사람의 이름을 입력하세요.(쉼표 기준으로 분리)";
    public static final String DELIMITER = ",";
    public static final String INPUT_NAME_ERROR = "[ERROR] 공백/여백은 이름으로 사용할 수 없습니다.";
    public static final String INPUT_MONEY = "의 배팅 금액은?";
    public static final String INPUT_MONEY_ERROR = "[ERROR] 1000의 배수로 금액을 입력하세요.";
    public static final String TO_DEALER = "딜러와";
    public static final String TWO_CARDS_TO_EACH = "에게 2장씩 나누었습니다.";
    public static final String DEALER = "딜러: ";
    public static final String CARD = "카드: ";
    public static final String ASK_GIVE_ONE_MORE_CARD = "는 한장의 카드를 더 받겠습니까?(예는 y, 아니오는 n)";
    public static final String ASK_GIVE_ONE_MORE_CARD_ERROR = "[ERROR] y 혹은 n을 입력하세요.(대/소문자 구분 없음)";
    public static final String DEALER_GOT_ONE_MORE_CARD_BY_SCORE = "딜러는 16이하라 한장의 카드를 더 받았습니다.";
    public static final String SCORE_RESULT = " - 결과: ";
    public static final String ULTIMATE_PROFIT = "## 최종 수익";
    public static final double BLACKJACK_PLAYER_PROFIT_FOR_ONE_ROUND = 1.5;

    public static void printOneRoundInfo(Dealer dealer, List<Player> playerList) {
        System.out.println("\n" + TO_DEALER + playerList.stream()
                .map(Player::getName)
                .collect(Collectors.joining(DELIMITER)) + TWO_CARDS_TO_EACH);
        System.out.println(DEALER + dealer.getCards().stream()
                .map(Card::toStringInKorean)
                .collect(Collectors.joining(DELIMITER)));
        for (Player player : playerList) {
            System.out.println(player.getName() + CARD + player.getCards().stream()
                    .map(Card::toStringInKorean)
                    .collect(Collectors.joining(DELIMITER)));
        }
    }

    public static void printSingleInfo(Player player) {
        System.out.println(player.getName() + CARD + player.getCards().stream()
                .map(Card::toStringInKorean)
                .collect(Collectors.joining(DELIMITER)));
    }

    public static void printUltimateResult(Dealer dealer, List<Player> playerList) {
        System.out.println("\n" + Print.DEALER + dealer.getCards().stream()
                .map(Card::toStringInKorean)
                .collect(Collectors.joining(Print.DELIMITER)) + Print.SCORE_RESULT + dealer.getScore());
        for (Player player : playerList) {
            System.out.println(player.getName() + Print.CARD + player.getCards().stream()
                    .map(Card::toStringInKorean)
                    .collect(Collectors.joining(Print.DELIMITER)) + Print.SCORE_RESULT + new Deck(player.getCards()).getSumOfScore());
        }
    }

    public static void printUltimateProfitInfo(Dealer dealer, List<Player> playerList) {
        System.out.println("\n" + ULTIMATE_PROFIT);
        System.out.println(DEALER + (int) dealer.getPrize());
        for (Player player : playerList) {
            System.out.println(player.getName() + ": " + (int) player.getPrize());
        }
    }
}
