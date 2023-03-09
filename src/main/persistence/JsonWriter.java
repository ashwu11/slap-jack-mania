package persistence;

import model.Leaderboard;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/*
 * Represents a writer that writes JSON representation of Leaderboard to file
 * Modeled after Json Serialization Demo
 */

public class JsonWriter {
    private final String destination;
    private PrintWriter writer;
    private static final int TAB = 4;

    // EFFECTS: constructs a writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer
    // if destination file cannot be opened for writing, throws a FileNotFoundException
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(destination);
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of Leaderboard to file
    public void write(Leaderboard lb) {
        JSONObject json = lb.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
