package ui;

import model.Card;
import model.CardDeck;
import model.Leaderboard;
import model.Player;

import java.util.*;

public class Game {
    private Card.Value cardCount; // keeping track of the count of cards
    private int cardCountInt;
    private int currentTurn; // keeps track of whose turn it is
    private ArrayList<String> playerNames;
    private ArrayList<Player> players;
    private ArrayList<ArrayList<Card>> playerDecks; // each player has a diff list of cards
    private ArrayList<Card> cardsPlayed;
    private ArrayList<String> instructions = new ArrayList<>();
    private CardDeck cardDeck;
    private Scanner input;
    private Boolean start;
    private Boolean run;
    private Boolean end;
    private String winner;
    private ArrayList<String> validKeys;
    private static final String PLAY_COMMAND = "play";
    private static final String QUIT_COMMAND = "quit";
    private static final String SAVE_COMMAND = "save";
    private static final String VIEW_COMMAND = "view";
    private static final String REMOVE_COMMAND = "remove";


    public Game() {
        cardDeck = new CardDeck();
        cardsPlayed = new ArrayList<>();
        playerNames = new ArrayList<>();
        players = new ArrayList<>();
        currentTurn = 0;
        cardCount = Card.Value.Ace;
        cardCountInt = 0;
        playerDecks = new ArrayList<ArrayList<Card>>();
        input = new Scanner(System.in);
        start = true;
        run = true;
        end = true;

        runGame();
    }

    public void runGame() {
        String key;
        cardDeck.shuffleDeck();
        handleInputStart();
        initializeAndDeal();

        while (run) {
            System.out.println("Current Turn: " + playerNames.get(currentTurn) + "\n");
            printCardsPlayed();
            key = input.nextLine();
            key = key.toLowerCase();

            handleInputGame(key);
            updateCardCount();
            updateCurrentTurn();
        }

        System.out.println("The winner is " + winner + "! Good game.");
        System.out.println("\nEnter " + SAVE_COMMAND + " to save the game.");

        while (end) {
            instructionsAfterGame();
            key = input.nextLine();
            key = key.toLowerCase();
            handleInputAfter(key);
        }
    }

        //  TODO Q: would it be better to store the rules in another class? or keep it in a method?


    public void handleInputAfter(String word) {

        if (word.equals(SAVE_COMMAND)) {
            for (Player p : players) {
                Boolean exists = Leaderboard.checkExisting(p.getName());
                if (exists) {
                    updateAccounts(p);
                } else {
                    Leaderboard.registerAccount(p.getName());
                    updateAccounts(p);
                }
            }

        } else if (word.equals(VIEW_COMMAND)) {
            Leaderboard.printAllAccounts();
            System.out.println("Enter " + REMOVE_COMMAND + " to remove an account");
        }  else if (word.equals(REMOVE_COMMAND)) {
            handleRemove();
        } else if (word.equals(PLAY_COMMAND)) {
            new Game();
        } else if (word.equals(QUIT_COMMAND)) {
            System.out.println("\nThanks for playing :)");
            end = false;
        } else {
            System.out.println("Invalid command!");
        }
    }

    private void handleRemove() {
        System.out.println("Enter the account username you wish to remove: ");
        String in;
        in = input.nextLine();
        Leaderboard.removeAccount(in);
    }

    private void updateAccounts(Player p) {
        if (p.getName().equals(winner)) {
            Leaderboard.updateAccount(p.getName(), true);
        } else {
            Leaderboard.updateAccount(p.getName(), false);
        }
    }

    private void instructionsAfterGame() {
        System.out.println("Enter " + VIEW_COMMAND + " to see the Leaderboard.");
        System.out.println("Enter " + PLAY_COMMAND + " to play again.");
        System.out.println("Enter " + QUIT_COMMAND + " to quit anytime.");
    }

    //REQUIRES: last letter of word is a valid key
    //EFFECTS: handles user input during the game
    private void handleInputGame(String word) {
        Player currentPlayer = players.get(currentTurn);
        String firstLetter = word.substring(0,1);
        String lastLetter = word.substring(word.length() - 1);

        if (gameOver(word)) {
            run = false;
        } else if (!validInput(lastLetter)) {
            System.out.println("\nNot your turn!");
            currentTurn--;
            cardCountInt--;
        } else if (lastLetter.equals(currentPlayer.getFlipKey())) {
            Card currentCard = currentPlayer.flipCard();
            cardsPlayed.add(currentCard);
            System.out.println("\n" + currentPlayer.getName() + " flipped a card!\n");
            System.out.println("Card count is at " + cardCount);
        } else {
            cardCount = Card.Value.Ace;
            cardCountInt = -1;
            handleSlapInput(firstLetter, lastLetter);
        }

    }

    //EFFECTS: handles the slap input
    private void handleSlapInput(String first, String last) {

        for (Player p : players) {
            if (last.equals(p.getSlapKey())) {
                currentTurn = players.indexOf(p) - 1;
                p.addCardsToHand(cardsPlayed);
                cardsPlayed.removeAll(cardsPlayed);
                System.out.println("\n oh no, " + p.getName() + " was the last to slap...\n");
            }
            if (first.equals(p.getSlapKey())) {
                System.out.println("\n Yay, " + p.getName() + " was the first to slap!\n");
            }
        }
    }

    // REQUIRES: key must be a single letter
    // checks if the input is a playable command
    public Boolean validInput(String key) {
        validKeys = new ArrayList<>();
        Player currentPlayer = players.get(currentTurn);
        validKeys.add(currentPlayer.getFlipKey());
        validKeys.add("t");

        for (Player p : players) {
            validKeys.add(p.getSlapKey());
        }

        return validKeys.contains(key);
    }

    //EFFECTS: shows the cards that have been played so far
    public void printCardsPlayed() {
        for (Card c : cardsPlayed) {
            System.out.println(c.getCard());
        }
    }

    //MODIFIES: this
    //EFFECTS: method that increments the next turn count
    private void updateCurrentTurn() {
        this.currentTurn = (currentTurn + 1) % players.size();
    }

    //EFFECTS: increments the cardCount during game
    private void updateCardCount() {
        ArrayList<Card.Value> values = new ArrayList<>();
        Collections.addAll(values, Card.Value.values());

        cardCountInt = (cardCountInt + 1) % values.size();
        cardCount = values.get(cardCountInt);
    }



    //EFFECTS: handles user input before the game until user quits
    public void handleInputStart() {
        while (start) {
            printInstructions();
        }
        printKeys();
        System.out.println("\nGet ready to play!");
    }

    //EFFECTS: prints instructions to start the game
    private void printInstructions() {
        System.out.println("\nPlease enter a player name.");
        System.out.println("When you are ready to play, enter '" + PLAY_COMMAND + "'.");
        String str;

        str = input.nextLine();
        int num = 0;

        if (str.equals(PLAY_COMMAND) || num == 4) { // TODO Why doesn't this work?
            start = false;
        } else {
            playerNames.add(str);
            printPlayers();
            printInstructions();
            num++;
        }
    }

    //EFFECTS: prints out players entered to play
    private void printPlayers() {
        System.out.println("\nCurrent players:\n");
        for (String p : playerNames) {
            System.out.println(p);
        }
    }

    private void printKeys() {
        instructions.add(" : 'x' to slap, 'c' to flip");
        instructions.add(" : 'b' to slap, 'n' to flip");
        instructions.add(" : 'q' to slap, 'a' to flip");
        instructions.add(" : 'l' to slap, 'p' to flip");

        System.out.println("\nInstructions: \n");
        int c = 0;
        for (String p : playerNames) {
            System.out.println(p + instructions.get(c));
            c++;
        }
    }

    private void initializeAndDeal() {
        int cardsPerPerson = 52 / playerNames.size();

        for (int p = 0; p < playerNames.size(); p++) {
            ArrayList<Card> playerHand = new ArrayList<>();
            Card[] hand = cardDeck.dealCards(cardsPerPerson);
            Collections.addAll(playerHand, hand);
            playerDecks.add(playerHand);

            String slapKey = instructions.get(p).substring(4,5);
            String flipKey = instructions.get(p).substring(17,18);
            Player player = new Player(playerNames.get(p), playerDecks.get(p), slapKey, flipKey);
            player.setNumCardsLeft(cardsPerPerson);
            players.add(player);
        }
    }

    //EFFECTS: get the name of the current player
    public String getCurrentName() {
        return playerNames.get(currentTurn);
    }

    //EFFECTS: check whether the game is over
    public boolean gameOver(String input) {
        Boolean check = false;
        int least = 52;

        for (Player p : players) {
            if (p.checkEmpty()) {
                winner = p.getName();
                check = true;
            }
        }

        if (input.equals(QUIT_COMMAND)) {
            for (Player p : players) {
                if (p.getNumCardsLeft() < least) {
                    winner = p.getName();
                }
            }
            check = true;
        }

        return check;
    }


    // method for testing only!

    public ArrayList<String> getPlayerNames() {
        return playerNames;
    }

    public int getCurrentPlayerCardsLeft() {
        return players.get(currentTurn).getNumCardsLeft();
    }


    public void seeHand() {
        for (Player p : players) {
            ArrayList<Card> cards = p.getHand();
            System.out.println("\nnext hand\n");

            for (Card c : cards) {
                System.out.println(c.getCard());
            }
        }

    }

}