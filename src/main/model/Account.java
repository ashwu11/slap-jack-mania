package model;

public class Account {
    private String username;
    private String status;
    private int wins;
    private int gamesPlayed;
    private int winStreak;

    public Account(String username, String status) {
        this.username = username;
        this.status = status;
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

}
