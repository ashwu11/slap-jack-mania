package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountTest {
    private Account test1;
    //TODO Q: Do we need tests for getters?

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
    public void testUpdateAccountWin() {
        test1.updateAccount(true);
        assertEquals(1, test1.getGamesPlayed());
        assertEquals(1, test1.getWins());
    }

    @Test
    public void testUpdateAccountLoss() {
        test1.updateAccount(false);
        assertEquals(1, test1.getGamesPlayed());
        assertEquals(0, test1.getWins());
    }

}
