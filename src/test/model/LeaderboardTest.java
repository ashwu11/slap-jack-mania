package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
        assertEquals(0, lb.getLeaderboard().size());
    }

    @Test
    public void testRegisterAccountSingle() {
        lb.registerAccount("acc");
        assertEquals(1, lb.getLeaderboard().size());
        assertEquals("acc", lb.getLeaderboard().get(0).getUsername());
    }

    @Test
    public void testRegisterAccountMultiple() {
        lb.registerAccount("one");
        assertEquals(1, lb.getLeaderboard().size());
        assertEquals("one", lb.getLeaderboard().get(0).getUsername());
        lb.registerAccount("two");
        assertEquals(2, lb.getLeaderboard().size());
        assertEquals("two", lb.getLeaderboard().get(1).getUsername());
        lb.registerAccount("three");
        assertEquals(3, lb.getLeaderboard().size());
        assertEquals("three", lb.getLeaderboard().get(2).getUsername());
    }

    @Test
    public void testUpdateAccountSingleWin() {
        lb.registerAccount("win");
        lb.updateAccount("win", true);
        Account a = lb.getLeaderboard().get(0);
        assertEquals(1, a.getGamesPlayed());
        assertEquals(1, a.getWins());

    }

    @Test
    public void testUpdateAccountSingleLoss() {
        lb.registerAccount("loss");
        lb.updateAccount("loss", false);
        Account a = lb.getLeaderboard().get(0);
        assertEquals(1, a.getGamesPlayed());
        assertEquals(0, a.getWins());
    }

    @Test
    public void testUpdateAccountMultipleFindsImmediately() {
        lb.registerAccount("one");
        lb.registerAccount("two");
        lb.registerAccount("three");
        Account accOne = lb.getLeaderboard().get(0);

        lb.updateAccount("one", true);
        assertEquals(1, accOne.getGamesPlayed());
        assertEquals(1, accOne.getWins());
    }

    @Test
    public void testUpdateAccountMultipleFindsMiddle() {
        lb.registerAccount("one");
        lb.registerAccount("two");
        lb.registerAccount("three");
        Account accTwo = lb.getLeaderboard().get(1);

        lb.updateAccount("two", false);
        assertEquals(1, accTwo.getGamesPlayed());
        assertEquals(0, accTwo.getWins());
    }

    @Test
    public void testUpdateAccountMultipleFindsLast() {
        lb.registerAccount("one");
        lb.registerAccount("two");
        lb.registerAccount("three");
        Account accThree = lb.getLeaderboard().get(2);

        lb.updateAccount("three", true);
        assertEquals(1, accThree.getGamesPlayed());
        assertEquals(1, accThree.getWins());
    }

    @Test
    public void testLookupAccountFindsImmediately() {
        lb.registerAccount("one");
        lb.registerAccount("two");
        lb.registerAccount("three");
        Account accOne = lb.getLeaderboard().get(0);
        assertEquals(accOne, lb.lookupAccount("one"));

    }

    @Test
    public void testLookupAccountFindsLater() {
        lb.registerAccount("one");
        lb.registerAccount("two");
        lb.registerAccount("three");
        Account accThree = lb.getLeaderboard().get(2);
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
        String expected = "one | 0 | 1\n";
        assertEquals(expected, lb.printAllAccounts());
    }

    @Test
    public void testPrintAllAccounts() {
        lb.registerAccount("one");
        lb.registerAccount("two");
        lb.updateAccount("one", true);
        lb.updateAccount("two", false);

        String expected = "one | 1 | 1\ntwo | 0 | 1\n";
        assertEquals(expected, lb.printAllAccounts());
    }

}
