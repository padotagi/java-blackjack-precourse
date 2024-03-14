import domain.card.Blackjack;
import domain.card.Card;
import domain.user.Dealer;
import domain.user.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Application {

    private Dealer dealer;
    private List<Player> playerList;
    private Input input;
    private final Result result = new Result();

    public static void main(String[] args) {
        Application application = new Application();
        application.input = new Input();
        application.dealer = new Dealer();
        application.playerList = new ArrayList<>();

        List<String> playerNames = Arrays.asList(application.input.inputName().split(Print.DELIMITER));
        for (String playerName : playerNames) {
            double bettingMoney = application.input.inputMoney(playerName);
            application.playerList.add(new Player(playerName, bettingMoney));
        }
        application.dealer.shuffleCards(application.dealer, application.playerList);
        application.playRoundOne(application.dealer, application.playerList);
    }

    public void playRoundOne(Dealer dealer, List<Player> playerList) {
        Print.printOneRoundInfo(dealer, playerList);

        boolean blackJack = playerList.stream()
                .anyMatch(player -> Blackjack.isBlackjack(player.getCards()));
        if (blackJack) {
            result.getUltimateResult(dealer, playerList);
            result.getUltimateProfitAtRoundOne(dealer, playerList);
        } else {
            playAfterRoundOne(dealer, playerList);
        }
    }

    public void playAfterRoundOne(Dealer dealer, List<Player> playerList) {
        playerList.forEach(this::dealOneCard);

        if (Blackjack.isUnderSixteen(dealer.getCards())) {
            System.out.println(Print.DEALER_GOT_ONE_MORE_CARD_BY_SCORE);
            dealer.addCard(Card.getCard());
        }

        boolean isUnderTwenty = playerList.stream()
                .anyMatch(player -> Blackjack.isUnderTwenty(player.getCards()));
        if (isUnderTwenty) {
            playAfterRoundOne(dealer, playerList);
        } else {
            result.getUltimateResult(dealer, playerList);
            result.getUltimateProfitAfterRoundOne(dealer, playerList);
        }
    }

    public void dealOneCard(Player player) {
        while (Blackjack.isUnderTwenty(player.getCards()) && "Y".equals(input.inputYn(player.getName()))) {
            player.addCard(Card.getCard());
            Print.printSingleInfo(player);
        }
    }
}
