import domain.card.Card;
import domain.user.Dealer;
import domain.user.Player;

import java.util.ArrayList;
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

        String[] playerNames = application.input.inputName().split(Print.DELIMITER);
        for (String playerName : playerNames) {
            double bettingMoney = application.input.inputMoney(playerName);
            application.playerList.add(new Player(playerName, bettingMoney));
        }
        application.shuffleCards(application.dealer, application.playerList);
        application.playRoundOne(application.dealer, application.playerList);
    }

    public void shuffleCards(Dealer dealer, List<Player> playerList) {
        for (int i = 0; i < Print.NUMBER_OF_CARDS_FOR_ROUND_ONE; i++) {
            dealer.addCard(Card.getCard());
            playerList.forEach(player -> player.addCard(Card.getCard()));
        }
    }

    public void playRoundOne(Dealer dealer, List<Player> playerList) {
        Print.printOneRoundInfo(dealer, playerList);

        boolean blackJack = playerList.stream()
                .anyMatch(player -> result.isBlackJack(player.getCards()));
        if (blackJack) {
            result.getUltimateResult(dealer, playerList);
            result.getUltimateProfitAtRoundOne(dealer, playerList);
        } else {
            playAfterRoundOne(dealer, playerList);
        }
    }

    public void playAfterRoundOne(Dealer dealer, List<Player> playerList) {
        for (Player player : playerList) {
            while (result.isUnderTwenty(player.getCards()) && "Y".equals(input.inputYn(player.getName()))) {
                player.addCard(Card.getCard());
                Print.printSingleInfo(player);
            }
        }
        if (result.isUnderSixteen(dealer.getCards())) {
            System.out.println(Print.DEALER_GOT_ONE_MORE_CARD_BY_SCORE);
            dealer.addCard(Card.getCard());
        }

        boolean isUnderTwenty = playerList.stream()
                .anyMatch(player -> result.isUnderTwenty(player.getCards()));
        if (isUnderTwenty) {
            playAfterRoundOne(dealer, playerList);
        } else {
            result.getUltimateResult(dealer, playerList);
            result.getUltimateProfitAfterRoundOne(dealer, playerList);
        }
    }
}
