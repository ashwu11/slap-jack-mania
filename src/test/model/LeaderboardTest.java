package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class LeaderboardTest {
    private Leaderboard lb;

    @BeforeEach
    public void runBefore() {
        lb = new Leaderboard();
    }

    @Test
    public void testConstructor() {
        assertEquals(0, lb.getAccounts().size());
    }

    @Test
    public void testRegisterAccountSingle() {
        lb.registerAccount("acc");
        assertEquals(1, lb.getAccounts().size());
        assertEquals("acc", lb.getAccounts().get(0).getUsername());
    }

    @Test
    public void testRegisterAccountMultiple() {
        lb.registerAccount("one");
        assertEquals(1, lb.getAccounts().size());
        assertEquals("one", lb.getAccounts().get(0).getUsername());
        lb.registerAccount("two");
        assertEquals(2, lb.getAccounts().size());
        assertEquals("two", lb.getAccounts().get(1).getUsername());
        lb.registerAccount("three");
        assertEquals(3, lb.getAccounts().size());
        assertEquals("three", lb.getAccounts().get(2).getUsername());
    }

    @Test
    public void testUpdateAccountSingleWin() {
        lb.registerAccount("win");
        lb.updateAccount("win", true);
        Account a = lb.getAccounts().get(0);
        assertEquals(1, a.getGamesPlayed());
        assertEquals(1, a.getWins());

    }

    @Test
    public void testUpdateAccountSingleLoss() {
        lb.registerAccount("loss");
        lb.updateAccount("loss", false);
        Account a = lb.getAccounts().get(0);
        assertEquals(1, a.getGamesPlayed());
        assertEquals(0, a.getWins());
    }

    @Test
    public void testUpdateAccountMultipleFindsImmediately() {
        lb.registerAccount("one");
        lb.registerAccount("two");
        lb.registerAccount("three");
        Account accOne = lb.getAccounts().get(0);

        lb.updateAccount("one", true);
        assertEquals(1, accOne.getGamesPlayed());
        assertEquals(1, accOne.getWins());
    }

    @Test
    public void testUpdateAccountMultipleFindsMiddle() {
        lb.registerAccount("one");
        lb.registerAccount("two");
        lb.registerAccount("three");
        Account accTwo = lb.getAccounts().get(1);

        lb.updateAccount("two", false);
        assertEquals(1, accTwo.getGamesPlayed());
        assertEquals(0, accTwo.getWins());
    }

    @Test
    public void testUpdateAccountMultipleFindsLast() {
        lb.registerAccount("one");
        lb.registerAccount("two");
        lb.registerAccount("three");
        Account accThree = lb.getAccounts().get(2);

        lb.updateAccount("three", true);
        assertEquals(1, accThree.getGamesPlayed());
        assertEquals(1, accThree.getWins());
    }

    @Test
    public void testLookupAccountFindsImmediately() {
        lb.registerAccount("one");
        lb.registerAccount("two");
        lb.registerAccount("three");
        Account accOne = lb.getAccounts().get(0);
        assertEquals(accOne, lb.lookupAccount("one"));

    }

    @Test
    public void testLookupAccountFindsLater() {
        lb.registerAccount("one");
        lb.registerAccount("two");
        lb.registerAccount("three");
        Account accThree = lb.getAccounts().get(2);
        assertEquals(accThree, lb.lookupAccount("three"));
    }

    @Test
    public void testLookupAccountFailsToFind() {
        lb.registerAccount("one");
        lb.registerAccount("two");
        lb.registerAccount("three");
        assertNull(lb.lookupAccount("four"));
    }

    @Test
    public void testPrintAllAccountsOne() {
        lb.registerAccount("one");
        lb.updateAccount("one", false);
        String expected = "one\t\t\t0\t\t\t1\n";
        assertEquals(expected, lb.printAllAccounts());
    }

    @Test
    public void testPrintAllAccounts() {
        lb.registerAccount("one");
        lb.registerAccount("two");
        lb.updateAccount("one", true);
        lb.updateAccount("two", false);

        String expected = "one\t\t\t1\t\t\t1\ntwo\t\t\t0\t\t\t1\n";
        assertEquals(expected, lb.printAllAccounts());
    }

    @Test
    public void testGetSortedAccountsByName() {
        lb.registerAccount("b");
        lb.registerAccount("a");
        lb.registerAccount("c");
        ArrayList<Account> expected = new ArrayList<>();
        expected.add(lb.getAccounts().get(1));
        expected.add(lb.getAccounts().get(0));
        expected.add(lb.getAccounts().get(2));
        assertEquals(expected, lb.getSortedAccountsByName());
    }


}
