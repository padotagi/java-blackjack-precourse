package domain.card;

import domain.Result;
import domain.Print;
import domain.Input;
import domain.user.Dealer;
import domain.user.Player;

import java.util.List;

public class Round {

    public void playRoundOne(Dealer dealer, List<Player> playerList) {
        Print.printOneRoundInfo(dealer, playerList);
        continuePlayingOrNotAtRoundOne(dealer, playerList);
    }

    public void playAfterRoundOne(Dealer dealer, List<Player> playerList) {
        playerList.forEach(this::dealOneCard);

        if (Blackjack.isUnderSixteen(new Deck(dealer.getCards()))) {
            System.out.println(Print.DEALER_GOT_ONE_MORE_CARD_BY_SCORE);
            dealer.addCard(Card.getCard());
        }
        continuePlayingOrNotAfterRoundOne(dealer, playerList);
    }

    public void continuePlayingOrNotAtRoundOne(Dealer dealer, List<Player> playerList) {
        boolean blackjack = playerList.stream()
                .anyMatch(player -> Blackjack.isBlackjack(new Deck(player.getCards())));
        if (blackjack) {
            Print.printUltimateResult(dealer, playerList);
            new Result().setUltimateProfitAtRoundOne(dealer, playerList);
        } else {
            playAfterRoundOne(dealer, playerList);
        }
    }

    public void continuePlayingOrNotAfterRoundOne(Dealer dealer, List<Player> playerList) {
        boolean isUnderTwenty = playerList.stream()
                .anyMatch(player -> Blackjack.isUnderTwenty(new Deck(player.getCards())));
        if (isUnderTwenty) {
            playAfterRoundOne(dealer, playerList);
        } else {
            Print.printUltimateResult(dealer, playerList);
            new Result().setUltimateProfitAfterRoundOne(dealer, playerList);
        }
    }

    public void dealOneCard(Player player) {
        Input input = new Input();
        while (Blackjack.isUnderTwenty(new Deck(player.getCards())) && "Y".equals(input.inputYn(player.getName()))) {
            player.addCard(Card.getCard());
            Print.printSingleInfo(player);
        }
    }
}
