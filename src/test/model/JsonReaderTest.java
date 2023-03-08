package model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;

// Modeled after Json Serialization Demo
class JsonReaderTest {

    @Test
    public void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/nonExistentFile.json");
        try {
            Leaderboard lb = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    public void testReaderEmptyLeaderboard() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyLeaderboard.json");
        try {
            Leaderboard lb = reader.read();
            assertEquals(0, lb.getAccounts().size());
        } catch (IOException e) {
            fail("Exception unexpected: Couldn't read from file");
        }
    }


    @Test
    public void testReaderNormalLeaderboard() {
        JsonReader reader = new JsonReader("./data/testReaderNormalLeaderboard.json");
        try {
            Leaderboard lb = reader.read();
            List<Account> accounts = lb.getAccounts();
            assertEquals(2, accounts.size());
            assertEquals(1, accounts.get(0).getGamesPlayed());
            assertEquals(0, accounts.get(0).getWins());
            assertEquals("kobe", accounts.get(0).getUsername());
            assertEquals(1, accounts.get(1).getGamesPlayed());
            assertEquals(1, accounts.get(1).getWins());
            assertEquals("ivan", accounts.get(1).getUsername());
        } catch (IOException e) {
            fail("Exception unexpected: Couldn't read from file");
        }
    }

}
