package domain.card;

import domain.Result;
import domain.Print;
import domain.Input;
import domain.user.Dealer;
import domain.user.Player;
import domain.user.User;

import java.util.List;

public class Round {

    public void playRoundOne(List<User> users) {
        Print.printOneRoundInfo(users);
        continuePlayingOrNotAtRoundOne(users);
    }

    public void playAfterRoundOne(List<User> users) {
        users.stream()
                .filter(user -> !(user instanceof Dealer))
                .forEach(user -> dealOneCard((Player) user));

        if (Blackjack.isUnderSixteen(new Deck(users.get(0).getCards()))) {
            System.out.println(Print.DEALER_GOT_ONE_MORE_CARD_BY_SCORE);
            users.get(0).addCard(Card.getCard());
        }
        continuePlayingOrNotAfterRoundOne(users);
    }

    public void continuePlayingOrNotAtRoundOne(List<User> users) {
        boolean blackjack = users.stream()
                .anyMatch(user -> Blackjack.isBlackjack(new Deck(user.getCards())));
        if (blackjack) {
            Print.printUltimateResult(users);
            new Result().setUltimateProfitAtRoundOne(users);
        } else {
            playAfterRoundOne(users);
        }
    }

    public void continuePlayingOrNotAfterRoundOne(List<User> users) {
        boolean isUnderTwenty = users.stream()
                .anyMatch(user -> Blackjack.isUnderTwenty(new Deck(user.getCards())));
        if (isUnderTwenty) {
            playAfterRoundOne(users);
        } else {
            Print.printUltimateResult(users);
            new Result().setUltimateProfitAfterRoundOne(users);
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
