package model;

import java.util.ArrayList;
import java.util.Collections;

/*
 * Represents a player in the game
 */

public class Player {
    private final String name;
    private final ArrayList<Card> hand;
    private final String slap;
    private final String flip;
    private int numCardsLeft;

    //EFFECTS: constructs a player with a name, their hand of cards, and slap and flip controls
    public Player(String name, ArrayList<Card> hand, String slap, String flip) {
        this.name = name;
        this.hand = hand;
        this.slap = slap;
        this.flip = flip;
        numCardsLeft = this.hand.size();
    }

    // REQUIRES: player hand is not empty
    // MODIFIES: this
    // EFFECTS: returns the top card from player's hand (and removes it from hand)
    public Card flipCard() {
        numCardsLeft -= 1;
        return hand.get(numCardsLeft);
    }

    // REQUIRES: 0 < cards.size()
    // MODIFIES: this
    // EFFECTS: adds multiple cards to the player's hand
    public void addCardsToHand(ArrayList<Card> cards) {
        hand.addAll(cards);
        numCardsLeft += cards.size();
        Collections.reverse(hand);
    }

    //EFFECTS: checks if the player has no cards left
    public Boolean checkEmpty() {
        return numCardsLeft <= 0;
    }

    //getters & setters
    public void setNumCardsLeft(int n) {
        numCardsLeft = n;
    }

    public int getNumCardsLeft() {
        return numCardsLeft;
    }

    public String getSlapKey() {
        return this.slap;
    }

    public String getFlipKey() {
        return this.flip;
    }

    public ArrayList<Card> getHand() {
        return this.hand;
    }

    public String getName() {
        return this.name;
    }
}
