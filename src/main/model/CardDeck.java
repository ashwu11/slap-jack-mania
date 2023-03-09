package model;

import java.util.Random;

/*
 * Represents a deck of 52 cards. There are four suits: Diamonds, Clubs, Hearts, Spades.
 * Each suit has the following values: Ace, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King
 */

public class CardDeck {
    private final Card[] cards;
    private int numCards;

    //EFFECTS: creates a deck of 52 cards
    public CardDeck() {
        cards = new Card[52];
        numCards = 0;
    }

    // MODIFIES: this
    // EFFECTS: adds appropriate cards to form a full deck
    public void makeDeck() {
        Card.Value[] values = Card.Value.values();

        for (int i = 0; i < 13; i++) {
            Card.Value value = values[i];
            cards[numCards++] = new Card(value, Card.Suit.Diamonds);
            cards[numCards++] = new Card(value, Card.Suit.Clubs);
            cards[numCards++] = new Card(value, Card.Suit.Hearts);
            cards[numCards++] = new Card(value, Card.Suit.Spades);
        }
    }

    //MODIFIES: this
    //EFFECTS: shuffles the deck of cards
    public void shuffleDeck() {
        Random random = new Random();

        for (int i = 0; i < cards.length; i++) {
            int randomNumber = random.nextInt(cards.length - 1);
            Card randomCard = cards[randomNumber];
            cards[randomNumber] = cards[i];
            cards[i] = randomCard;
        }
    }

    //MODIFIES: this
    //REQUIRES: 0 < c < 52
    //EFFECTS: deals the number of cards from the deck
    public Card[] dealCards(int c) {
        Card[] stack = new Card[c];

        for (int i = 0; i < c; i++) {
            numCards--;
            stack[i] = cards[numCards];
        }
        return stack;
    }

    //getters
    public int getNumCards() {
        return this.numCards;
    }

    public Card[] getCards() {
        return this.cards;
    }
}
