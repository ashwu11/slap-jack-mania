package model;

import java.util.ArrayList;

    /**
     * Represents a leaderboard displaying each account and its corresponding stats
    **/

public class Leaderboard {
    private static ArrayList<Account> leaderboard;

    public Leaderboard() {
        leaderboard = new ArrayList<Account>();
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

    //TODO method that can view all accounts w stats based on top wins
    public static void printAllAccounts() {
        System.out.println("username | wins | games played");
        // for (Account a : leaderboard)
        // System.out.println(a.getUsername() + "  " a.getWins() + "  " etc.....)
        // maybe find a way to line everything up? Like add spaces after the username after checking length idk
    }
}
