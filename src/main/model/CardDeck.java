package model;

import java.util.ArrayList;
import java.util.Random;

import model.Card;

    /**
     * Represents a deck of 52 cards. There are four suits: diamonds, clubs, hearts, spades.
     * There are four of each card: Ace, 2, 3, 4, 5, 6, 7, 8, 9, 10, Jack, Queen, King
     **/

public class CardDeck {
    private Card[] cards;

    //EFFECTS: creates an array of 52 cards
    public CardDeck() {
        cards = new Card[52];
    }

    // MODIFIES: this
    // EFFECTS: adds appropriate cards to form a full deck
    public void makeDeck() {
        Card.Value[] values = Card.Value.values();
        int count = 0;

        for (int i = 0; i < 13; i++) {
            Card.Value value = values[i];
            cards[count++] = new Card(value, Card.Suit.Diamonds);
            cards[count++] = new Card(value, Card.Suit.Clubs);
            cards[count++] = new Card(value, Card.Suit.Hearts);
            cards[count++] = new Card(value, Card.Suit.Spades);
        }
    }

    //MODIFIES: this
    //EFFECTS: shuffles the deck of cards
    public void shuffleDeck() {
        Random random = new Random();

        for (int i = 0; i < cards.length; i++) {
            int randomNumber = i + random.nextInt(cards.length - 1);
            Card randomCard = cards[randomNumber]; // get a card at a random index value
            cards[randomNumber] = cards[i];       // swap the index of the card we got with the card we're at
            cards[i] = randomCard;               // set the actual card to the random card we got earlier
        }
    }

    //REQUIRES: 0 < c < 52
    public Card[] dealCards(int c) {  // c is number of cards a player gets, depends on the number of players playing
        Card[] stack = new Card[c];

        for (int i = 0; i < c; i++) {
            stack[i] = cards[i]; // TODO Q: does this deal from the bottom of the deck...?
        }

        return stack;
    }
}
