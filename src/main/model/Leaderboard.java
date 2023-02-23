package model;

import java.util.ArrayList;

/**
 * Represents a leaderboard displaying each account and its corresponding stats
 **/

public class Leaderboard {
    private static final ArrayList<Account> leaderboard = new ArrayList<>();

    //TODO Q: it doesn't allow me to make a constructor, so how can I make tests for this class?
    public Leaderboard() {
        //leaderboard = new ArrayList<>();
    }

    //MODIFIES: this
    //REQUIRES: the username does not already exist in leaderboard
    //EFFECTS: creates and adds an account to the leaderboard
    public static void registerAccount(String name) {
        Account newAccount = new Account(name);
        leaderboard.add(newAccount);
    }

    public static void updateAccount(String name, Boolean win) {
        for (Account a : leaderboard) {
            if (a.getUsername().equals(name)) {
                a.updateAccount(win);
            }
        }
    }

    public static Boolean checkExisting(String name) {
        Boolean exists = false;

        for (Account a : leaderboard) {
            if (a.getUsername().equals(name)) {
                exists = true;
            }
        }
        return exists;
    }

    // REQUIRES: account name is the name of an existing account
    // EFFECTS: returns account object that has name
    public static Account lookupAccount(String name) {
        Account acc = null;
        for (Account a : leaderboard) {
            if (a.getUsername().equalsIgnoreCase(name)) {
                acc = a;
            }
        }
        return acc;
    }

    //TODO Q: this method doesn't work sometimes -> TA said we will learn about iterators later
    //REQUIRES: leaderboard.size() > 1
    //EFFECTS: removes account from leaderboard
    public static void removeAccount(String name) {
        int count = 0;
        for (Account a : leaderboard) {
            if (name.equals(a.getUsername())) {
                leaderboard.remove(count);
            }
            count++;
        }
    }

    //EFFECTS: prints out all accounts in leaderboard
    public static void printAllAccounts() {
        System.out.println("\nusername | wins | games played\n");
        for (Account a : leaderboard) {
            System.out.println(a.getUsername() + " | " + a.getWins() + " | " + a.getGamesPlayed());
        }
        System.out.println("\n");
    }
}
