package model;

    /**
     * Represents an account with a username, status quote, number of wins, number of games played,
     * current win streak, and highest win streak.
     **/

public class Account {
    private String username;
    private int wins;
    private int gamesPlayed;

    public Account(String username) {
        this.username = username;
        wins = 0;
        gamesPlayed = 0;
    }

    public void updateAccount(Boolean win) {
        gamesPlayed += 1;
        if (win) {
            wins += 1;
        }
    }

    // EFFECTS: determines if given string matches account username
    public Boolean matchAccount(String name) {
        if (name.equals(this.username)) {
            return true;
        } else {
            return false;
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

//    public int getWinStreak() {
//        return winStreak;
//    }
//
//    public int getHighestWinStreak() {
//        return highestWinStreak;
//    }

}
