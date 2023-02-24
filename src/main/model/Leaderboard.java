package model;

import java.util.ArrayList;

/**
 * Represents a leaderboard displaying each account and its corresponding stats
 **/

public class Leaderboard {
    private final ArrayList<Account> leaderboard;

    //EFFECTS: makes a new list of accounts
    public Leaderboard() {
        leaderboard = new ArrayList<>();
    }

    //MODIFIES: this
    //REQUIRES: the username does not already exist in leaderboard
    //EFFECTS: creates and adds an account to the leaderboard
    public void registerAccount(String name) {
        Account newAccount = new Account(name);
        leaderboard.add(newAccount);
    }

    //MODIFIES: this
    //REQUIRES: leaderboard is not empty
    //EFFECTS: updates the stats of one account in leaderboard
    public void updateAccount(String name, Boolean win) {
        for (Account a : leaderboard) {
            if (a.getUsername().equals(name)) {
                a.updateAccount(win);
            }
        }
    }

    // EFFECTS: returns account object that has name
    public Account lookupAccount(String name) {
        Account acc = null;
        for (Account a : leaderboard) {
            if (a.getUsername().equalsIgnoreCase(name)) {
                acc = a;
            }
        }
        return acc;
    }

//    //this method doesn't work sometimes -> prof said can fix this later, when we learn about iterators...
//    //REQUIRES: leaderboard.size() > 1
//    //EFFECTS: removes account from leaderboard
//    public void removeAccount(String name) {
//        int count = 0;
//        for (Account a : leaderboard) {
//            if (name.equals(a.getUsername())) {
//                leaderboard.remove(count);
//            }
//            count++;
//        }
//    }

    //EFFECTS: prints out all accounts in leaderboard
    public String printAllAccounts() {
        String all = "";
        for (Account a : leaderboard) {
            all = all.concat(a.getUsername() + " | " + a.getWins() + " | " + a.getGamesPlayed() + "\n");
        }
        return all;
    }

    public ArrayList<Account> getLeaderboard() {
        return this.leaderboard;
    }
}
