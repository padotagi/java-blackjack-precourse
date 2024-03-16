package domain;

import domain.card.Round;
import domain.user.Dealer;
import domain.user.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Application {

    public static void main(String[] args) {
        Input input = new Input();
        Dealer dealer = new Dealer();
        List<Player> playerList = new ArrayList<>();
        Round round = new Round();

        List<String> playerNames = Arrays.asList(input.inputName().split(Print.DELIMITER));
        for (String playerName : playerNames) {
            double bettingMoney = input.inputMoney(playerName);
            playerList.add(new Player(playerName, bettingMoney));
        }
        dealer.shuffleCards(dealer, playerList);
        round.playRoundOne(dealer, playerList);
    }
}
