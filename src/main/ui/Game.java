package ui;

import model.Card;
import model.CardDeck;
import model.Player;

import java.util.*;

public class Game {
    private Card.Value cardCount; // keeping track of the count of cards
    private int currentTurn; // keeps track of whose turn it is
    private ArrayList<String> playerNames;
    private ArrayList<Player> players;
    private ArrayList<ArrayList<Card>> playerDecks; // each player has a diff list of cards
    private ArrayList<Card> pilePlayed;
    private ArrayList<String> instructions = new ArrayList<>();
    private CardDeck cardDeck;
    private Scanner input;
    private Boolean run;
    private String winner;
    private static final String PLAY_COMMAND = "play";
    private static final String QUIT_COMMAND = "quit";


    public Game() {
        cardDeck = new CardDeck();
        pilePlayed = new ArrayList<>();
        playerNames = new ArrayList<>();
        players = new ArrayList<>();
        currentTurn = 0;
        cardCount = Card.Value.Ace;
        playerDecks = new ArrayList<ArrayList<Card>>();
        input = new Scanner(System.in);
        run = true;

        runGame();
    }

    public void runGame() {
        cardDeck.shuffleDeck();
        handleInputStart();
        initializeAndDeal();

        // just testing, delete later
        seeHand();
        int n = players.get(0).getNumCardsLeft();
        System.out.println(n);
        Card c = players.get(0).flipCard();
        System.out.println(c.getCard());
        int i = players.get(0).getNumCardsLeft();
        System.out.println(i);
        //


        System.out.println("Current Turn: " + playerNames.get(currentTurn));
        // while run is true:
        // start the game by indicating which player goes first
        // during each round
        //     indicate whose turn it is
        //     player flips a card using command key -> adds this card to pilePlayed
        //     increment playCount
        //     increment currentTurn (remember to mod by number of players!)
        //     check if game over
        //          yes : change run to false
        //                announce winner
        //                ask whether or not to save -> if save then call methods that update or registers accounts
        //     checks if there is a slap rule
        //          yes : last person to slap gets all the cards (check this using string position)
        //                resets the current turn to the person who had to collect the cards
        //     TODO Q: would it be better to store the rules in another class? or keep it in a method?

        // after the game : either a player has no cards OR quit command is inputted
        // would need another processor thing
        // option of whether to save this game or not
        // if yes, either create new account or find existing account using the player name
        // update the account information and store it in leaderboard permanently
        // add a method to leaderboard to delete an account
        //

    }


    //EFFECTS: handles user input until user quits
    public void handleInputStart() {
        while (run) {
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
            run = false;
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

        for (Player p : players) {
            if (p.checkEmpty()) {
                check = true;
            }
        }

        if (input.equals(QUIT_COMMAND)) {
            check = true;
        }

        return check;
    }

    // only called when gameOver is true
    public String getWinner() {
        Boolean b = true;
        return null;
        //TODO finish this
    }

    //TODO updates accounts if game is saved
    public void updateAccounts() {
        //
    }

    //TODO registers account
    public void signUp() {
        //
    }

    // getters
    public ArrayList<String> getPlayerNames() {
        return playerNames;
    }

    public int getCurrentPlayerCardsLeft() {
        return players.get(currentTurn).getNumCardsLeft();
    }






    // for testing only!
    public void seeHand() {
        for (Player p : players) {
            ArrayList<Card> cards = p.getHand();
            System.out.println("\nnext hand\n");

            for (Card c : cards) {
                System.out.println(c.getCard());
            }
        }

    }

    // TODO method that increments the next playCount
    // use .get(index) to help?

    // TODO method that increments cardCount
    // TODO method that checks if it is the player's turn

}