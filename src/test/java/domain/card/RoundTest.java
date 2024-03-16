package domain.card;

import domain.Print;
import domain.user.Dealer;
import domain.user.Player;
import org.junit.jupiter.api.Test;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoundTest {

    @Test
    void playRoundOne() {
        //given
        Dealer dealer = new Dealer();
        dealer.addCard(new Card(Symbol.ACE, Type.CLUB));
        dealer.addCard(new Card(Symbol.ACE, Type.CLUB));
        List<Player> playerList = new ArrayList<>();
        Player player = new Player("sayo", 3000);
        player.addCard(new Card(Symbol.ACE, Type.CLUB));
        player.addCard(new Card(Symbol.ACE, Type.HEART));
        playerList.add(player);
        //when
        Print.printOneRoundInfo(dealer, playerList);
        new Round().continuePlayingOrNotAtRoundOne(dealer, playerList);
        //then
        PrintStream mockPrintStream = mock(PrintStream.class);
        verify(mockPrintStream).println(player.getName() + Print.ASK_GIVE_ONE_MORE_CARD);
    }

    @Test
    void playAfterRoundOne() {
        //given
        Dealer dealer = new Dealer();
        dealer.addCard(new Card(Symbol.ACE, Type.CLUB));
        dealer.addCard(new Card(Symbol.ACE, Type.CLUB));
        List<Player> playerList = new ArrayList<>();
        Player player = new Player("sayo", 3000);
        player.addCard(new Card(Symbol.ACE, Type.CLUB));
        player.addCard(new Card(Symbol.ACE, Type.HEART));
        playerList.add(player);
        //when
        Round round = new Round();
        playerList.forEach(round::dealOneCard);
        if (Blackjack.isUnderSixteen(new Deck(dealer.getCards()))) {
            System.out.println(Print.DEALER_GOT_ONE_MORE_CARD_BY_SCORE);
            dealer.addCard(Card.getCard());
        }
        round.continuePlayingOrNotAfterRoundOne(dealer, playerList);
        //then
        PrintStream mockPrintStream = mock(PrintStream.class);
        verify(mockPrintStream).println(player.getName() + Print.ASK_GIVE_ONE_MORE_CARD);
    }

    @Test
    void continuePlayingOrNotAtRoundOne() {

        //when

        //then

    }

    @Test
    void dealOneCard() {
        //given
        Player player = new Player("hiro", 3000);
        player.addCard(new Card(Symbol.TEN, Type.CLUB));
        player.addCard(new Card(Symbol.ACE, Type.HEART));
        //when
        boolean result = Blackjack.isUnderTwenty(new Deck(player.getCards()));
        if (result) {
            player.addCard(Card.getCard());
            Print.printSingleInfo(player);
        }
        //then
        assertEquals(result, false);
    }
}
