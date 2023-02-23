package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {
    private Player p1;
    private Player p2;

    @BeforeEach
    public void runBefore() {
        ArrayList<Card> p1hand = new ArrayList<>();
        ArrayList<Card> p2hand = new ArrayList<>();

        p1 = new Player("player1", p1hand, "x", "c");
        p2 = new Player("player2", p2hand, "b", "n");
    }

    @Test
    public void testConstructor() {
        assertEquals("player1", p1.getName());

        assertEquals("player2", p2.getName());
    }
}
