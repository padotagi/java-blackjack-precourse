package domain;

import domain.card.Card;
import domain.card.Deck;
import domain.user.Dealer;
import domain.user.Player;
import domain.user.User;

import java.util.List;
import java.util.stream.Collectors;

public class Print {

    public static final String DELIMITER = ",";
    public static final String TWO_CARDS_TO_EACH = "에게 2장씩 나누었습니다.";
    public static final String DEALER = "딜러";
    public static final String CARD = "카드: ";
    public static final String SCORE_RESULT = " - 결과: ";
    public static final String ULTIMATE_PROFIT = "## 최종 수익";

    public static void printOneRoundInfo(List<User> users) {
        System.out.println("\n" + users.stream()
                .map(User::getName)
                .collect(Collectors.joining(DELIMITER)) + TWO_CARDS_TO_EACH);

        users.forEach(player -> printSingleInfo((Player) player));
    }

    public static void printSingleInfo(Player player) {
        System.out.println(player.getName() + CARD + player.getCards().stream()
                .map(Card::toStringInKorean)
                .collect(Collectors.joining(DELIMITER)));
    }

    public static void printUltimateResult(List<User> users) {
        System.out.println();
        for (User player : users) {
            System.out.println(player.getName() + CARD + player.getCards().stream()
                    .map(Card::toStringInKorean)
                    .collect(Collectors.joining(DELIMITER)) + SCORE_RESULT + new Deck(player.getCards()).getSumOfScore());
        }
    }

    public static void printUltimateProfitInfo(List<User> users) {
        System.out.println("\n" + ULTIMATE_PROFIT);
        System.out.println(DEALER + ": "  + (int) users.get(0).getPrize());
        users.stream()
                .filter(player -> ! (player instanceof Dealer))
                .forEach(player -> System.out.println(player.getName() + ": " + (int) player.getPrize()));

    }
}
