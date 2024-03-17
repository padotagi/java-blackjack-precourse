package domain;

import domain.card.Round;
import domain.user.Dealer;
import domain.user.Player;
import domain.user.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Application {

    public static void main(String[] args) {
        Input input = new Input();
        List<User> users = new ArrayList<>();
        Dealer dealer = new Dealer(Print.DEALER, 0);
        users.add(dealer);
        Round round = new Round();

        List<String> playerNames = Arrays.asList(input.inputName().split(Print.DELIMITER));
        for (String playerName : playerNames) {
            double bettingMoney = input.inputMoney(playerName);
            users.add(new Player(playerName, bettingMoney));
        }
        round.playRoundOne(dealer.shuffleCards(users));
    }
}
