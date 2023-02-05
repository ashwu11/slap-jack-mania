package model;

import java.util.ArrayList;
import model.Card;

    /**
     * Represents a deck of 52. There are four suits: diamonds, clubs, hearts, spades.
     * There are four of each card: Ace, 2, 3, 4, 5, 6, 7, 8, 9, 10, Jack, Queen, King
     **/

public class CardDeck {

    private ArrayList<Card> cards;

    public CardDeck() {
        cards = new ArrayList<Card>(52);
    }

}
