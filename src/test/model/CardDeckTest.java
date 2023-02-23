package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertEquals(52, cards.getNumCards());
    }

    @Test
    public void testMakeDeck() {
        assertEquals(aceDiamonds.getValue(), cards.getCards()[0].getValue());
        assertEquals(aceDiamonds.getSuit(), cards.getCards()[0].getSuit());
        assertEquals(kingSpades.getValue(), cards.getCards()[51].getValue());
        assertEquals(kingSpades.getSuit(), cards.getCards()[51].getSuit());
    }

    @Test
    public void testShuffleDeck() {

    }
}
