package ui;


import model.*;
import model.Event;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.exceptions.InvalidInputException;
import ui.exceptions.InvalidSlapException;
import ui.exceptions.QuitGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
    private FlipAction flipAction;
    private SlapActionB slapActionB;
    private SlapActionZ slapActionZ;
    private SlapAction2 slapAction2;
    private SlapActionL slapActionL;
    private JLabel playerTurn;
    private JLabel footer;
    private JLabel cardImg;
    private JLabel gameMsg1;
    private JLabel gameMsg2;
    private JLabel gameMsg3;
    private JLabel gameMsg4;
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
        setUp();

//        // console version
//        try {
//            runGame();
//        } catch (QuitGame e) {
//            printEventLog();
//            System.out.println("Quitting game...");
//        }
    }

    //MODIFIES: this
    //EFFECTS: runs a GUI game based on user input
    public void runGameGUI() throws QuitGame {
        getContentPane().removeAll();
        getContentPane().repaint();
        setBackground(new Color(216, 233, 248));
        createFinishGameButton();
        createRulesButton(650,625,150,75);
        createKeysButton(this, 350,625,150,75, 20);
        createGameMessages();

        // display instructions at the bottom
        footer = new JLabel(returnKeys());
        footer.setBounds(50, 700, 1500, 100);
        footer.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        this.add(footer);

        // indicate whose turn it is
        playerTurn = new JLabel("\nIt's " + playerNames.get(currentTurn) + "'s turn");
        playerTurn.setBounds(80, 80, 400, 50);
        playerTurn.setFont(new Font("Times New Roman", Font.BOLD, 42));
        this.add(playerTurn);

        // display cards played - default is joker
        cardImg.setBounds(485, 220, 200, 290);
        this.add(cardImg);
        addCardActions();
    }

    public void createKeysButton(JFrame frame, int x, int y, int width, int height, int font) {
        JButton keysButton = new JButton("Keys");
        keysButton.setBounds(x,y,width,height);
        keysButton.setFont(new Font("Times New Roman", Font.PLAIN, font));
        keysButton.addActionListener(e -> {
            keysFrameGUI();
        });

        frame.add(keysButton);
    }

    public void createGameMessages() {
        gameMsg1 = new JLabel("Get rid of all your cards to win!");
        gameMsg1.setBounds(800, 250, 400, 50);
        gameMsg1.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        this.add(gameMsg1);

        gameMsg2 = new JLabel("Good Luck!");
        gameMsg2.setBounds(800, 300, 400, 50);
        gameMsg2.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        this.add(gameMsg2);

        gameMsg3 = new JLabel("");
        gameMsg3.setBounds(800, 350, 400, 50);
        gameMsg3.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        this.add(gameMsg3);

        gameMsg4 = new JLabel("");
        gameMsg4.setBounds(800, 400, 400, 50);
        gameMsg4.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        this.add(gameMsg4);
    }

    public void createFinishGameButton() {
        JButton finishGameButton = new JButton("Finish Game");
        finishGameButton.setBounds(500,625,150,75);
        finishGameButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        finishGameButton.setActionCommand("DONE");
        finishGameButton.addActionListener(this);
        add(finishGameButton);
    }

    public void resetCardImg() {
        cardImg.setIcon(getCardImage("Black Joker"));
    }

    public void updateCardImg() {
        cardImg.setIcon(getCardImage(cardsPlayed.get(cardsPlayed.size() - 1).getCardName()));
    }

    public void addCardActions() {
        cardImg.getInputMap().put(KeyStroke.getKeyStroke('m'), "flipAction");
        cardImg.getInputMap().put(KeyStroke.getKeyStroke('c'), "flipAction");
        cardImg.getInputMap().put(KeyStroke.getKeyStroke('a'), "flipAction");
        cardImg.getInputMap().put(KeyStroke.getKeyStroke('0'), "flipAction");
        cardImg.getActionMap().put("flipAction", flipAction);

        cardImg.getInputMap().put(KeyStroke.getKeyStroke('b'), "slapActionB");
        cardImg.getInputMap().put(KeyStroke.getKeyStroke('z'), "slapActionZ");
        cardImg.getInputMap().put(KeyStroke.getKeyStroke('2'), "slapAction2");
        cardImg.getInputMap().put(KeyStroke.getKeyStroke('l'), "slapActionL");
        cardImg.getActionMap().put("slapActionB", slapActionB);
        cardImg.getActionMap().put("slapActionZ", slapActionZ);
        cardImg.getActionMap().put("slapAction2", slapAction2);
        cardImg.getActionMap().put("slapActionL", slapActionL);
    }

    public class FlipAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            Player currentPlayer = players.get(currentTurn);
            cardFlip(currentPlayer);
            gameMsg1.setText("\n-> " + currentPlayer.getName() + " flipped a card, " + cardCount + "!");
            gameMsg2.setText("");
            gameMsg3.setText("");
            gameMsg4.setText("");
            updateCardCount();
            updateCurrentTurn();
            printCardsPlayed();
            playerTurn.setText("\nIt's " + playerNames.get(currentTurn) + "'s turn");
            footer.setText(returnKeys());
            updateCardImg();

            for (Player p : players) {
                if (p.checkEmpty()) {
                    gameOver(QUIT_COMMAND);
                    afterGameGUI();
                }
            }
        }
    }

    public class SlapActionB extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                cardSlap("b", "z");
            } catch (InvalidSlapException ex) {
                System.out.println("oh well");
                wrongTurn();
            }

            slapActionPerformedHelper();
        }
    }

    public class SlapActionZ extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                cardSlap("z", "b");
            } catch (InvalidSlapException ex) {
                System.out.println("oh well");
                wrongTurn();
            }

            slapActionPerformedHelper();
        }
    }

    public class SlapAction2 extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                cardSlap("2", "l");
            } catch (InvalidSlapException ex) {
                System.out.println("oh well");
                wrongTurn();
            }

            slapActionPerformedHelper();
        }
    }

    public class SlapActionL extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                cardSlap("l", "2");
            } catch (InvalidSlapException ex) {
                System.out.println("oh well");
                wrongTurn();
            }

            slapActionPerformedHelper();
        }
    }

    public void slapActionPerformedHelper() {
        updateCardCount();
        updateCurrentTurn();
        playerTurn.setText("\nIt's " + playerNames.get(currentTurn) + "'s turn");
        footer.setText(returnKeys());
        resetCardImg();

        for (Player p : players) {
            if (p.checkEmpty()) {
                gameOver(QUIT_COMMAND);
                afterGameGUI();
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: runs a console game based on user input
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
        flipAction = new FlipAction();
        slapActionB = new SlapActionB();
        slapActionZ = new SlapActionZ();
        slapAction2 = new SlapAction2();
        slapActionL = new SlapActionL();
        cardImg = new JLabel(getCardImage("Black Joker"));
        winner = "everybody";
    }

    // MODIFIES: this
    // EFFECTS:  draws the JFrame window where this Game will operate
    private void initializeGraphics() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setLocationRelativeTo(null);
        setVisible(true);
        setSize(1200,800);
        setTitle("Slap Jack Mania");
        loadImages();
        setTitlePage();
        getContentPane().setBackground(new Color(216, 233, 248));
        // 2 ways to do smt on close:
        //setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // and then override dispose method.
        // OR this way is more work but allows for variety of methods (window opening, activated, deiconified, etc)
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                printEventLog();
                super.windowClosing(e);
                JOptionPane.showConfirmDialog(null,"Are sure you want to exit?");
                System.exit(JOptionPane.YES_OPTION);
            }
        });
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

        JLabel title = new JLabel("Slap Jack Mania");
        title.setFont(new Font("Times New Roman", Font.BOLD, 55));
        title.setForeground(Color.WHITE);
        title.setBounds(410, 50, 700, 200);
        this.add(title);

        displayImage = new JLabel(titleImage);
        displayImage.setBounds(0, 0, 1200, 800);
        add(displayImage);
    }

    //MODIFIES: this
    //EFFECTS: adds buttons to the title page
    private void addButtonsToTitlePage() {
        JButton loadButton = new JButton("Load Data");
        loadButton.setBounds(525,670,150,50);
        loadButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        add(loadButton);
        loadButton.setActionCommand(LOAD_COMMAND);
        loadButton.addActionListener(this);

        createPlayButton(loadButton);
        createRulesButton(700,670,150,50);

        JButton aboutButton = new JButton("About");
        aboutButton.setBounds(350,670,150,50);
        aboutButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        add(aboutButton);
        aboutButton.addActionListener(e -> {
            aboutFrameGUI();
        });
    }

    private void createRulesButton(int x, int y, int width, int height) {
        JButton rulesButton = new JButton("Rules");
        rulesButton.setBounds(x,y,width,height);
        rulesButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        add(rulesButton);
        rulesButton.addActionListener(e -> {
            rulesFrameGUI();
        });
    }

    private void createPlayButton(JButton loadButton) {
        JButton playButton = new JButton("Start Game");
        playButton.setBounds(500,560,200,85);
        playButton.setFont(new Font("Times New Roman", Font.BOLD, 28));
        add(playButton);
        playButton.addActionListener(e -> {
            loadButton.setVisible(false);
            playButton.setVisible(false);
            enterPlayerNamesGUI();
        });
    }

    //Todo game stops when rules or keys button is pressed...

    private void keysFrameGUI() {
        JFrame rules = new JFrame("Keys");
        JLabel label = new JLabel("K E Y S");
        JTextArea textArea = createKeysDescription();
        textArea.setEditable(false);

        label.setBounds(75, 15, 200, 75);
        textArea.setBounds(35, 105, 240, 300);

        textArea.setBackground(new Color(216, 233, 248));

        label.setFont(new Font("Times New Roman", Font.BOLD, 32));
        textArea.setFont(new Font("Times New Roman", Font.PLAIN, 16));

        rules.add(label);
        rules.add(textArea);
        setFrame(rules, 275, 320);
    }

    private JTextArea createKeysDescription() {
        return new JTextArea("Player 1 : 'b' to slap, 'm' to flip \n\n"
        + "Player 2 : 'z' to slap, 'c' to flip\n\n"
        + "Player 3 : '2' to slap, 'a' to flip\n\n"
        + "Player 4 : 'l' to slap, '0' to flip\n\n");
    }

    private void rulesFrameGUI() {
        JFrame rules = new JFrame("Rules");
        createKeysButton(rules, 265,485,150,75, 20);

        JLabel label = new JLabel("R U L E S");
        JTextArea textArea = createRulesDescription();
        textArea.setEditable(false);

        label.setBounds(265, 35, 200, 75);
        textArea.setBounds(60, 135, 650, 500);

        textArea.setBackground(new Color(216, 233, 248));

        label.setFont(new Font("Times New Roman", Font.BOLD, 36));
        textArea.setFont(new Font("Times New Roman", Font.PLAIN, 16));

        rules.add(label);
        rules.add(textArea);
        setFrame(rules, 675, 650);
    }

    private JTextArea createRulesDescription() {
        return new JTextArea(
                "General Rules\n\n"
                + "-  Last player to slap in the active pair must collect all cards from the current round.\n"
                + "-  Players who slap incorrectly must collect all cards from the current round.\n"
                + "-  There is no penalty if all players do not slap the pile.\n"
                + "-  In ascending order, players count from Ace to King every time a card is flipped \n"
                + "   The count starts over when a round ends.\n\n\n"
                + "When to Slap\n\n"
                + "-  A Jack appears\n"
                + "-  The card value matches the current count\n"
                + "-  Doubles: Two cards with the same value, ex/ 3 + 3\n"
                + "-  Sandwich: Two cards of the same value with any card in between, ex/ 1 + 7 + 1");
    }

    private void aboutFrameGUI() {
        JFrame about = new JFrame("About");
        JLabel label = new JLabel("A B O U T");
        JTextArea textArea = createAboutDescription();
        textArea.setEditable(false);

        label.setBounds(255, 30, 375, 75);
        textArea.setBounds(60, 125, 650, 500);

        textArea.setBackground(new Color(216, 233, 248));

        label.setFont(new Font("Times New Roman", Font.BOLD, 32));
        textArea.setFont(new Font("Times New Roman", Font.PLAIN, 16));

        about.add(label);
        about.add(textArea);
        setFrame(about, 675, 590);
    }

    private JTextArea createAboutDescription() {
        return new JTextArea(
                "This application represents a game of Slap Jack, a multiplayer reaction game of \n"
                        + "up to four players. Players can choose whether to save a game, which will add\n"
                        + "their account to the leaderboard. Players can also view each saved account's \n"
                        + "total wins and games played. The 'flips' and 'slaps' will be represented by certain keys, \n"
                        + "depending on how many players are in the game. The objective is to get rid of all your \n"
                        + "cards, and a description of the game rules can be found in the rules page.\n\n"
                        + "This project is of interest to me because Slap Jack is one of my favorite card \n"
                        + "games. I have always wanted to code some sort of interactive game, and this project is \n"
                        + "the perfect opportunity to do so. I believe this will be a fun project to work on.\n\n"
                        + "The point system of the game follows an opponent pairing process.\n"
                        + "The pair with the first player to slap correctly becomes the active pair for that round.\n"
                        + "The slower player in the active pair must collect all cards from the current round.\n"
                        + "The cards will be distributed based on the actions of the following pairs: \n\n"
                        + "- Player 1 VS Player 2 \n"
                        + "- Player 3 VS Player 4");
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
        createKeysButton(names, 305, 255, 80, 40, 16);
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
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

        // not sure why this doesn't work
        enter.getActionMap().put("ENTER", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enterNameAction(text, textArea, space);
            }
        });

        //TODO enter key for inputting player names

//        class EnterAction extends AbstractAction {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                enterNameAction(text, textArea, space);
//            }
//        }
//
//        AbstractAction enterAction = new EnterAction();
//        enter.getInputMap().put(KeyStroke.getKeyStroke("Enter"), "enterAction");
//        enter.getActionMap().put("enterAction", enterAction);
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
            try {
                runGameGUI();
            } catch (QuitGame ex) {
                throw new RuntimeException(ex); //TODO fix this
            }
            // have to enter play in console to start game...
            //runGameGUI();
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

        if (e.getActionCommand().equals("DONE")) {
            gameOver(QUIT_COMMAND);
            afterGameGUI();
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
        printKeys();
        initializeAndDeal();
        JLabel message = new JLabel("Get ready to play!");
        JOptionPane.showMessageDialog(null, message);
    }

    //EFFECTS: returns an image of specified card
    private ImageIcon getCardImage(String cardName) {
        String sep = System.getProperty("file.separator");
        ImageIcon cardimage = new ImageIcon(System.getProperty("user.dir") + sep  + "cardimages" + sep
                + cardName + ".png");
        Image image = cardimage.getImage();
        Image newimg = image.getScaledInstance(200, 290,  java.awt.Image.SCALE_SMOOTH);
        cardimage = new ImageIcon(newimg);

        return cardimage;
    }

    //EFFECTS: creates a Leaderboard GUI
    private void printLeaderboardGUI() {
        JFrame frame = new JFrame("Leaderboard");
        JLabel label = new JLabel("L E A D E R B O A R D");
        JTextArea textArea = new JTextArea("Username \t\t Wins \t\t Games Played \n\n");
        JButton sort = new JButton("Sort By Name");

        label.setBounds(180, 25, 450, 100);
        textArea.setBounds(75, 160, 700, 350);
        sort.setBounds(300, 450, 200, 75);

        label.setFont(new Font("Times New Roman", Font.BOLD, 40));
        textArea.setFont(new Font("Times New Roman", Font.PLAIN, 18));
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
        getContentPane().removeAll();
        repaint();
        JLabel win = new JLabel("The winner is " + winner + "!");
        win.setFont(new Font("Times New Roman", Font.BOLD, 55));
        win.setForeground(Color.WHITE);
        win.setBounds(350, 40, 800, 200);
        win.setVisible(true);
        this.add(win);

        JLabel thank = new JLabel("Thanks for playing :)");
        thank.setFont(new Font("Times New Roman", Font.BOLD, 25));
        thank.setForeground(Color.WHITE);
        thank.setBounds(475, 110, 500, 200);
        thank.setVisible(true);
        this.add(thank);

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
        save.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        save.setBounds(200,600,175,80);
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
        lb.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        lb.setBounds(487,600,225,80);
        lb.setVisible(true);
        lb.setActionCommand(VIEW_COMMAND);
        lb.addActionListener(e -> printLeaderboardGUI());

        add(lb);
    }

    //EFFECTS: creates Store Data button for afterGameGUI
    private void createStoreDataButton() {
        JButton store = new JButton("Store Data");
        store.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        store.setBounds(825,600,175,80);
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
        System.out.println("\nCards played:\n");

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
                System.out.println("\n-> Oh no, " + p.getName() + " is taking all the cards...");
                System.out.println("-> Taking all the cards...");
                gameMsg4.setText("-> Oh no, " + p.getName() + " is taking all the cards...");
            }

            if (first.equals(p.getSlapKey())) {
                System.out.println("\n-> Yay, " + p.getName() + " was the first to slap!");
                gameMsg2.setText("-> " + p.getName() + " was the first to slap!");
                if (randomNumber == 1) {
                    System.out.println("-> So speedy :D");
                    gameMsg3.setText("-> So speedy :D");
                }
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: handles the case when an incorrect slap occurs
    private void incorrectSlap(String first) {
        for (Player p : players) {
            if (first.equals(p.getSlapKey())) {
                System.out.println("\n Oh no, " + p.getName() + " wasn't supposed to slap >:(\n");
                System.out.println("-> Taking all the cards...");
                gameMsg2.setText("-> " + p.getName() + " wasn't supposed to slap >:(");
                gameMsg3.setText("-> Taking all the cards...");
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

    private String returnKeys() {
        String keys = "||   ";
        for (int k = 0; k < players.size(); k++) {
            String playerName = players.get(k).getName();
            int cardsLeft = players.get(k).getNumCardsLeft();
            keys = keys.concat(playerName + " : " + cardsLeft + " cards left   ||   ");
        }
        return keys;
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

        if (cardCountInt == 0) {
            prevCardInt = 13;
        } else {
            prevCardInt = (cardCountInt - 1) % values.size(); //Todo changed this might not work later
        }

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

    //EFFECTS: prints event log to the console
    private void printEventLog() {
        StringBuilder printed = new StringBuilder();
        for (Event next : EventLog.getInstance()) {
            printed.append(next.toString()).append("\n\n");
        }
        // this does the same thing as
        // String printed = "";
        //        for (Event next : EventLog.getInstance()) {
        //            printed = printed + next.toString() + "\n\n";
        //        }

        System.out.println(printed);
    }

//    @Override
//    public void dispose() {
//        printEventLog();
//        super.dispose();
//    }

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