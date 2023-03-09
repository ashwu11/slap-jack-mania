package model;

/*
 * Represents an account with a username, number of wins, and number of games played.
 */

public class Account {
    private String username;
    private int wins;
    private int gamesPlayed;

    //EFFECTS: creates a new account with username and 0 wins and 0 games played
    public Account(String username) {
        this.username = username;
        wins = 0;
        gamesPlayed = 0;
    }

    //MODIFIES: this
    //EFFECTS: adds 1 to games played and only add 1 to wins if win state is true
    public void updateAccount(Boolean win) {
        this.gamesPlayed++;
        if (win) {
            this.wins++;
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

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

}
