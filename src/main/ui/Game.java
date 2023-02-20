package ui;

import model.Card;
import model.CardDeck;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private Card.Value cardCount; // keeping track of the count of cards
    private int currentTurn; // keeps track of whose turn it is
    private ArrayList<String> playerNames;
    private ArrayList<ArrayList<Card>> playerDecks; // each player has a diff list of cards
    private ArrayList<Card> pilePlayed;
    private CardDeck cardDeck;
    private Scanner input;
    private Boolean run;
    private static final String PLAY_COMMAND = "play";

    //EFFECTS: starts a game by asking for player names
    public Game() {
        runGame();
    }

    public void runGame() {
        cardDeck = new CardDeck();
        pilePlayed = new ArrayList<>();
        playerNames = new ArrayList<>();
        currentTurn = 0;
        cardCount = Card.Value.Ace;
        playerDecks = new ArrayList<ArrayList<Card>>();
        input = new Scanner(System.in);
        run = true;

        cardDeck.shuffleDeck();
        handleInput();

        // for each player,
        // make a new arraylist of cards
        // fill it using dealCards, pass in an int of (52 / playerNames.size())
        // add it to playerDecks by using .add()
        // TODO Q: but dealCards returns a list of cards, so how can I fill it?
    }


    //EFFECTS: handles user input until user quits
    public void handleInput() {
        while (run) {
            printInstructions();
        }
        System.out.println("Get ready to play!");
    }

    //EFFECTS: prints instructions to start the game
    private void printInstructions() {
        System.out.println("\nPlease enter player name:\n");
        System.out.println("When you are ready to play, enter '" + PLAY_COMMAND + "'.");
        String str;
        str = input.nextLine();

        if (str.equals(PLAY_COMMAND)) {
            run = false;
        } else {
            playerNames.add(str);
            printPlayers();
            printInstructions();
        }
    }

    //EFFECTS: prints out players entered to play
    private void printPlayers() {
        System.out.println("\nCurrent players:\n");
        for (String p : playerNames) {
            System.out.println(p);
        }
    }


}

    // TODO method that increments the next playCount
        // use .get(index) to help?
    // TODO method that increments cardCount