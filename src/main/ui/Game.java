package ui;

import exceptions.InvalidInputException;
import exceptions.InvalidSlapException;
import exceptions.QuitGame;
import model.*;

import java.util.*;

/**
 * Represents a game of SlapJack
 **/

public class Game {
    private ArrayList<String> playerNames;
    private ArrayList<Player> players;
    private ArrayList<ArrayList<Card>> playerDecks;
    private ArrayList<Card> cardsPlayed;
    private final ArrayList<String> instructions;
    private CardDeck cardDeck;
    private Card.Value cardCount;
    private Boolean start;
    private Boolean run;
    private Boolean end;
    private String winner;
    private String key;
    private int cardCountInt;
    private int currentTurn;
    private int numberOfPlayers;
    private final Scanner input;
    private final Leaderboard leaderboard;
    private static final String PLAY_COMMAND = "play";
    private static final String QUIT_COMMAND = "quit";
    private static final String SAVE_COMMAND = "save";
    private static final String VIEW_COMMAND = "view";
    //private static final String REMOVE_COMMAND = "remove";

    //EFFECTS: initializes a game
    public Game() {
        initializeVariables();
        input = new Scanner(System.in);
        leaderboard = new Leaderboard();
        instructions = new ArrayList<>();
        instructions.add(" : 'x' to slap, 'c' to flip");
        instructions.add(" : 'b' to slap, 'n' to flip");
        instructions.add(" : 'q' to slap, 'a' to flip");
        instructions.add(" : 'l' to slap, 'p' to flip");

        System.out.println("\nWelcome to SlapJack Mania! \nTo start, please enter up to four player names.");
        try {
            runGame();
        } catch (QuitGame e) {
            System.out.println("Quitting game...");
        }
    }

    //MODIFIES: this
    //EFFECTS: runs a game
    public void runGame() throws QuitGame {
        initializeVariables();
        handleInputStart();
        initializeAndDeal();

        while (run) {
            System.out.println("\nIt's " + playerNames.get(currentTurn) + "'s turn");
            printCardsPlayed();
            acceptInput();

            try {
                handleInputGame(key);
                updateCardCount();
                updateCurrentTurn();
            } catch (InvalidInputException e) {
                System.err.println("That was an invalid input :(");
            }
        }

        System.out.println("\nThe winner is " + winner + "! Good game.");
        System.out.println("\nEnter '" + SAVE_COMMAND + "' to save the game.");

        while (end) {
            instructionsAfterGame();
            acceptInput();
            handleInputAfter(key);
        }
    }

    //MODIFIES: this
    //EFFECTS: resets all the required variables to start another game
    private void initializeVariables() {
        cardDeck = new CardDeck();
        cardsPlayed = new ArrayList<>();
        playerNames = new ArrayList<>();
        players = new ArrayList<>();
        currentTurn = 0;
        cardCount = Card.Value.Ace;
        cardCountInt = 0;
        playerDecks = new ArrayList<>();
        start = true;
        run = true;
        end = true;
        numberOfPlayers = 0;
    }


    //EFFECTS: handles user input before the game starts, until the user quits
    public void handleInputStart() throws QuitGame {
        while (start) {
            try {
                printInstructions();
            } catch (InvalidInputException e) {
                System.err.println("That was an invalid input :(");
            }
        }
        printKeys();
        System.out.println("\nGet ready to play!");
    }

    //Looked at long form problem (FitLifeGymChain) for help
    //EFFECTS: prints instructions to get the game started
    private void printInstructions() throws QuitGame, InvalidInputException {
        System.out.println("\nWhen you are ready to play, enter '" + PLAY_COMMAND + "'");
        System.out.println("To quit the app, enter '" + QUIT_COMMAND + "'\n");
        String str;
        str = input.nextLine();

        if (str.equals(PLAY_COMMAND) || numberOfPlayers == 4) {
            start = false;
        } else if (str.equals(QUIT_COMMAND)) {
            throw new QuitGame();
        }  else if (str.isEmpty()) {
            throw new InvalidInputException();
        } else {
            numberOfPlayers++;
            playerNames.add(str);
            printPlayers();
            printInstructions();
        }
    }

    //MODIFIES: this
    //EFFECTS: initializes a deck of cards and deals cards to each player
    private void initializeAndDeal() {
        cardDeck.makeDeck();
        cardDeck.shuffleDeck();

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

    //EFFECTS: shows the cards that have been played so far
    public void printCardsPlayed() {
        System.out.println("Cards played:\n");

        for (Card c : cardsPlayed) {
            System.out.println(c.getCardName());
        }
    }

    //Looked at long form problem (FitLifeGymChain) for help
    //EFFECTS: handles user input during the game
    private void handleInputGame(String word) throws InvalidInputException {
        Player currentPlayer = players.get(currentTurn);

        if (word.isEmpty()) {
            throw new InvalidInputException();
        }

        String firstLetter = word.substring(0,1);
        String lastLetter = word.substring(word.length() - 1);

        if (gameOver(word)) {
            run = false;
        } else if (!validInput(lastLetter)) {
            wrongTurn();
        } else if (lastLetter.equals(currentPlayer.getFlipKey())) {
            cardFlip(currentPlayer);
        } else {
            try {
                cardSlap(firstLetter, lastLetter);
            } catch (InvalidSlapException e) {
                System.out.println("Cannot slap if no cards have been played :|");
            }
        }
    }

    //EFFECTS: increments the cardCount during game
    private void updateCardCount() {
        ArrayList<Card.Value> values = new ArrayList<>();
        Collections.addAll(values, Card.Value.values());

        cardCountInt = (cardCountInt + 1) % values.size();
        cardCount = values.get(cardCountInt);
    }

    //MODIFIES: this
    //EFFECTS: method that increments the next player's turn
    private void updateCurrentTurn() {
        this.currentTurn = (currentTurn + 1) % players.size();
    }

    //MODIFIES: this
    //EFFECTS: handles the case when another player flips a card not during their turn
    private void wrongTurn() {
        System.out.println("\nNot your turn >:(");
        currentTurn--;
        cardCountInt--;
    }

    //MODIFIES: this
    //EFFECTS: handles the case when a player flips a card
    private void cardFlip(Player currentPlayer) {
        Card currentCard = currentPlayer.flipCard();
        cardsPlayed.add(currentCard);
        System.out.println("\n-> " + currentPlayer.getName() + " flipped a card, " + cardCount + "!");
    }

    //EFFECTS: handles the case when players 'slap' the cardsPlayed
    private void cardSlap(String first, String last) throws InvalidSlapException {
        handleSlapInput(first, last);
        cardCount = Card.Value.Ace;
        cardCountInt = -1;
    }

    //EFFECTS: handles the case when users input a 'slap'
    private void handleSlapInput(String first, String last) throws InvalidSlapException {
        int size = cardsPlayed.size();
        boolean validSlap;
        if (size >= 3) {
            Card firstCard = cardsPlayed.get(size - 1);
            Card secondCard = cardsPlayed.get(size - 2);
            Card thirdCard = cardsPlayed.get(size - 3);
            validSlap = checkAllRules(firstCard, secondCard, thirdCard);
        } else if (size >= 2) {
            Card firstCard = cardsPlayed.get(size - 1);
            Card secondCard = cardsPlayed.get(0);
            validSlap = checkTwoRules(firstCard, secondCard);
        } else if (size >= 1) {
            Card firstCard = cardsPlayed.get(0);
            validSlap = checkJackRule(firstCard);
        } else {
            throw new InvalidSlapException();
        }

        if (validSlap) {
            correctSlap(first, last);
        } else {
            incorrectSlap(first);
        }

    }

    //EFFECTS: handles the case when a correct slap occurs
    private void correctSlap(String first, String last) {
        Random random = new Random();
        int randomNumber = random.nextInt(3);
        for (Player p : players) {
            if (last.equals(p.getSlapKey())) {
                currentTurn = players.indexOf(p) - 1;
                p.addCardsToHand(cardsPlayed);
                cardsPlayed.removeAll(cardsPlayed);
                System.out.println("\n-> Oh no, " + p.getName() + " was the last to slap...");
                System.out.println("-> Taking all the cards...");
            }
            if (first.equals(p.getSlapKey())) {
                System.out.println("\n-> Yay, " + p.getName() + " was the first to slap!");
                if (randomNumber == 1) {
                    System.out.println("-> So speedy!");
                }

            }
        }
    }

    //EFFECTS: handles the case when an incorrect slap occurs
    private void incorrectSlap(String first) {
        for (Player p : players) {
            if (first.equals(p.getSlapKey())) {
                System.out.println("\n Oh no, " + p.getName() + " wasn't supposed to slap!\n");
                p.addCardsToHand(cardsPlayed);
                cardsPlayed.removeAll(cardsPlayed);
                currentTurn = players.indexOf(p) - 1;
            }
        }
    }

    //REQUIRES: key must be a single letter
    //EFFECTS: checks if the input is a playable command
    public Boolean validInput(String key) {
        ArrayList<String> validKeys = new ArrayList<>();
        Player currentPlayer = players.get(currentTurn);
        validKeys.add(currentPlayer.getFlipKey());
        validKeys.add("t");

        for (Player p : players) {
            validKeys.add(p.getSlapKey());
        }

        return validKeys.contains(key);
    }

    //EFFECTS: prints out all players entered to play so far
    private void printPlayers() {
        System.out.println("\nCurrent players:");
        for (String p : playerNames) {
            System.out.println(p);
        }
    }

    //EFFECTS: prints out the instructions for each player
    private void printKeys() {
        System.out.println("\nInstructions:");
        int c = 0;
        for (String p : playerNames) {
            System.out.println(p + instructions.get(c));
            c++;
        }
    }

    //MODIFIES: this
    //EFFECTS: checks whether the game is over
    public boolean gameOver(String input) {
        boolean check = false;
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
                    least = p.getNumCardsLeft();
                }
            }
            check = true;
        }

        return check;
    }

    //EFFECTS: prints the instructions after a game
    private void instructionsAfterGame() {
        System.out.println("Enter '" + VIEW_COMMAND + "' to see the Leaderboard.");
        System.out.println("Enter '" + PLAY_COMMAND + "' to play again.");
        System.out.println("Enter '" + QUIT_COMMAND + "' to quit anytime.");
    }

    //REQUIRES: input length > 0
    //EFFECTS handles the user input after a game
    public void handleInputAfter(String word) throws QuitGame {

        if (word.equals(SAVE_COMMAND)) {
            handleSave();
        } else if (word.equals(VIEW_COMMAND)) {
            System.out.println("\nusername | wins | games played\n");
            System.out.println(leaderboard.printAllAccounts());
            System.out.println("\n");
//            System.out.println("\nEnter '" + REMOVE_COMMAND + "' to remove an account"); //prof said fix problem later
//        }  else if (word.equals(REMOVE_COMMAND)) {
//            handleRemove();
//            leaderboard.printAllAccounts();
        } else if (word.equals(PLAY_COMMAND)) {
            try {
                System.out.println("\nPlaying again! \nPlease enter up to four player names.");
                runGame();
            } catch (QuitGame e) {
                System.out.println("Quitting game...");
                end = false;
            }
        } else if (word.equals(QUIT_COMMAND)) {
            System.out.println("\nThanks for playing :)");
            throw new QuitGame();
        } else {
            System.out.println("Invalid command!");
        }
    }

    //MODIFIES: this
    //EFFECTS: saves the current game by updating player accounts to the leaderboard
    private void handleSave() {
        System.out.println("\ngame saved!\n");
        for (Player p : players) {
            Account a = leaderboard.lookupAccount(p.getName());
            if (a != null) {
                updateAccounts(p);
            } else {
                leaderboard.registerAccount(p.getName());
                updateAccounts(p);
            }
        }
    }

    //EFFECTS: updates the stats of each account in the leaderboard
    private void updateAccounts(Player p) {
        leaderboard.updateAccount(p.getName(), p.getName().equals(winner));
    }

    //EFFECTS: checks whether the Jack rule is met
    private Boolean checkJackRule(Card first) {
        return first.getValue() == Card.Value.Jack || first.getValue() == cardCount;
    }

    //EFFECTS: checks whether the Jack or Double rule is met
    private Boolean checkTwoRules(Card first, Card second) {
        return first.getValue() == second.getValue() || checkJackRule(first);
    }

    //EFFECTS: checks whether any of the rules are met
    private Boolean checkAllRules(Card first, Card second, Card third) {
        return checkTwoRules(first, second) || first.getValue() == third.getValue();
    }

    //EFFECTS: accepts the user input
    private void acceptInput() {
        key = input.nextLine();
        key = key.toLowerCase();
    }

//    //Will add this function later
//    //EFFECTS: removes an account from the leaderboards
//    private void handleRemove() {
//        System.out.println("\nEnter the account username you wish to remove: ");
//        String in;
//        in = input.nextLine();
//        leaderboard.removeAccount(in);
//        System.out.println("\nAccount '" + in + "' has been removed.\n");
//    }
}