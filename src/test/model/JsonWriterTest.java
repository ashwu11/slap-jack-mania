package model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

// Modeled after Json Serialization Demo
public class JsonWriterTest {

    @Test
    public void testWriterIllegalFile() {
        try {
            Leaderboard lb = new Leaderboard();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException expected");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    public void testWriterEmptyLeaderboard() {
        try {
            Leaderboard lb = new Leaderboard();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyLeaderboard.json");
            writer.open();
            writer.write(lb);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyLeaderboard.json");
            lb = reader.read();
            assertEquals(0, lb.getAccounts().size());
        } catch (IOException e) {
            fail("Exception not expected");
        }
    }



    @Test
    void testWriterNormalLeaderboard() {
        try {
            Leaderboard lb = new Leaderboard();
            lb.registerAccount("anya");
            lb.registerAccount("bond");
            Account anya = lb.getAccounts().get(0);
            Account bond = lb.getAccounts().get(1);
            anya.setWins(0);
            anya.setGamesPlayed(2);
            bond.setWins(2);
            bond.setGamesPlayed(2);

            JsonWriter writer = new JsonWriter("./data/testWriterNormalLeaderboard.json");
            writer.open();
            writer.write(lb);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterNormalLeaderboard.json");
            lb = reader.read();
            assertEquals(2, lb.getAccounts().size());
            assertEquals("anya", anya.getUsername());
            assertEquals(0, anya.getWins());
            assertEquals(2, anya.getGamesPlayed());

            assertEquals("bond", bond.getUsername());
            assertEquals(2, bond.getWins());
            assertEquals(2, bond.getGamesPlayed());

        } catch (IOException e) {
            fail("Exception not expected");
        }
    }

}
