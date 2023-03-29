package model;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

/*
 * Represents a leaderboard with a list of accounts
 */

public class Leaderboard {
    private final ArrayList<Account> accounts;
    //private final HashMap<String, Account> leaderboard2;

    //EFFECTS: makes a new leaderboard with an empty list of accounts
    public Leaderboard() {
        accounts = new ArrayList<>();
    }

    //EFFECTS: makes a new leaderboard with the specified list of accounts
    public Leaderboard(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }

    //MODIFIES: this
    //REQUIRES: the username does not already exist in leaderboard
    //EFFECTS: creates and adds an account to the leaderboard
    public void registerAccount(String name) {
        Account newAccount = new Account(name);
        accounts.add(newAccount);
    }

    //MODIFIES: this
    //REQUIRES: leaderboard is not empty
    //EFFECTS: updates the stats of one account in leaderboard
    public void updateAccount(String name, Boolean win) {
        for (Account a : accounts) {
            if (a.getUsername().equals(name)) {
                a.updateAccount(win);
            }
        }
    }

    // EFFECTS: returns account object that has name
    public Account lookupAccount(String name) {
        Account acc = null;
        for (Account a : accounts) {
            if (a.getUsername().equalsIgnoreCase(name)) {
                acc = a;
            }
        }
        return acc;
    }

    //EFFECTS: prints out all accounts in leaderboard
    public String printAllAccounts() {
        String all = "";
        for (Account a : accounts) {
            all = all.concat(a.getUsername() + "\t\t\t" + a.getWins() + "\t\t\t" + a.getGamesPlayed() + "\n");
        }
        return all;
    }

    //EFFECTS: puts the list of accounts in json object and returns it
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("accounts", this.accounts);
        return json;
    }

    //EFFECTS: returns a list of accounts sorted by username
    public ArrayList<Account> getSortedAccountsByName() {
        ArrayList<String> names = new ArrayList<>();
        for (Account a : this.accounts) {
            names.add(a.getUsername());
        }

        Collections.sort(names);

        ArrayList<Account> accounts = new ArrayList<>();
        for (String s : names) {
            accounts.add(lookupAccount(s));

        }

        return accounts;
    }

//    public ArrayList<Account> getSortedAccountsByWins() {
//        ArrayList<Account> accounts = new ArrayList<>();
//        int n = 0;
//        for (Account a : this.accounts) {
//            if (a.getWins() == n) {
//                accounts.add(a);
//            }
//            n++;
//        }
//
//        Collections.sort(accounts, Collections.reverseOrder());
//        return accounts;
//    }

    //getters
    public ArrayList<Account> getAccounts() {
        return this.accounts;
    }

//    //fix this method later when we learn about iterators...
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
}
