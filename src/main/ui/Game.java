package ui;


import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.exceptions.InvalidInputException;
import ui.exceptions.InvalidSlapException;
import ui.exceptions.QuitGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Represents a game of SlapJack
 **/

public class Game extends JFrame implements ActionListener {
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
    private ImageIcon titleImage;
    private JLabel displayImage;
    private int cardCountInt;
    private int currentTurn;
    private int numberOfPlayers;
    private Leaderboard leaderboard;
    private final Scanner input;
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;
    private static final String JSON_STORE = "./data/leaderboard.json";
    private static final String PLAY_COMMAND = "play";
    private static final String QUIT_COMMAND = "quit";
    private static final String SAVE_COMMAND = "save";
    private static final String VIEW_COMMAND = "view";
    private static final String LOAD_COMMAND = "LOAD";

    //EFFECTS: constructs a game
    public Game() throws FileNotFoundException {
        initializeVariables();
        input = new Scanner(System.in);
        leaderboard = new Leaderboard();
        instructions = new ArrayList<>();
        instructions.add(" : 'b' to slap, 'm' to flip");
        instructions.add(" : 'z' to slap, 'c' to flip");
        instructions.add(" : '2' to slap, 'a' to flip");
        instructions.add(" : 'l' to slap, '0' to flip");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        System.out.println("\nWelcome to SlapJack Mania!\nTo start the game, enter up to four player names.");

        try {
            //setUp();
            runGame(); // will not run if GUI calls run game GUI
        } catch (QuitGame e) {
            System.out.println("Quitting game...");
        }
    }

    //MODIFIES: this
    //EFFECTS: runs a game based on user input
    public void runGameGUI() {
        initializeAndDeal();


        // say whose turn it is
        // display the last three cards played


        // whether a slap or flip occurred
        updateCardCount();
        updateCurrentTurn();

        // make a done button for when ppl want to finish playing
        // the following should be after a game has finished

        //-> say who's the winner
        afterGameGUI();
        //-> this has save game, leaderboard, and store
        //play again button?

    }

    //MODIFIES: this
    //EFFECTS: runs a game based on user input
    public void runGame() throws QuitGame {
        setUp();
        handleInputStart();
        initializeAndDeal();

        while (run) {
            beginRound();

            try {
                handleInputGame(key);
                updateCardCount();
                updateCurrentTurn();
            } catch (InvalidInputException e) {
                System.err.println("That was an invalid input :(");
            }
        }

        congratulate();

        while (end) {
            instructionsAfterGame();
            acceptInput();
            try {
                handleInputAfter(key);
            } catch (InvalidInputException e) {
                System.err.println("That was an invalid input :(");
            }
        }
    }

    //EFFECTS: helper that initializes variables and graphics
    private void setUp() {
        initializeVariables();
        initializeGraphics();
    }

    // MODIFIES: this
    // EFFECTS:  draws the JFrame window where this Game will operate
    private void initializeGraphics() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setSize(1200,800);
        setTitle("Slap Jack Mania");
        loadImages();
        setTitlePage();
        getContentPane().setBackground(Color.BLUE);
    }

    //MODIFIES: this
    //EFFECTS: loads images that will be displayed
    private void loadImages() {
        String sep = System.getProperty("file.separator");
        titleImage = new ImageIcon(System.getProperty("user.dir") + sep  + "images" + sep + "Image1.jpg");
    }

    //MODIFIES: this
    //EFFECTS: sets up the title page when the application starts
    private void setTitlePage() {
        addButtonsToTitlePage();
        displayImage = new JLabel(titleImage);
        displayImage.setBounds(0, 0, 1200, 800);
        add(displayImage);
    }

    //MODIFIES: this
    //EFFECTS: adds buttons to the title page
    private void addButtonsToTitlePage() {
        JButton loadButton = new JButton("Load Data");
        loadButton.setBounds(525,650,150,50);
        loadButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        add(loadButton);
        loadButton.setActionCommand(LOAD_COMMAND);
        loadButton.addActionListener(this);

        JButton playButton = new JButton("Start Game");
        playButton.setBounds(512,550,175,80);
        playButton.setFont(new Font("Times New Roman", Font.BOLD, 25));
        add(playButton);
        playButton.addActionListener(e -> {
            loadButton.setVisible(false);
            playButton.setVisible(false);
            enterPlayerNamesGUI();
        });
    }

    //MODIFIES: this
    //EFFECTS: accepts player name inputs in GUI
    private void enterPlayerNamesGUI() {
        JFrame names = new JFrame("Players");
        JLabel label = new JLabel("Please enter up to four player names");
        JTextField text = new JTextField(" ");
        String space = "           ";
        JTextArea textArea = new JTextArea("\n" + space);
        textArea.setEditable(false);

        label.setBounds(70, 30, 375, 75);
        text.setBounds(80, 120, 200, 40);
        textArea.setBounds(85, 180, 195, 200);

        text.setBackground(new Color(255, 248, 244));
        textArea.setBackground(new Color(216, 233, 248));

        label.setFont(new Font("Times New Roman", Font.BOLD, 22));
        textArea.setFont(new Font("Times New Roman", Font.BOLD, 18));

        createEnterButton(names, text, textArea, space);
        createDoneButton(names);

        names.add(label);
        names.add(text);
        names.add(textArea);
        setFrame(names, 500, 500);
    }

    //EFFECTS: helper that sets the JFrame
    private void setFrame(JFrame names, int width, int height) {
        names.setSize(width,height);
        names.setLayout(new BorderLayout());
        names.setVisible(true);
        names.setLocationRelativeTo(this);
        names.setBackground(new Color(255, 248, 244));
        names.getContentPane().setBackground(new Color(216, 233, 248));
    }

    //MODIFIES: this
    //EFFECTS: creates Enter button when entering player names
    private void createEnterButton(JFrame names, JTextField text, JTextArea textArea, String space) {
        JButton enter = new JButton("Enter");
        enter.setBackground(new Color(255, 248, 244));
        enter.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        enter.setBounds(305, 120, 80, 40);
        names.add(enter);
        enter.addActionListener(e -> enterNameAction(text, textArea, space));
    }

    //MODIFIES: this
    //EFFECTS: creates Done button when entering player names
    private void createDoneButton(JFrame names) {
        JButton done = new JButton("Done");
        done.setBackground(new Color(255, 248, 244));
        done.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        done.setBounds(305, 390, 80, 40);
        names.add(done);
        done.addActionListener(e -> {
            doneEnteringNameAction();
            names.setVisible(false);
            //runGameGUI();  //TODO change to this
            //try {
            //    runGame();
            //} catch (QuitGame ex) {
            //    System.out.println("Quit game using top left button.");
            //}
        });
    }

    //EFFECTS: action performed when load leaderboard button is clicked
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(LOAD_COMMAND)) {
            loadLeaderboard();
            JLabel message = new JLabel("Loaded Data Successfully!");
            JOptionPane.showMessageDialog(null, message);
        }
    }

    //MODIFIES: this
    //EFFECTS: action performed when Enter button is clicked
    private void enterNameAction(JTextField text, JTextArea textArea, String space) {
        numberOfPlayers++;
        playerNames.add(text.getText().trim());
        printPlayers();
        textArea.append(text.getText() + "\n\n" + space);
        text.setText(" ");
    }

    //EFFECTS: action performed when Done button is clicked
    private void doneEnteringNameAction() {
        start = false;
        printKeys();
        JLabel message = new JLabel("Get ready to play!");
        JOptionPane.showMessageDialog(null, message);
    }

    //EFFECTS: returns an image of specified card
    private ImageIcon getCardImage(Card card) {
        String sep = System.getProperty("file.separator");
        return new ImageIcon(System.getProperty("user.dir") + sep  + "images" + sep
                + card.getCardName() + ".png");
    }

    //EFFECTS: creates a Leaderboard GUI
    private void printLeaderboardGUI() {
        JFrame frame = new JFrame("Leaderboard");
        JLabel label = new JLabel("L E A D E R B O A R D");
        JTextArea textArea = new JTextArea("Username \t\t Wins \t\t Games Played \n\n");
        JButton sort = new JButton("Sort By Name");
//        JLabel nameHeader = new JLabel("Username");
//        JLabel winsHeader = new JLabel("Wins");
//        JLabel gamesPlayedHeader = new JLabel("Games Played");

        label.setBounds(175, 25, 450, 100);
        textArea.setBounds(75, 175, 700, 350);
        sort.setBounds(300, 425, 200, 75);

        label.setFont(new Font("Times New Roman", Font.BOLD, 40));
        textArea.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        sort.setFont(new Font("Times New Roman", Font.BOLD, 20));

        textArea.setBackground(new Color(216, 233, 248));
        textArea.setEditable(false);

        sort.addActionListener(e -> leaderboardHelperGUI(textArea, leaderboard.getSortedAccountsByName()));

        frame.add(sort);
        frame.add(label);
        frame.add(textArea);
        leaderboardHelperGUI(textArea, leaderboard.getAccounts());
        setFrame(frame, 800, 600);
    }

    //EFFECTS: prints the info in leaderboard onto the JTextArea
    private void leaderboardHelperGUI(JTextArea textArea, ArrayList<Account> accounts) {
        textArea.setText("Username \t\t Wins \t\t Games Played \n\n");
        ArrayList<String> info = new ArrayList<>();

        for (Account a : accounts) {
            info.add(a.getUsername());
            info.add(Integer.toString(a.getWins()));
            info.add(Integer.toString(a.getGamesPlayed()));
        }

        int n = 1;
        for (String s : info) {
            if (n == 3) {
                textArea.append(s + "\n");
                n = 1;
            } else {
                textArea.append(s + "\t\t");
                n++;
            }

        }
    }

    //MODIFIES: this
    //EFFECTS: Creates GUI when a game ends
    private void afterGameGUI() {
        remove(displayImage);
        JTextArea win = new JTextArea("The winner is " + winner + "!");  // TODO this doesn't show up :(
        win.setBounds(550, 100, 100, 50);
        win.setVisible(true);
        add(win);

        createSaveButton();
        createLeaderboardButton();
        createStoreDataButton();
        displayImage = new JLabel(titleImage);
        displayImage.setBounds(0, 0, 1200, 800);
        add(displayImage);
    }

    //EFFECTS: creates Save button for afterGameGUI
    private void createSaveButton() {
        JButton save = new JButton("Save Game");
        save.setFont(new Font("Times New Roman", Font.BOLD, 25));
        save.setBounds(200,550,175,80);
        save.setVisible(true);
        save.setActionCommand(SAVE_COMMAND);
        save.addActionListener(e -> {
            handleSave();
            JLabel message = new JLabel("Saved Game Successfully!");
            JOptionPane.showMessageDialog(null, message);
        });

        add(save);
    }

    //EFFECTS: creates Leaderboard button for afterGameGUI
    private void createLeaderboardButton() {
        JButton lb = new JButton("Leaderboard");
        lb.setFont(new Font("Times New Roman", Font.BOLD, 25));
        lb.setBounds(487,550,225,80);
        lb.setVisible(true);
        lb.setActionCommand(VIEW_COMMAND);
        lb.addActionListener(e -> printLeaderboardGUI());

        add(lb);
    }

    //EFFECTS: creates Store Data button for afterGameGUI
    private void createStoreDataButton() {
        JButton store = new JButton("Store Data");
        store.setFont(new Font("Times New Roman", Font.BOLD, 25));
        store.setBounds(850,550,175,80);
        store.setVisible(true);
        store.setActionCommand("store");
        store.addActionListener(e -> {
            storeLeaderboard();
            JLabel message = new JLabel("Stored Data Successfully!");
            JOptionPane.showMessageDialog(null, message);
        });

        add(store);
    }

    //EFFECTS: helper that initializes each round in the game
    private void beginRound() {
        System.out.println("\nIt's " + playerNames.get(currentTurn) + "'s turn");
        printCardsPlayed();
        acceptInput();
    }

    //EFFECTS: helper that prints congratulatory text after a game
    private void congratulate() {
        System.out.println("\nThe winner is " + winner + "! Good game.");
        System.out.println("\nEnter '" + SAVE_COMMAND + "' to save the game.");
        afterGameGUI();
    }

    //MODIFIES: this
    //EFFECTS: loads Leaderboard from file
    private void loadLeaderboard() {
        try {
            leaderboard = jsonReader.read();
            System.out.println("\nLoaded previous Leaderboard from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("\nUnable to read from " + JSON_STORE + " file :[");
        }
    }

    //EFFECTS: saves Leaderboard to file
    private void storeLeaderboard() {
        try {
            jsonWriter.open();
            jsonWriter.write(leaderboard);
            jsonWriter.close();
            System.out.println("\nSaved current Leaderboard to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("\nUnable to write to " + JSON_STORE + " file :[");
        }
    }

    //MODIFIES: this
    //EFFECTS: resets the required variables to start a new game
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

    //Modeled after long form problem: FitLifeGymChain
    //MODIFIES: this
    //EFFECTS: prints instructions to get the game started
    // if QUIT_COMMAND is entered, throw a QuitGame exception
    // if empty string is entered, throw an InvalidInputException
    private void printInstructions() throws QuitGame, InvalidInputException {
        System.out.println("\nOther Actions: \nEnter '" + LOAD_COMMAND + "' to load previous leaderboard from file");
        System.out.println("Enter '" + PLAY_COMMAND + "' when you are ready to play");
        System.out.println("Enter '" + QUIT_COMMAND + "' to quit the app\n");
        String str;
        str = input.nextLine();

        if (str.equals(LOAD_COMMAND)) {
            loadLeaderboard();
        } else if (str.equals(PLAY_COMMAND) || numberOfPlayers == 4) {
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

    //EFFECTS: prints the cards that have been played so far
    public void printCardsPlayed() {
        System.out.println("Cards played:\n");

        for (Card c : cardsPlayed) {
            System.out.println(c.getCardName());
        }
    }

    //Modeled after long form problem: FitLifeGymChain
    //MODIFIES: this
    //EFFECTS: handles user input during the game
    // if empty string is entered, throw InvalidInputException
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

    //MODIFIES: this
    //EFFECTS: increments the cardCount by one value during game
    private void updateCardCount() {
        ArrayList<Card.Value> values = new ArrayList<>();
        Collections.addAll(values, Card.Value.values());

        cardCountInt = (cardCountInt + 1) % values.size();
        cardCount = values.get(cardCountInt);
    }

    //MODIFIES: this
    //EFFECTS: increments the next player's turn by one
    private void updateCurrentTurn() {
        this.currentTurn = (currentTurn + 1) % players.size();
    }

    //MODIFIES: this
    //EFFECTS: handles the case when the wrong player flips a card
    private void wrongTurn() {
        System.out.println("\nNot your turn >:(");
        currentTurn--;
        cardCountInt--;
    }

    //MODIFIES: this
    //EFFECTS: handles the case when the correct player flips a card
    private void cardFlip(Player currentPlayer) {
        Card currentCard = currentPlayer.flipCard();
        cardsPlayed.add(currentCard);
        System.out.println("\n-> " + currentPlayer.getName() + " flipped a card, " + cardCount + "!");
    }

    //MODIFIES: this
    //EFFECTS: handles the case when players 'slap' the cardsPlayed
    private void cardSlap(String first, String last) throws InvalidSlapException {
        handleSlapInput(first, last);
        cardCount = Card.Value.Ace;
        cardCountInt = -1;
    }

    //EFFECTS: handles the case when users input a 'slap' string
    // if no cards have been played yet, throw an InvalidSlapException
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

    //MODIFIES: this
    //EFFECTS: handles the case when a correct slap occurs
    private void correctSlap(String first, String last) {
        Random random = new Random();
        int randomNumber = random.nextInt(3);

        for (Player p : players) {
            if (last.equals(p.getSlapKey())) {
                currentTurn = players.indexOf(p) - 1;
                p.addCardsToHand(cardsPlayed);
                cardsPlayed.clear();
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

    //MODIFIES: this
    //EFFECTS: handles the case when an incorrect slap occurs
    private void incorrectSlap(String first) {
        for (Player p : players) {
            if (first.equals(p.getSlapKey())) {
                System.out.println("\n Oh no, " + p.getName() + " wasn't supposed to slap!\n");
                System.out.println("-> Taking all the cards...");
                p.addCardsToHand(cardsPlayed);
                cardsPlayed.clear();
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

    //EFFECTS: prints out the control instructions for each player
    private void printKeys() {
        System.out.println("\nInstructions:");
        int c = 0;
        for (String p : playerNames) {
            System.out.println(p + instructions.get(c));
            c++;
        }
    }

    //MODIFIES: this
    //EFFECTS: checks whether the game is over and updates the winner
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
        System.out.println("\nEnter '" + VIEW_COMMAND + "' to see the Leaderboard.");
        System.out.println("Enter '" + PLAY_COMMAND + "' to play again.");
        System.out.println("Enter '" + QUIT_COMMAND + "' to quit anytime.");
        System.out.println("Enter 'store' to store current leaderboard to file.");
    }

    //MODIFIES: this
    //EFFECTS handles the user input after a game
    // if empty string is entered, throw InvalidInputException
    public void handleInputAfter(String word) throws QuitGame, InvalidInputException {
        if (word.isEmpty()) {
            throw new InvalidInputException();
        } else if (word.equals(SAVE_COMMAND)) {
            handleSave();
        } else if (word.equals("store")) {
            storeLeaderboard();
        }

        if (word.equals(VIEW_COMMAND)) {
            System.out.println("\nusername\t\twins\t\tgames played\n" + leaderboard.printAllAccounts() + "\n");
//            System.out.println("\nEnter '" + REMOVE_COMMAND + "' to remove an account"); //prof said fix problem later
//        }  else if (word.equals(REMOVE_COMMAND)) {
//            handleRemove();
//            leaderboard.printAllAccounts();
        }

        if (word.equals(QUIT_COMMAND)) {
            System.out.println("\nThanks for playing :)");
            throw new QuitGame();
        }

        if (word.equals(PLAY_COMMAND)) {
            try {
                System.out.println("\nPlaying again! \nPlease enter up to four player names.");
                runGame();
            } catch (QuitGame e) {
                System.out.println("Quitting game...");
                end = false;
            }
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

    //MODIFIES: this
    //EFFECTS: updates the stats of each account in the leaderboard
    private void updateAccounts(Player p) {
        leaderboard.updateAccount(p.getName(), p.getName().equals(winner));
    }

    //EFFECTS: checks whether the Jack rule and Count rule is met
    private Boolean checkJackRule(Card first) {
        return first.getValue() == Card.Value.Jack || first.getValue() == getPrevCard();
    }

    //EFFECTS: helper that gets the previous card in the order of Card.Value
    private Card.Value getPrevCard() {
        ArrayList<Card.Value> values = new ArrayList<>();
        Collections.addAll(values, Card.Value.values());
        Card.Value prevCard;
        int prevCardInt;

        prevCardInt = (cardCountInt - 1) % values.size();
        prevCard = values.get(prevCardInt);
        return prevCard;
    }

    //EFFECTS: checks whether the Jack or Double rule is met
    private Boolean checkTwoRules(Card first, Card second) {
        return first.getValue() == second.getValue() || checkJackRule(first);
    }

    //EFFECTS: checks whether any of the rules are met
    private Boolean checkAllRules(Card first, Card second, Card third) {
        return checkTwoRules(first, second) || first.getValue() == third.getValue();
    }

    //MODIFIES: this
    //EFFECTS: helper that accepts the user input
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