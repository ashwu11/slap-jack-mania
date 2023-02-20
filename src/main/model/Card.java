package model;

import java.util.ArrayList;

    /**
     * Represents a card with a suit and value
     **/

public class Card {
    private Suit suit;
    private Value value;

    enum Suit {
        Diamonds, Clubs, Hearts, Spades;

        private Suit[] suits = Suit.values();

        //TODO get a specific suit from the list
        public Suit getSuit(int n) {
            return Diamonds; // stub! suits.get(n);
        }

    }

    enum Value {
        Ace, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King;

        private Value[] values = Value.values();

        //TODO get a specific value from the list
        public Value getValue(int n) {
            return Ace;  // stub!  values.get(n);
        }
    }

    public Card(Value value, Suit suit) {
        this.value = value;
        this.suit = suit;
    }


    // getters
    public Suit getSuit() {
        return suit;
    }

    public Value getValue() {
        return value;
    }

    public String getCard() {
        return value + " of " + suit;
    }
}
