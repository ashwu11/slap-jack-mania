package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountTest {
    private Account test1;

    @BeforeEach
    public void runBefore() {
        test1 = new Account("Test1");
    }

    @Test
    public void testConstructor(){
        assertEquals("Test1", test1.getUsername());
        assertEquals(0, test1.getWins());
        assertEquals(0, test1.getGamesPlayed());
    }

    @Test
    public void testUpdateAccountOnceWin() {
        test1.updateAccount(true);
        assertEquals(1, test1.getGamesPlayed());
        assertEquals(1, test1.getWins());
    }

    @Test
    public void testUpdateAccountOnceLoss() {
        test1.updateAccount(false);
        assertEquals(1, test1.getGamesPlayed());
        assertEquals(0, test1.getWins());
    }

    @Test
    public void testUpdateAccountMultipleTimes() {
        test1.updateAccount(true);
        test1.updateAccount(false);
        test1.updateAccount(true);
        assertEquals(3, test1.getGamesPlayed());
        assertEquals(2, test1.getWins());
    }


}
