package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CardDeckTest {
    private CardDeck cards;
    private Card aceDiamonds;
    private Card kingSpades;

    @BeforeEach
    public void runBefore() {
        cards = new CardDeck();
        aceDiamonds = new Card(Card.Value.Ace, Card.Suit.Diamonds);
        kingSpades = new Card(Card.Value.King, Card.Suit.Spades);
    }

    @Test
    public void testConstructor() {
        assertEquals(0, cards.getNumCards());
        assertEquals(52, cards.getCards().length);
    }

    @Test
    public void testMakeDeck() {
        cards.makeDeck();
        assertEquals(aceDiamonds.getValue(), cards.getCards()[0].getValue());
        assertEquals(aceDiamonds.getSuit(), cards.getCards()[0].getSuit());
        assertEquals(kingSpades.getValue(), cards.getCards()[51].getValue());
        assertEquals(kingSpades.getSuit(), cards.getCards()[51].getSuit());
        assertEquals(52, cards.getNumCards());
    }

    @Test
    public void testShuffleDeck() {
        cards.makeDeck();

        ArrayList<Card.Value> beforeValues = new ArrayList<>();
        for (Card c : cards.getCards()) {
            beforeValues.add(c.getValue());
        }

        cards.shuffleDeck();
        ArrayList<Card.Value> afterValues = new ArrayList<>();
        for (Card c : cards.getCards()) {
            afterValues.add(c.getValue());
        }

        assertFalse(beforeValues == afterValues);
    }

    @Test
    public void testDealCardsOne() {
        cards.makeDeck();
        cards.shuffleDeck();
        assertEquals(1, cards.dealCards(1).length);
    }

    @Test
    public void testDealCardsMultiple() {
        cards.makeDeck();
        cards.shuffleDeck();
        assertEquals(22, cards.dealCards(22).length);
    }

    @Test
    public void testDealCardsMax() {
        cards.makeDeck();
        cards.shuffleDeck();
        assertEquals(52, cards.dealCards(52).length);
    }
}
