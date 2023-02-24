package model;

/**
 * Represents an account with a username, status quote, number of wins, number of games played,
 * current win streak, and highest win streak.
 **/

public class Account {
    private String username;
    private int wins;
    private int gamesPlayed;

    //EFFECTS: creates a new account with specified username and 0 wins and 0 games played
    public Account(String username) {
        this.username = username;
        wins = 0;
        gamesPlayed = 0;
    }

    //MODIFIES: this
    //EFFECTS: adds 1 to games played and only add 1 to wins if win is true
    public void updateAccount(Boolean win) {
        gamesPlayed += 1;
        if (win) {
            wins += 1;
        }
    }


    // getters & setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getWins() {
        return wins;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

}
