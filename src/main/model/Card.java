package model;

    /**
     * Represents a card with a suit and value
     **/

public class Card {
    private Suit suit;
    private Value value;

    public enum Suit {
        Diamonds, Clubs, Hearts, Spades;
    }

    public enum Value {
        Ace, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King;
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
