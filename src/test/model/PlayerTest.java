package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    private Player p1;
    private Player p2;
    private Card aceHearts;
    private Card twoSpades;
    private Card threeDiamonds;
    private Card fourClubs;

    @BeforeEach
    public void runBefore() {
        aceHearts = new Card(Card.Value.Ace, Card.Suit.Hearts);
        twoSpades = new Card(Card.Value.Two, Card.Suit.Spades);
        threeDiamonds = new Card(Card.Value.Three, Card.Suit.Diamonds);
        fourClubs = new Card(Card.Value.Four, Card.Suit.Clubs);
        ArrayList<Card> p1hand = new ArrayList<>();
        p1hand.add(aceHearts);
        p1hand.add(twoSpades);

        ArrayList<Card> p2hand = new ArrayList<>();

        p1 = new Player("player1", p1hand, "x", "c");
        p2 = new Player("player2", p2hand, "b", "n");
    }

    @Test
    public void testConstructor() {
        assertEquals("player1", p1.getName());
        assertEquals(2, p1.getHand().size());
        assertEquals("x", p1.getSlapKey());
        assertEquals("c", p1.getFlipKey());
        assertEquals(2, p1.getNumCardsLeft());

        assertEquals("player2", p2.getName());
        assertEquals(0, p2.getHand().size());
        assertEquals("b", p2.getSlapKey());
        assertEquals("n", p2.getFlipKey());
        assertEquals(0, p2.getNumCardsLeft());
    }

    @Test
    public void testFlipCard() {
        assertEquals(twoSpades, p1.flipCard());
        assertEquals(1, p1.getNumCardsLeft());
    }

    @Test
    public void testAddCardsToHand() {
        ArrayList<Card> cardsToAdd = new ArrayList<>();
        cardsToAdd.add(threeDiamonds);
        cardsToAdd.add(fourClubs);

        p2.addCardsToHand(cardsToAdd);
        assertEquals(2, p2.getHand().size());
        assertEquals(2, p2.getNumCardsLeft());
        assertEquals(threeDiamonds, p2.flipCard());

        p1.addCardsToHand(cardsToAdd);
        assertEquals(4, p1.getHand().size());
        assertEquals(4, p1.getNumCardsLeft());
        assertEquals(aceHearts, p1.flipCard());
    }

    @Test
    public void testCheckEmpty() {
        assertTrue(p2.checkEmpty());
        assertFalse(p1.checkEmpty());
    }
}
