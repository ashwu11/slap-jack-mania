package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CardTest {
    private Card aceHearts;
    private Card twoSpades;

    @BeforeEach
    public void runBefore() {
        aceHearts = new Card(Card.Value.Ace, Card.Suit.Hearts);
        twoSpades = new Card(Card.Value.Two, Card.Suit.Spades);
    }

    @Test
    public void testConstructor() {
        assertEquals(Card.Value.Ace, aceHearts.getValue());
        assertEquals(Card.Suit.Hearts, aceHearts.getSuit());

        assertEquals(Card.Value.Two, twoSpades.getValue());
        assertEquals(Card.Suit.Spades, twoSpades.getSuit());
    }

    @Test
    public void testGetCard() {
        assertEquals("Ace of Hearts", aceHearts.getCard());
        assertEquals("Two of Spades", twoSpades.getCard());
    }

}