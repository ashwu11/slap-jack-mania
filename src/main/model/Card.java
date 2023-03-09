package model;

/*
 * Represents a card with a suit and a value
 */

public class Card {
    private final Suit suit;
    private final Value value;

    public enum Suit {
        Diamonds, Clubs, Hearts, Spades
    }

    public enum Value {
        Ace, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King
    }

    //EFFECTS: makes a card with specified value and suit
    public Card(Value value, Suit suit) {
        this.value = value;
        this.suit = suit;
    }

    //EFFECTS: returns a description of the card in the format "value of suit"
    public String getCardName() {
        return value + " of " + suit;
    }

    // getters & setters
    public Suit getSuit() {
        return suit;
    }

    public Value getValue() {
        return value;
    }
}
