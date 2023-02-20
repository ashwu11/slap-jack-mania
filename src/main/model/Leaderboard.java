package model;

import java.util.ArrayList;
import model.Account;

    /**
     * Represents a leaderboard displaying each account and its corresponding stats
    **/

public class Leaderboard {
    private ArrayList<Account> leaderboard;

    public Leaderboard() {
        leaderboard = new ArrayList<Account>();
    }

    public String viewAccount(String user) {
        return null; // show account info for given username
    }

    //TODO method that can view all accounts w stats
        // System.out.println("username | wins | games played | win streak | highest win streak")
        // for (Account a : leaderboard)
        // System.out.println(a.getUsername() + "  " a.getWins() + "  " etc.....)


}
