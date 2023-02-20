package ui;

import model.Card;
import model.CardDeck;

import java.util.ArrayList;

public class Game {
    private int cardCount; // keeping track of the count of cards
    private int currentPlayer;
    private ArrayList<String> players;
    private CardDeck cardDeck;
    private ArrayList<ArrayList<Card>> playerDeck; // each player has a diff pile of cards
    private ArrayList<Card> pilePlayed;
}



    // TODO method that increments the next playCount
        // use .get(index) to help?