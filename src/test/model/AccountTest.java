package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountTest {
    private Account test1;
    private Account test2;
    //TODO Q: Do we need a test class for every single model? Also do we need tests for getters?

    @BeforeEach
    public void runBefore() {
        test1 = new Account("Test1");
        test2 = new Account("Test2");
    }

    @Test
    public void testConstructor(){
        assertEquals("Test1", test1.getUsername());
        assertEquals(0, test1.getWins());
        assertEquals(0, test1.getGamesPlayed());

        assertEquals("Test2", test2.getUsername());
        assertEquals(0, test2.getWins());
        assertEquals(0, test2.getGamesPlayed());
    }

    @Test
    public void testUpdateAccount() {
        test1.updateAccount(true);
        assertEquals(1, test1.getGamesPlayed());
        assertEquals(1, test1.getWins());

        test2.updateAccount(false);
        assertEquals(1, test2.getGamesPlayed());
        assertEquals(0, test2.getWins());
    }

}
