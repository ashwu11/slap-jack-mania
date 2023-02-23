package model;

import java.util.ArrayList;
import java.util.Collections;

/**
     * Represents a player in a game with a name, cards in hand, and slap and flip buttons
     **/

public class Player {
    private String name;
    private ArrayList<Card> hand;
    private String slap;
    private String flip;
    private int numCardsLeft;

    public Player(String name, ArrayList<Card> hand, String slap, String flip) {
        this.name = name;
        this.hand = hand;
        this.slap = slap;
        this.flip = flip;
    }

    // MODIFIES: this
    // REQUIRES: player hand is not empty
    // EFFECTS: returns the top card from player's hand (and removes it from hand)
    public Card flipCard() {  // change void to Card
        numCardsLeft -= 1;
        return hand.get(numCardsLeft);
    }

    // MODIFIES: this
    // EFFECTS: adds multiple cards to the player's hand
    public void addCardsToHand(ArrayList<Card> cards) {
        hand.addAll(cards);
        numCardsLeft += cards.size();
        Collections.reverse(hand);
    }


    //EFFECTS: checks if the player had no cards left
    public Boolean checkEmpty() {
        return numCardsLeft == 0;
    }

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
