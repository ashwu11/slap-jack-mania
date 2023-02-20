package model;

    /**
     * Represents an account with a username, status quote, number of wins, number of games played,
     * current win streak, and highest win streak.
     **/

public class Account {
    private String username;
    private String status;
    private int wins;
    private int gamesPlayed;
    private int winStreak;
    private int highestWinStreak;

    public Account(String username, String status) {
        this.username = username;
        this.status = status;
        wins = 0;
        gamesPlayed = 0;
        winStreak = 0;
        highestWinStreak = 0;
    }



    // getters & setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getWins() {
        return wins;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public int getWinStreak() {
        return winStreak;
    }

    public int getHighestWinStreak() {
        return highestWinStreak;
    }

}
